package com.psybergate.resoma.time.service.impl;

import com.psybergate.resoma.time.dto.ValidationDTO;
import com.psybergate.resoma.time.entity.Status;
import com.psybergate.resoma.time.entity.TimeEntry;
import com.psybergate.resoma.time.repository.TimeEntryRepository;
import com.psybergate.resoma.time.resource.EmployeeResource;
import com.psybergate.resoma.time.resource.ProjectServiceClient;
import com.psybergate.resoma.time.service.TimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TimeServiceImpl implements TimeService {

    private final TimeEntryRepository timeEntryRepository;
    private final EmployeeResource employeeResource;
    private ProjectServiceClient projectServiceClient;

    @Autowired
    public TimeServiceImpl(TimeEntryRepository timeEntryRepository,
                           EmployeeResource employeeResource,
                           ProjectServiceClient projectServiceClient) {
        this.timeEntryRepository = timeEntryRepository;
        this.employeeResource = employeeResource;
        this.projectServiceClient = projectServiceClient;
    }

    @Override
    @Transactional
    public TimeEntry captureTime(@Valid TimeEntry timeEntry) {
        if (!employeeResource.validateEmployee(timeEntry.getEmployeeId())) {
            throw new ValidationException("Employee id does no exist");
        }
        ValidationDTO validationDTO = projectServiceClient.validateProject(timeEntry.getProjectId());
        if (!validationDTO.getExist()) {
            throw new ValidationException("Project id does no exist");
        }
        validationDTO = projectServiceClient.validateTask(timeEntry.getProjectId(), timeEntry.getTaskId());
        if (!validationDTO.getExist()) {
            throw new ValidationException("Task id does no exist");
        }
        timeEntry.setStatus(Status.NEW);
        timeEntry.addStatusHistory();
        timeEntry = timeEntryRepository.save(timeEntry);
        return timeEntry;
    }

    @Override
    public List<TimeEntry> retrieveEntries(Boolean deleted) {
        return timeEntryRepository.findAllByDeleted(deleted);
    }

    @Override
    public TimeEntry retrieveEntry(UUID entryId) {
        return timeEntryRepository.findByIdAndDeleted(entryId, false);
    }

    @Override
    @Transactional
    public TimeEntry updateEntry(@Valid TimeEntry timeEntry) {
        if (timeEntry.isApproved())
            throw new ValidationException("Status can not be APPROVED");
        timeEntry.setStatus(Status.NEW);
        return timeEntryRepository.save(timeEntry);
    }

    @Override
    @Transactional
    public void deleteEntry(UUID entryId) {
        TimeEntry timeEntry = retrieveEntry(entryId);
        deleteEntry(timeEntry);
    }

    @Override
    @Transactional
    public TimeEntry submitEntry(@Valid TimeEntry timeEntry) {
        if (!EnumSet.of(Status.NEW, Status.SUBMITTED, Status.REJECTED).contains(timeEntry.getStatus()))
            throw new ValidationException("Status can only be NEW, SUBMITTED or REJECTED");
        timeEntry.setStatus(Status.SUBMITTED);
        timeEntry.addStatusHistory();
        timeEntry = timeEntryRepository.save(timeEntry);
        return timeEntry;
    }


    @Override
    @Transactional
    public TimeEntry approveEntry(@Valid TimeEntry timeEntry) {
        if (!EnumSet.of(Status.SUBMITTED).contains(timeEntry.getStatus()))
            throw new ValidationException("Status can only be SUBMITTED");
        timeEntry.setStatus(Status.APPROVED);
        timeEntry.addStatusHistory();
        timeEntry = timeEntryRepository.save(timeEntry);
        return timeEntry;
    }

    @Override
    @Transactional
    public TimeEntry rejectEntry(@Valid TimeEntry timeEntry) {
        if (!EnumSet.of(Status.SUBMITTED).contains(timeEntry.getStatus()))
            throw new ValidationException("Status can only be SUBMITTED");
        timeEntry.setStatus(Status.REJECTED);
        timeEntry.addStatusHistory();
        timeEntry = timeEntryRepository.save(timeEntry);
        return timeEntry;
    }

    @Override
    @Transactional
    public List<TimeEntry> submitEntries(List<@Valid TimeEntry> timeEntries) {
        List<TimeEntry> submittedEntries = new ArrayList<>();
        timeEntries.forEach(timeEntry -> submittedEntries.add(submitEntry(timeEntry)));
        return submittedEntries;
    }

    @Override
    @Transactional
    public List<TimeEntry> approveEntries(List<@Valid TimeEntry> timeEntries) {
        List<TimeEntry> submittedEntries = new ArrayList<>();
        timeEntries.forEach(timeEntry -> submittedEntries.add(approveEntry(timeEntry)));
        return submittedEntries;
    }

    @Override
    @Transactional
    public List<TimeEntry> rejectEntries(List<@Valid TimeEntry> entries) {
        List<TimeEntry> rejectedEntries = new ArrayList<>();
        entries.forEach(timeEntry -> rejectedEntries.add(rejectEntry(timeEntry)));
        return rejectedEntries;
    }


    private void deleteEntry(TimeEntry timeEntry) {
        if (Status.APPROVED.equals(timeEntry.getStatus()))
            throw new ValidationException("Status can not be APPROVED");
        timeEntry.setDeleted(true);
        timeEntryRepository.save(timeEntry);
    }

}

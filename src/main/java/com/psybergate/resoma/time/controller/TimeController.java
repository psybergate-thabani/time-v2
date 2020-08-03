package com.psybergate.resoma.time.controller;

import com.psybergate.resoma.time.entity.TimeEntry;
import com.psybergate.resoma.time.service.TimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping(path = "/api/time")
public class TimeController {

    @Autowired
    private TimeService timeService;

    @Value("${eureka.instance.instance-id}")
    private String instanceId;

    @PostMapping("v1/time-entries")
    public ResponseEntity<TimeEntry> captureTimeEntry(@RequestBody @Valid TimeEntry timeEntry) {
        TimeEntry retrievedTimeEntry = timeService.captureTime(timeEntry);
        return ResponseEntity.ok(retrievedTimeEntry);
    }

    @DeleteMapping("v1/time-entries/{timeEntryId}")
    public ResponseEntity<Void> deleteTimeEntry(@PathVariable UUID timeEntryId) {
        timeService.deleteEntry(timeEntryId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("v1/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> retrieveTimeEntry(@PathVariable UUID timeEntryId) {
        return ResponseEntity.ok(timeService.retrieveEntry(timeEntryId));
    }

    @GetMapping(value = "v1/time-entries", params = {"deleted"})
    public ResponseEntity<List<TimeEntry>> retrieveTimeEntries(@RequestParam("deleted") Boolean deleted) {
        log.info("Instance Id: {}", instanceId);
        return ResponseEntity.ok(timeService.retrieveEntries(deleted));
    }

    @PutMapping("v1/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> updateTimeEntry(@PathVariable UUID timeEntryId, @RequestBody @Valid TimeEntry timeEntry) {
        if (!timeEntryId.equals(timeEntry.getId()))
            throw new ValidationException("id in url path does not match time entry id in request body");
        TimeEntry retrievedTimeEntry = timeService.updateEntry(timeEntry);
        return ResponseEntity.ok(retrievedTimeEntry);
    }

    @PutMapping(path = "v1/time-entries/{timeEntryId}/submit")
    public ResponseEntity<TimeEntry> submitTimeEntry(@PathVariable UUID timeEntryId,
                                                     @RequestBody @Valid TimeEntry timeEntry) {
        if (!timeEntryId.equals(timeEntry.getId()))
            throw new ValidationException("id in url path does not match time entry id in request body");
        return ResponseEntity.ok(timeService.submitEntry(timeEntry));
    }

    @PutMapping(path = "v1/time-entries/submit")
    public ResponseEntity<List<TimeEntry>> submitTimeEntries(@RequestBody @Valid List<TimeEntry> timeEntries) {
        return ResponseEntity.ok(timeService.submitEntries(timeEntries));
    }

    @PutMapping(path = "v1/time-entries/{timeEntryId}/approve")
    public ResponseEntity<TimeEntry> approveTimeEntry(@PathVariable UUID timeEntryId, @RequestBody @Valid TimeEntry timeEntry) {
        if (!timeEntryId.equals(timeEntry.getId()))
            throw new ValidationException("id in url path does not match time entry id in request body");
        return ResponseEntity.ok(timeService.approveEntry(timeEntry));
    }

    @PutMapping(path = "v1/time-entries/approve")
    public ResponseEntity<List<TimeEntry>> approveTimeEntries(@RequestBody @Valid List<TimeEntry> timeEntries) {
        return ResponseEntity.ok(timeService.approveEntries(timeEntries));
    }

    @PutMapping(path = "v1/time-entries/{id}/reject")
    public ResponseEntity<TimeEntry> rejectTimeEntry(@PathVariable(name = "id") UUID entryId, @RequestBody @Valid TimeEntry timeEntry) {
        if (!entryId.equals(timeEntry.getId()))
            throw new ValidationException("id in url path does not match time entry id in request body");
        return ResponseEntity.ok(timeService.rejectEntry(timeEntry));
    }

    @PutMapping(path = "v1/time-entries/reject")
    public ResponseEntity<List<TimeEntry>> rejectTimeEntries(@RequestBody @Valid List<TimeEntry> timeEntries) {
        return ResponseEntity.ok(timeService.rejectEntries(timeEntries));
    }
}


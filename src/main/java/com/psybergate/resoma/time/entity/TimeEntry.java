package com.psybergate.resoma.time.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, of = {"employeeId", "taskId", "date"})
@Entity(name = "TimeEntry")
public class TimeEntry extends BaseEntity {

    @NotNull(message = "{employee.notnull}")
    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @NotNull(message = "{project.notnull}")
    @Column(name = "project_id", nullable = false)
    private UUID projectId;

    @NotNull(message = "{task.notnull}")
    @Column(name = "task_id", nullable = false)
    private UUID taskId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "status_reason")
    private String statusReason;

    @Column(name = "description")
    private String description;

    @Positive(message = "{period.positive}")
    @Column(name = "period", nullable = false)
    private int period;

    @NotNull(message = "{date.notnull}")
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "time_entry_id", nullable = false)
    private Set<StatusHistory> statusHistory = new HashSet<>();

    public TimeEntry() {
    }

    public TimeEntry(UUID employeeId, UUID projectId, UUID taskId, String description, int period, LocalDate date, boolean deleted) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.taskId = taskId;
        this.description = description;
        this.period = period;
        this.date = date;
        this.status = Status.NEW;
        this.setDeleted(deleted);
        super.setId(UUID.randomUUID());
        super.setCreatedDate(LocalDateTime.now());
    }

    public boolean isApproved() {
        return Status.APPROVED.equals(status);
    }

    public void addStatusHistory() {
        getStatusHistory().add(new StatusHistory(this));
    }
}

package com.psybergate.resoma.time.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"status", "statusReason", "timeStamp"}, callSuper = false)
@Entity(name = "StatusHistory")
public class StatusHistory extends BaseEntity {

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{status.notnull}")
    private Status status;

    @Column(name = "status_update_reason")
    private String statusReason;

    @Column(name = "time_stamp", nullable = false)
    @NotNull(message = "{timestamp.notnull}")
    private LocalDateTime timeStamp;

    public StatusHistory() {
    }

    public StatusHistory(TimeEntry timeEntry) {
        this.status = timeEntry.getStatus();
        this.statusReason = timeEntry.getStatusReason();
        this.timeStamp = LocalDateTime.now();
        super.setId(UUID.randomUUID());
        super.setCreatedDate(LocalDateTime.now());
    }
}

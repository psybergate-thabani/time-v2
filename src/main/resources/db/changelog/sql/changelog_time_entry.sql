--liquibase formatted sql
--changeset thabani:create_time_entry_table
CREATE TABLE time_entry(
    id                  UUID PRIMARY KEY,
    version             BIGINT NOT NULL,
    deleted             BOOLEAN,
    created             TIMESTAMP,
    created_by          VARCHAR(255),
    last_modified_by    VARCHAR(255),
    last_modified_date  TIMESTAMP,
    employee_id         UUID NOT NULL,
    project_id          UUID NOT NULL,
    task_id             UUID NOT NULL,
    status              VARCHAR(255) NOT NULL,
    status_reason       VARCHAR(255) NOT NULL,
    description         VARCHAR(255),
    period              INTEGER,
    date                DATE NOT NULL
);
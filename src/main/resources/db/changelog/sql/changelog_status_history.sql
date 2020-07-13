--liquibase formatted sql
--changeset thabani:create_status_history_table
CREATE TABLE status_history(
    id                      UUID PRIMARY KEY,
    version                 BIGINT NOT NULL,
    deleted                 BOOLEAN,
    created                 TIMESTAMP,
    created_by              VARCHAR(255),
    last_modified_by        VARCHAR(255),
    last_modified_date      TIMESTAMP,
    status                  VARCHAR(255) NOT NULL,
    status_update_reason    VARCHAR(255),
    time_stamp              TIME NOT NULL,
    time_entry_id           UUID NOT NULL,
);
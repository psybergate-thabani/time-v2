package com.psybergate.resoma.time.repository;

import com.psybergate.resoma.time.entity.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TimeEntryRepository extends JpaRepository<TimeEntry, UUID> {
    List<TimeEntry> findAllByDeleted(Boolean deleted);

    TimeEntry findByIdAndDeleted(UUID entryId, boolean deleted);
}

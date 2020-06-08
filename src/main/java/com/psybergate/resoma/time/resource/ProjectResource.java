package com.psybergate.resoma.time.resource;

import java.util.UUID;

public interface ProjectResource {
    boolean validateProject(UUID projectId);

    boolean validateTask(UUID projectId, UUID taskId);
}

package com.psybergate.resoma.time.resource;

import com.psybergate.resoma.time.dto.ValidationDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface ProjectServiceClient {

    ValidationDTO validateProject(@PathVariable UUID projectId);

    ValidationDTO validateTask(@PathVariable UUID projectId, @PathVariable UUID taskId);

}

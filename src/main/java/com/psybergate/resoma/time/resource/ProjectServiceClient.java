package com.psybergate.resoma.time.resource;

import com.psybergate.resoma.time.dto.ValidationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "project-service", path = "/api/project")
public interface ProjectServiceClient {

    @GetMapping(path = "/v1/project-entries/{projectId}/validate")
    ValidationDTO validateProject(@PathVariable UUID projectId);

    @GetMapping("v1/project-entries/{projectId}/tasks/{taskId}/validate")
    ValidationDTO validateTask(@PathVariable UUID projectId, @PathVariable UUID taskId);

}

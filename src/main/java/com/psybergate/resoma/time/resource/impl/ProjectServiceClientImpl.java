package com.psybergate.resoma.time.resource.impl;

import com.psybergate.resoma.time.dto.ValidationDTO;
import com.psybergate.resoma.time.resource.ProjectServiceClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class ProjectServiceClientImpl implements ProjectServiceClient {

    private RestTemplate restTemplate;

    public ProjectServiceClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ValidationDTO validateProject(UUID projectId) {
        String url = String.format("http://project:8082/api/project/v1/project-entries/%s/validate", projectId);
        ValidationDTO validate = restTemplate.getForObject(url, ValidationDTO.class);
        return validate;
    }

    @Override
    public ValidationDTO validateTask(UUID projectId, UUID taskId) {
        String url = String.format("http://project:8082/api/project/v1/project-entries/%s/tasks/%s/validate", projectId, taskId);
        ValidationDTO validate = restTemplate.getForObject(url, ValidationDTO.class);
        return validate;
    }
}

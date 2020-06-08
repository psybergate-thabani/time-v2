package com.psybergate.resoma.time.resource.impl;

import com.psybergate.resoma.time.resource.ProjectResource;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.UUID;


@Component
public class ProjectResourceImpl implements ProjectResource {

    private RestTemplate restTemplate;

    public ProjectResourceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SneakyThrows
    @Override
    public boolean validateTask(UUID projectId, UUID taskId) {
        String url = String.format("http://localhost:8080/api/project/v1/projects/%s/tasks/%s?deleted=false", projectId, taskId);
        Boolean validate = restTemplate.getForObject(url, Boolean.class);
        if (Objects.isNull(validate))
            throw new InvocationTargetException(new Throwable(),"Unexpected type returned");

        return validate;
    }

    @SneakyThrows
    @Override
    public boolean validateProject(UUID projectId) {
        String url = String.format("http://localhost:8080/api/project/v1/projects/%s?deleted=false", projectId);
        Boolean validate = restTemplate.getForObject(url, Boolean.class);
        if (Objects.isNull(validate))
            throw new InvocationTargetException(new Throwable(),"Unexpected type returned");

        return validate;
    }
}

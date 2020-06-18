package com.psybergate.resoma.time.resource.impl;

import com.psybergate.resoma.time.resource.EmployeeResource;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.UUID;

@Component
public class EmployeeResourceImp implements EmployeeResource {

    private RestTemplate restTemplate;

    public EmployeeResourceImp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SneakyThrows
    @Override
    public boolean validateEmployee(UUID employeeId) {
        String url = String.format("http://localhost:8081/api/people/v1/employees/%s/valid?deleted=false", employeeId);
        Boolean validate = restTemplate.getForObject(url, Boolean.class);
        if (Objects.isNull(validate))
            throw new InvocationTargetException(new Throwable(), "Unexpected type returned");

        return validate;
    }
}

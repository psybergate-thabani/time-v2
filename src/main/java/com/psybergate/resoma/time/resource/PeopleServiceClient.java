package com.psybergate.resoma.time.resource;

import com.psybergate.resoma.time.dto.ValidationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "people-service", path = "/api/people")
public interface PeopleServiceClient {

    @GetMapping(path = "v1/employees/{employeeId}/validate?deleted=false")
    ValidationDTO validateEmployee(@PathVariable UUID employeeId);
}

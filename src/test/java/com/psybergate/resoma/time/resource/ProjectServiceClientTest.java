package com.psybergate.resoma.time.resource;

import com.psybergate.resoma.time.dto.ValidationDTO;
import com.psybergate.resoma.time.resource.impl.ProjectServiceClientImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceClientTest {

    private ProjectServiceClient projectServiceClient;
    @Mock
    private RestTemplate restTemplate;

    private String projectUrl;
    private String taskUrl;

    @BeforeEach
    void setup() {
        projectServiceClient = new ProjectServiceClientImpl(restTemplate);
        projectUrl = "http://project:8082/api/project/v1/project-entries/%s/validate";
        taskUrl = "http://project:8082/api/project/v1/project-entries/%s/tasks/%s/validate";
    }

    @Test
    public void shouldReturnTrue_whenGivenTaskThatExist() {
        //Arrange
        UUID projectId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        String url = String.format(taskUrl, projectId, taskId);
        when(restTemplate.getForObject(url, ValidationDTO.class)).thenReturn(new ValidationDTO(true));

        //Act
        ValidationDTO valid = projectServiceClient.validateTask(projectId, taskId);

        //Assert and Verify
        assertTrue(valid.getExist());
        verify(restTemplate).getForObject(url, ValidationDTO.class);
    }

    @Test
    public void shouldReturnFalse_whenGivenTaskThatDoesNotExist() {
        //Arrange
        UUID projectId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        String url = String.format(taskUrl, projectId, taskId);
        when(restTemplate.getForObject(url, ValidationDTO.class)).thenReturn(new ValidationDTO(false));

        //Act
        ValidationDTO valid = projectServiceClient.validateTask(projectId, taskId);

        //Assert and Verify
        assertFalse(valid.getExist());
        verify(restTemplate).getForObject(url, ValidationDTO.class);
    }

    @Test
    public void shouldReturnTrue_whenGivenProjectThatExist() {
        //Arrange
        UUID projectId = UUID.randomUUID();
        String url = String.format(projectUrl, projectId);
        when(restTemplate.getForObject(url, ValidationDTO.class)).thenReturn(new ValidationDTO(true));

        //Act
        ValidationDTO valid = projectServiceClient.validateProject(projectId);

        //Assert and Verify
        assertTrue(valid.getExist());
        verify(restTemplate).getForObject(url, ValidationDTO.class);
    }

    @Test
    public void shouldReturnFalse_whenGivenProjectThatDoesNotExist() {
        //Arrange
        UUID projectId = UUID.randomUUID();
        String url = String.format(projectUrl, projectId);
        when(restTemplate.getForObject(url, ValidationDTO.class)).thenReturn(new ValidationDTO(false));

        //Act
        ValidationDTO valid = projectServiceClient.validateProject(projectId);

        //Assert and Verify
        assertFalse(valid.getExist());
        verify(restTemplate).getForObject(url, ValidationDTO.class);
    }

}

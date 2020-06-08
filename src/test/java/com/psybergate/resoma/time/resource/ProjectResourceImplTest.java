package com.psybergate.resoma.time.resource;

import com.psybergate.resoma.time.resource.impl.ProjectResourceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectResourceImplTest {

    private ProjectResource projectResource;
    @Mock
    private RestTemplate mockRestTemplate;


    private String taskUrl;
    private String projectUrl;

    @BeforeEach
    void setup() {
        projectResource = new ProjectResourceImpl(mockRestTemplate);
        taskUrl = "http://localhost:8080/api/project/v1/projects/%s/tasks/%s?deleted=false";
        projectUrl = "http://localhost:8080/api/project/v1/projects/%s?deleted=false";
    }

    @Test
    public void shouldReturnTrue_whenGivenProjectThatExist() {
        //Arrange
        UUID projectId = UUID.randomUUID();
        String url = String.format(projectUrl, projectId);
        when(mockRestTemplate.getForObject(url, Boolean.class)).thenReturn(true);

        //Act
        boolean valid = projectResource.validateProject(projectId);

        //Assert and Verify
        assertTrue(valid);
        verify(mockRestTemplate).getForObject(url, Boolean.class);
    }

    @Test
    public void shouldReturnFalse_whenGivenProjectThatDoesNotExist() {
        //Arrange
        UUID projectId = UUID.randomUUID();
        String url = String.format(projectUrl, projectId);
        when(mockRestTemplate.getForObject(url, Boolean.class)).thenReturn(false);

        //Act
        boolean valid = projectResource.validateProject(projectId);

        //Assert and Verify
        assertFalse(valid);
        verify(mockRestTemplate).getForObject(url, Boolean.class);
    }

    @Test
    public void shouldThrowInvocationTargetException_whenGivenProjectThatIsNull() {
        //Arrange
        UUID projectId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        String url = String.format(projectUrl, projectId);
        when(mockRestTemplate.getForObject(url, Boolean.class)).thenReturn(null);

        //Act and Assert
        assertThrows(InvocationTargetException.class, () -> {
            boolean valid = projectResource.validateProject(projectId);
        });
    }

    @Test
    public void shouldReturnTrue_whenGivenTaskThatExist() {
        //Arrange
        UUID projectId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        String url = String.format(taskUrl, projectId, taskId);
        when(mockRestTemplate.getForObject(url, Boolean.class)).thenReturn(true);

        //Act
        boolean valid = projectResource.validateTask(projectId, taskId);

        //Assert and Verify
        assertTrue(valid);
        verify(mockRestTemplate).getForObject(url, Boolean.class);
    }

    @Test
    public void shouldReturnFalse_whenGivenTaskThatDoesNotExist() {
        //Arrange
        UUID projectId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        String url = String.format(taskUrl, projectId, taskId);
        when(mockRestTemplate.getForObject(url, Boolean.class)).thenReturn(false);

        //Act
        boolean valid = projectResource.validateTask(projectId, taskId);

        //Assert and Verify
        assertFalse(valid);
        verify(mockRestTemplate).getForObject(url, Boolean.class);
    }

    @Test
    public void shouldThrowInvocationTargetException_whenGivenTaskThatIsNull() {
        //Arrange
        UUID projectId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        String url = String.format(taskUrl, projectId, taskId);
        when(mockRestTemplate.getForObject(url, Boolean.class)).thenReturn(null);

        //Act and Assert
        assertThrows(InvocationTargetException.class, () -> {
            boolean valid = projectResource.validateTask(projectId, taskId);
        });
    }
}

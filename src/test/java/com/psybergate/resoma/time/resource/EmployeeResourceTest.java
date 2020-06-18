package com.psybergate.resoma.time.resource;

import com.psybergate.resoma.time.resource.impl.EmployeeResourceImp;
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
public class EmployeeResourceTest {

    private EmployeeResource employeeResourceImp;
    @Mock
    private RestTemplate restTemplate;

    private String employeeUrl;

    @BeforeEach
    void setup() {
        employeeResourceImp = new EmployeeResourceImp(restTemplate);
        employeeUrl = "http://localhost:8081/api/people/v1/employees/%s/valid?deleted=false";
    }

    @Test
    public void shouldReturnTrue_whenGivenEmployeeThatExist() {
        //Arrange
        UUID employeeId = UUID.randomUUID();
        String url = String.format(employeeUrl, employeeId);
        when(restTemplate.getForObject(url, Boolean.class)).thenReturn(true);

        //Act
        boolean valid = employeeResourceImp.validateEmployee(employeeId);

        //Assert and Verify
        assertTrue(valid);
        verify(restTemplate).getForObject(url, Boolean.class);
    }

    @Test
    public void shouldReturnFalse_whenGivenEmployeeThatDoesNotExist() {
        //Arrange
        UUID employeeId = UUID.randomUUID();
        String url = String.format(employeeUrl, employeeId);
        when(restTemplate.getForObject(url, Boolean.class)).thenReturn(false);

        //Act
        boolean valid = employeeResourceImp.validateEmployee(employeeId);

        //Assert and Verify
        assertFalse(valid);
        verify(restTemplate).getForObject(url, Boolean.class);
    }

    @Test
    public void shouldThrowInvocationTargetException_whenGivenEmployeeThatIsNull() {
        //Arrange
        UUID employeeId = UUID.randomUUID();
        String url = String.format(employeeUrl, employeeId);
        when(restTemplate.getForObject(url, Boolean.class)).thenReturn(null);

        //Act and Assert
        assertThrows(InvocationTargetException.class, () -> {
            boolean valid = employeeResourceImp.validateEmployee(employeeId);
        });
    }
}

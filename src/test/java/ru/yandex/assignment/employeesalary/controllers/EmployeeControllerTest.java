package ru.yandex.assignment.employeesalary.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yandex.assignment.employeesalary.domain.Employee;
import ru.yandex.assignment.employeesalary.domain.Salary;
import ru.yandex.assignment.employeesalary.services.EmployeeService;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void createEmployeeSuccess() {
        Employee employee = new Employee();
        employee.name = "Вячеслав Казаков";
        employee.salary = new Salary();
        employee.salary.value = BigDecimal.valueOf(342.22);

        Employee mockAnswerEmployee = new Employee();
        mockAnswerEmployee.id = 234L;
        mockAnswerEmployee.name = "Вячеслав Казаков";
        mockAnswerEmployee.salary = new Salary();
        mockAnswerEmployee.salary.id = 2342L;
        mockAnswerEmployee.salary.value = BigDecimal.valueOf(342.22);
        BDDMockito.given(employeeService.createEmployee(employee)).willReturn(mockAnswerEmployee);

        Employee methodResult = employeeController.createEmployee(employee);
        assertEquals(mockAnswerEmployee, methodResult);
    }

    @Test
    public void createEmployeeFailureNoBody() {
        boolean excCaught = false;
        try {
            employeeController.createEmployee(null);
        } catch (IllegalArgumentException exc) {
            excCaught = true;
        }
        assertTrue(excCaught);
        verify(employeeService, never()).createEmployee(null);
    }

    @Test
    public void createEmployeeFailureNoName() {
        Employee employee = new Employee();
        employee.salary = new Salary();
        employee.salary.value = BigDecimal.valueOf(3242);

        boolean excCaught = false;
        try {
            employeeController.createEmployee(employee);
        } catch (IllegalArgumentException exc) {
            excCaught = true;
        }
        assertTrue(excCaught);
        verify(employeeService, never()).createEmployee(employee);
    }

    @Test
    public void createEmployeeFailureNoSalary() {
        Employee employee = new Employee();
        employee.name = "Владимир Богатов";

        boolean excCaught = false;
        try {
            employeeController.createEmployee(employee);
        } catch (IllegalArgumentException exc) {
            excCaught = true;
        }
        assertTrue(excCaught);
        verify(employeeService, never()).createEmployee(employee);

        employee.salary = new Salary();
        excCaught = false;
        try {
            employeeController.createEmployee(employee);
        } catch (IllegalArgumentException exc) {
            excCaught = true;
        }
        assertTrue(excCaught);
        verify(employeeService, never()).createEmployee(employee);
    }

}

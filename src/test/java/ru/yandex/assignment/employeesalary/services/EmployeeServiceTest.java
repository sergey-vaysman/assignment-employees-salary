package ru.yandex.assignment.employeesalary.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yandex.assignment.employeesalary.domain.Employee;
import ru.yandex.assignment.employeesalary.domain.Salary;
import ru.yandex.assignment.employeesalary.persistence.EmployeeDAO;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeDAO employeeDAO;

    @Test
    public void testCreateEmployee() {
        Employee employee = new Employee();
        employee.name = "Андрей Степанов";
        employee.salary = new Salary();
        employee.salary.value = BigDecimal.valueOf(3526.32);

        Employee mockAnswerEmployee = new Employee();
        mockAnswerEmployee.id = 152L;
        mockAnswerEmployee.name = "Андрей Степанов";
        mockAnswerEmployee.salary = new Salary();
        mockAnswerEmployee.salary.id = 313L;
        mockAnswerEmployee.salary.value = BigDecimal.valueOf(3526.32);
        BDDMockito.given(employeeDAO.createEmployee(employee)).willReturn(mockAnswerEmployee);

        Employee methodResult = employeeService.createEmployee(employee);
        assertEquals(mockAnswerEmployee, methodResult);
    }

}

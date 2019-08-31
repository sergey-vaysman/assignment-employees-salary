package ru.yandex.assignment.employeesalary.services;

import org.springframework.stereotype.Service;
import ru.yandex.assignment.employeesalary.domain.Employee;
import ru.yandex.assignment.employeesalary.domain.Salary;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {

    public Employee createEmployee(Employee employee) {
        // TODO:
        throw new UnsupportedOperationException();
    }

    public Employee editSalary(Long employeeId, Salary salary) {
        // TODO:
        throw new UnsupportedOperationException();
    }

    public List<Employee> getAllEmployees() {
        // TODO: implement

        Employee employee1 = new Employee();
        employee1.id = 1L;
        employee1.name = "employee1";
        employee1.salary = new Salary();
        employee1.salary.id = 1L;
        employee1.salary.value = BigDecimal.valueOf(1500L);

        Employee employee2 = new Employee();
        employee1.id = 2L;
        employee1.name = "employee1";
        employee1.salary = new Salary();
        employee1.salary.id = 2L;
        employee1.salary.value = BigDecimal.valueOf(1800L);

        return Arrays.asList(employee1, employee2);
    }

    public void removeEmployee(Long employeeId) {
        // TODO:
        throw new UnsupportedOperationException();
    }


}

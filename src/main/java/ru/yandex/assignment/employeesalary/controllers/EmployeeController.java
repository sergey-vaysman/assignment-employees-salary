package ru.yandex.assignment.employeesalary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.assignment.employeesalary.domain.Employee;
import ru.yandex.assignment.employeesalary.domain.Salary;
import ru.yandex.assignment.employeesalary.services.EmployeeService;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/api/create_employee")
    public Employee createEmployee(Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @PostMapping("/api/edit_salary")
    public Employee editSalary(Long employeeId, Salary salary) {
        return employeeService.editSalary(employeeId, salary);
    }

    @GetMapping("/api/all_employees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping("/api/remove_employee")
    public void removeEmployee(Long employeeId) {
        employeeService.removeEmployee(employeeId);
    }

}

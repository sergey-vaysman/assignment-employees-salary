package ru.yandex.assignment.employeesalary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.assignment.employeesalary.domain.Employee;
import ru.yandex.assignment.employeesalary.services.EmployeeService;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/api/create_employee")
    public Employee createEmployee(@RequestBody Employee employee) {
        boolean isDataValid = employee != null && employee.name != null && employee.salary != null && employee.salary.value != null;
        if (!isDataValid) throw new IllegalArgumentException("Invalid request data");
        return employeeService.createEmployee(employee);
    }

    @PostMapping("/api/edit_employee")
    public void editEmployee(@RequestBody Employee employee) {
        boolean isDataValid = employee != null && employee.id != null && employee.salary != null && employee.salary.value != null;
        if (!isDataValid) throw new IllegalArgumentException("Invalid request data");
        employeeService.editSalary(employee.id, employee.salary);
    }

    @GetMapping("/api/all_employees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping("/api/remove_all_employees")
    public void removeEmployee() {
        employeeService.removeAllEmployees();
    }

}

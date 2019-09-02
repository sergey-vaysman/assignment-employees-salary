package ru.yandex.assignment.employeesalary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.assignment.employeesalary.domain.Employee;
import ru.yandex.assignment.employeesalary.domain.Salary;
import ru.yandex.assignment.employeesalary.persistence.EmployeeDAO;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDAO dao;

    public Employee createEmployee(Employee employee) {
        return dao.createEmployee(employee);
    }

    public void editSalary(Long employeeId, Salary salary) {
        dao.editSalary(employeeId, salary);
    }

    public List<Employee> getAllEmployees() {
        return dao.getAllEmployees();
    }

    public void removeAllEmployees() {
        dao.removeAllEmployees();
    }


}

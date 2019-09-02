package ru.yandex.assignment.employeesalary.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.assignment.employeesalary.domain.Employee;
import ru.yandex.assignment.employeesalary.domain.Salary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert employeeInsert;
    private final SimpleJdbcInsert salaryInsert;

    @Autowired
    public EmployeeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        employeeInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("employee").usingGeneratedKeyColumns("id");
        salaryInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("salary").usingGeneratedKeyColumns("id");
    }

    @Transactional
    public Employee createEmployee(Employee employee) {
        Map<String, Object> params = new HashMap<>();
        params.put("value", employee.salary.value);
        employee.salary.id = salaryInsert.executeAndReturnKey(params).longValue();
        params.clear();
        params.put("name", employee.name);
        params.put("salary_id", employee.salary.id);
        employee.id = employeeInsert.executeAndReturnKey(params).longValue();
        return employee;
    }

    public List<Employee> getAllEmployees() {
        return jdbcTemplate.query("SELECT e.id, e.name, s.id, s.value FROM employee e INNER JOIN salary s ON s.id = e.salary_id",
                (rs, rowNum) -> {
                    Employee employee = new Employee();
                    employee.id = rs.getLong(1);
                    employee.name = rs.getString(2);
                    employee.salary = new Salary();
                    employee.salary.id = rs.getLong(3);
                    employee.salary.value = rs.getBigDecimal(4);
                    return employee;
                });
    }

    @Transactional
    public void editSalary(Long employeeId, Salary salary) {
        Long salaryId = jdbcTemplate.queryForObject("SELECT salary_id FROM employee WHERE id = ?", new Object[]{employeeId}, (rs, rowNum) -> rs.getLong(1));
        if (salaryId == null) throw new RuntimeException("Указанный сотрудник не найден в БД");
        jdbcTemplate.update("UPDATE salary SET value = ? WHERE id = ?", salary.value, salaryId);
    }

    @Transactional
    public void removeAllEmployees() {
        jdbcTemplate.update("DELETE FROM employee");
        jdbcTemplate.update("DELETE FROM salary");
    }

}

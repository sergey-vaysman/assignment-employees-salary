package ru.yandex.assignment.employeesalary.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.junit4.SpringRunner;
import ru.yandex.assignment.employeesalary.domain.Employee;
import ru.yandex.assignment.employeesalary.domain.Salary;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeDAOTest {

    @Test
    public void testCreateEmployee() {
        JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
        SimpleJdbcInsert employeeInsert = new EmployeeInsertStub(jdbcTemplate);
        SimpleJdbcInsert salaryInsert = new SalaryInsertStub(jdbcTemplate);
        EmployeeDAO employeeDAO = new EmployeeDAO(jdbcTemplate, employeeInsert, salaryInsert);

        Employee employee = new Employee();
        employee.name = "Константин Васильев";
        employee.salary = new Salary();
        employee.salary.value = BigDecimal.valueOf(83942.32);

        Employee methodResult = employeeDAO.createEmployee(employee);
        assertNotNull(methodResult);
        assertEquals(Long.valueOf(241L), methodResult.id);
        assertEquals("Константин Васильев", methodResult.name);
        assertNotNull(methodResult.salary);
        assertEquals(Long.valueOf(654L), methodResult.salary.id);
        assertEquals(BigDecimal.valueOf(83942.32), methodResult.salary.value);
    }

    private static class SalaryInsertStub extends SimpleJdbcInsert {

        SalaryInsertStub(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate);
        }

        @Override
        public Number executeAndReturnKey(Map<String, ?> args) {
            if (args == null || args.size() != 1) return null;
            if (!args.get("value").equals(BigDecimal.valueOf(83942.32))) return null;
            return 654L;
        }
    }

    private static class EmployeeInsertStub extends SimpleJdbcInsert {

        EmployeeInsertStub(JdbcTemplate jdbcTemplate) {
            super(jdbcTemplate);
        }

        @Override
        public Number executeAndReturnKey(Map<String, ?> args) {
            if (args == null || args.size() != 2) return null;
            if (!args.get("name").equals("Константин Васильев")) return null;
            if (!args.get("salary_id").equals(654L)) return null;
            return 241L;
        }
    }

}

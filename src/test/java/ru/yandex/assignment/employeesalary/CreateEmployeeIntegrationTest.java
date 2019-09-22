package ru.yandex.assignment.employeesalary;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.assignment.employeesalary.domain.Employee;
import ru.yandex.assignment.employeesalary.domain.Salary;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Commit
public class CreateEmployeeIntegrationTest {

    private final Logger logger = LoggerFactory.getLogger(CreateEmployeeIntegrationTest.class);

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Employee restRequestData;
    private Long createdEmployeeId;
    private Long createdSalaryId;

    @Before
    public void before() {
        restRequestData = new Employee();
        restRequestData.name = "Сергей Иванов";
        restRequestData.salary = new Salary();
        restRequestData.salary.value = BigDecimal.valueOf(1352.43D);
    }

    @Test
    public void test() {
        logger.info("Проверка ответа REST");
        ResponseEntity<Employee> restResponse = testRestTemplate.postForEntity("/api/create_employee", restRequestData, Employee.class);
        assertEquals(HttpStatus.OK, restResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, restResponse.getHeaders().getContentType());
        Employee createdEmployee = restResponse.getBody();
        assertNotNull(createdEmployee);
        assertNotNull(createdEmployee.id);
        assertEquals("Сергей Иванов", createdEmployee.name);
        assertNotNull(createdEmployee.salary);
        assertNotNull(createdEmployee.salary.id);
        assertEquals(BigDecimal.valueOf(1352.43D), createdEmployee.salary.value);

        createdEmployeeId = createdEmployee.id;
        createdSalaryId = createdEmployee.salary.id;

        logger.info("Проверка сохранения данных");
        List<Long> maxIdQueryResult = jdbcTemplate.query("SELECT MAX(id) FROM employee", (rs, rowNum) -> rs.getLong(1));
        assertEquals(1, maxIdQueryResult.size());
        assertEquals(createdEmployeeId, maxIdQueryResult.get(0));
        String sql = "SELECT e.name, s.id, s.value FROM employee e INNER JOIN salary s ON s.id = e.salary_id WHERE e.id = ?";
        List<EmployeeMapper> employeeQueryResult = jdbcTemplate.query(sql, new Object[]{createdEmployeeId}, (rs, rowNum) -> {
            String name = rs.getString(1);
            Long salaryId = rs.getLong(2);
            BigDecimal salary = rs.getBigDecimal(3);
            return new EmployeeMapper(name, salaryId, salary);
        });
        assertEquals(1, employeeQueryResult.size());
        assertEquals(new EmployeeMapper("Сергей Иванов", createdSalaryId, BigDecimal.valueOf(1352.43D)), employeeQueryResult.get(0));
    }

    private static class EmployeeMapper {
        final String name;
        final Long salaryId;
        final BigDecimal salary;

        EmployeeMapper(String name, Long salaryId, BigDecimal salary) {
            this.name = name;
            this.salaryId = salaryId;
            this.salary = salary;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EmployeeMapper that = (EmployeeMapper) o;
            return Objects.equals(name, that.name) &&
                    Objects.equals(salaryId, that.salaryId) &&
                    Objects.equals(salary, that.salary);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, salaryId, salary);
        }
    }


    @After
    public void after() {
        if (createdEmployeeId != null) {
            jdbcTemplate.update("DELETE FROM employee WHERE id = ?", createdEmployeeId);
        }
        if (createdSalaryId != null) {
            jdbcTemplate.update("DELETE FROM salary WHERE id = ?", createdSalaryId);
        }
    }

}

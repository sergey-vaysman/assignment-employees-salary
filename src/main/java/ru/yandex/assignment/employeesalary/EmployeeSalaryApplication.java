package ru.yandex.assignment.employeesalary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class EmployeeSalaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeSalaryApplication.class, args);
	}

	@Configuration
	protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication().withUser("USER").password("USER").roles("USER");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.httpBasic()
					.and()
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/api/edit_employee").hasRole("USER")
					.and()
					.csrf().disable()
					.formLogin().disable();
		}
	}

}

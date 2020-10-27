package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
@Service
public class EmpService {

	@Autowired
	JdbcTemplate jdbcTemplate;
	//get all emp
	public List <Employee> getAllEmployees() {
		String sql="SELECT * FROM employees";
		List<Employee> emp=jdbcTemplate.query(sql,new EmployeeMapper());
		return emp;

	}


	//get emp  by id
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId) throws EmployeeNotPresent
	{

		int userCount=jdbcTemplate.queryForObject("select count(*) from employees where id=?",new Object[] {employeeId} , Integer.class);
		if(userCount==0)
		{
			throw new EmployeeNotPresent();

		}
		String query = "SELECT * FROM employees WHERE id=?";
		Employee emp = jdbcTemplate.queryForObject(query,new Object[]{employeeId},new EmployeeMapper());
		return ResponseEntity.ok().body(emp);
	}

	//post
	public int createEmployee(@RequestBody Employee employee) {

		//Optional<Employee> employee1 = Optional.ofNullable(employeeRepository.findByEmailId(employee.getEmailId()));
		String sqlQuery = "insert into employees(firstName,lastName,emailId) values (?, ?, ?)";
		return  jdbcTemplate.update(sqlQuery, 
				employee.getFirstName(), 
				employee.getLastName(), 
				employee.getEmailId());

	}



	public int updateEmployee(@PathVariable(value = "id") Long employeeId,	@RequestBody Employee employeeDetails)
	{
		String sqlQuery = "update employees set firstName = ?, lastName = ?, emailId = ? where id = ?";
		return jdbcTemplate.update(sqlQuery,employeeDetails.getFirstName(),employeeDetails.getLastName(),employeeDetails.getEmailId(),employeeId);

	}



	public int deleteEmployee(@PathVariable(value = "id") Long employeeId)
	{

		int rowsAffected=jdbcTemplate.update("delete from employees where id=?",new Object[] {employeeId});

		if(rowsAffected==0)
		{
			throw new EmployeeNotFound();
		}
		return rowsAffected;

	}

	private static final class EmployeeMapper implements RowMapper<Employee>
	{

		@Override
		public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
			Employee emp=new Employee();
			emp.setId(rs.getLong("id"));
			emp.setFirstName(rs.getString("firstName"));
			emp.setLastName(rs.getString("lastName"));
			emp.setEmailId(rs.getString("emailId"));
			return emp;


		}

	}


}



package com.example.demo;

//jdbctemplate
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Employees")
public class EmpController {

	@Autowired
	EmpService empService;

	@GetMapping("/")
	public List <Employee> getAll() {
		return empService.getAllEmployees();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getEmployeeById(@PathVariable(value = "id") Long employeeId) throws EmployeeNotPresent 
	{
		return ResponseEntity.ok().body(empService.getEmployeeById(employeeId));
	}
	@PostMapping("/")
	public int createEmployee(@RequestBody Employee employee)  {

		return empService.createEmployee(employee);

	}
	
	@PutMapping("/{id}")
	public int updateEmployee(@PathVariable(value = "id") Long employeeId,
			@RequestBody Employee employeeDetails) {
		return empService.updateEmployee(employeeId, employeeDetails);
	}
	
	@DeleteMapping("/{id}")
	public int deleteEmployee(@PathVariable(value = "id") Long employeeId)
	{
		return empService.deleteEmployee(employeeId);
	}


}





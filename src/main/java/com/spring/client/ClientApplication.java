package com.spring.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.model.Employee;

@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {

//------------------------------ 1st test -----------------------------		
		RestTemplate restTemplate = new RestTemplate();
		URI targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/SpringRestExample/rest/emp/")
				.path("1").build().encode().toUri();
		Employee employee = restTemplate.getForObject(targetUrl, Employee.class);
		System.out.println("\n\n Employee is " + employee.toString());

//------------------------------ 2nd test -----------------------------		
		Employee newEmployee = new Employee();
		newEmployee.setId(77);
		newEmployee.setName("Adding Employee");
		URI targetUrl1 = UriComponentsBuilder.fromUriString("http://localhost:8080/SpringRestExample/rest/emp/create")
				.build().encode().toUri();
		Employee returnedEmployee = restTemplate.postForObject(targetUrl1, newEmployee, Employee.class);
		System.out.println("\n\nSaved&Returned employee is " + returnedEmployee.toString());

//------------------------------ 3rd test -----------------------------		
		List<Employee> temp = new ArrayList<>();
		Employee employee1 = new Employee();
		employee1.setId(55);
		employee1.setName("employee1");
		Employee employee2 = new Employee();
		employee2.setId(56);
		employee2.setName("employee2");
		temp.add(employee1);
		temp.add(employee2);
		URI targetUrlList = UriComponentsBuilder
				.fromUriString("http://localhost:8080/SpringRestExample/rest/emps/create").build().encode().toUri();
		ParameterizedTypeReference<List<Employee>> typeRef = new ParameterizedTypeReference<List<Employee>>() {
		};
		ResponseEntity<List<Employee>> responseEntity = restTemplate.exchange(targetUrlList, HttpMethod.POST,
				new HttpEntity<>(temp), typeRef);
		List<Employee> employees = responseEntity.getBody();
		for (Employee e : employees) {
			System.out
					.println("\nPost List employee are " + e.getId() + "  " + e.getName() + "  " + e.getCreatedDate());
		}

//------------------------------ 4th test -----------------------------		
		URI targetUrl2 = UriComponentsBuilder.fromUriString("http://localhost:8080/SpringRestExample/rest/emps").build()
				.encode().toUri();
		ResponseEntity<List<Employee>> responseEntity1 = restTemplate.exchange(targetUrl2, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Employee>>() {
				});
		List<Employee> yees = responseEntity1.getBody();
		System.out.println("\n\nSize of employees is " + yees.size());
		for (Employee e : yees) {
			System.out.println("Employee is " + e.toString());
		}

	}

}

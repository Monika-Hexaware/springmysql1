package com.springmysql.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springmysql.controller.EmployeeController;
import com.springmysql.entity.Employee;
import com.springmysql.service.EmployeeService;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {
	@Mock
	private EmployeeService service;

	@InjectMocks
	private EmployeeController controller;
	
	private List<Employee> prepareEmployeeRecords(){
		List<Employee> employee = new ArrayList<Employee>();
		Employee employee1 = new Employee(5L, "fbbAV");
		Employee employee2 = new Employee(4L, "L6KjF");
		employee.add(employee1);
		employee.add(employee2);
		return employee;
	}
	
	@Test
	public void testFetchAllPass() {
		Mockito
        .when(controller.fetchAll()).thenReturn(prepareEmployeeRecords());
		List<Employee> employee = prepareEmployeeRecords();
		List<Employee> employeeFromController =  controller.fetchAll();
		for(int i=0; i < employee.size();i++) { 
			Assertions.assertEquals(employee.get(i).getId(), employeeFromController.get(i).getId());
            Assertions.assertEquals(employee.get(i).getName(), employeeFromController.get(i).getName());
		}
		
	}

	@Test
	public void testFetchAllFailure() {
		Mockito
        .when(controller.fetchAll()).thenReturn(prepareEmployeeRecords());
		List<Employee> employee = null; //Intentionally made null to fail the test.
		List<Employee> employeeFromController =  controller.fetchAll();
		Assertions.assertNotEquals(employee, employeeFromController);
	}
	
	
	 @Test public void fetchByIdPass() { 
		 Mockito
	        .when(controller.fetchById(5L)).thenReturn(prepareEmployeeRecords().get(0));
			Employee employeeById = prepareEmployeeRecords().get(0);
			Employee employeeByIdFromController = controller.fetchById(5L);
			
			Assertions.assertEquals(employeeById.getId(), employeeByIdFromController.getId());
        Assertions.assertEquals(employeeById.getName(), employeeByIdFromController.getName());
	 }

	 @Test public void fetchByIdFailure() { 
		 Mockito
	        .when(controller.fetchById(5L)).thenReturn(prepareEmployeeRecords().get(0));
			Employee employeeById = prepareEmployeeRecords().get(1);
			Employee employeeByIdFromController = controller.fetchById(5L);
			
			Assertions.assertNotEquals(employeeById.getId(), employeeByIdFromController.getId());
        Assertions.assertNotEquals(employeeById.getName(), employeeByIdFromController.getName());
	 }
	 
	 @Test
	 public void deletePass() { 
		 controller.delete(5L);
		 Assertions.assertTrue(true); // This line will be executed only if there is no error in calling the controller for delete as there is no return value.
	 }

	@Test
	public void createPass() {
		Employee employeeToBeCreated 			= prepareEmployeeRecords().get(0);
		Employee employeeReturned = prepareEmployeeRecords().get(0);
		employeeReturned.setId(7L); //Changed the ID.
		
		Mockito
			.when(controller.create(employeeToBeCreated)).thenReturn(employeeReturned);
		
        Employee employeeFromController  = controller.create(employeeToBeCreated);
		Assertions.assertNotEquals(employeeToBeCreated.getId(), employeeFromController.getId()); //Since Id of created one is mocked as changed from within serviceid, it cannot be equal.
        Assertions.assertEquals(employeeToBeCreated.getName(), employeeFromController.getName());
}
	
	@Test
	public void createFailure() {
		Employee employeeToBeCreated 			= prepareEmployeeRecords().get(0);
		Employee employeeReturned 			= null; // Intentionally left to null to fail the case. 
				
		Mockito
			.when(controller.create(employeeToBeCreated)).thenReturn(employeeReturned);
		
        Employee employeeFromController  = controller.create(employeeToBeCreated);
		Assertions.assertNull(employeeFromController);
	}
	
	/*
	 * @Test public void update() { ResponseEntity<Object> responseObject = null;
	 * 
	 * Mockito.when(controller.update(employeeToBeUpdated,
	 * "")).thenReturn(responseObject); }
	 */	
}
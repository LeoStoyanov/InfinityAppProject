package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class handles all of the client to backend requests
 * all of these classes hand all requests off to the Service
 * Layer.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController
{
    //Creates a variable that references the EmployeeService
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService)
    {
        super();
        this.employeeService = employeeService;
    }


    /**
     * Takes a new employee and adds it the the DB and will return
     * a status request
     * @param employee - the new employee that is to be added to DB
     * @return - the status code of result/content of employee added
     */
    @PostMapping
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee)
    {
        return new ResponseEntity<>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
    }


    /**
     * Goes to the DB and lists all of the employees
     * in an array of JSON format
     * @return - List of all employees
     */
    @GetMapping
    public List<Employee> getAllEmployees()
    {
        return employeeService.getAllEmployees();
    }

    /**
     * Gets a specific Employee out of the DB.
     *
     * @param id - the ID of employee needed
     * @return - the content of employee of set ID/result of status code
     */
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeByID(@PathVariable("id") long id)
    {
        return new ResponseEntity<Employee>(employeeService.getEmployeeByID(id), HttpStatus.OK);
    }

    /**
     * Updates the content of an existing employee
     *
     * @param id - id of employee in need of update
     * @param employee - creates new employee to copy over content into existing employee
     * @return - Content of new updated employee/ result of status code
     */
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee)
    {
        return new ResponseEntity<Employee>(employeeService.updateEmployee(employee,id),HttpStatus.OK);
    }

    /**
     * Deletes an employee of given ID
     *
     * @param id - ID of employee being deleted
     * @return - a string over success or failure
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") long id)
    {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<String>("Employee deleted successfully", HttpStatus.OK);
    }

}

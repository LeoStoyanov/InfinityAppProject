package com.example.demo.service;

import com.example.demo.model.Employee;

import java.util.List;

/**
 * This is an Interface of what Services need to be implemented
 * All of these are used by the controller
 */
public interface EmployeeService
{
    /**
     * Saves an employee into a DB
     *
     * @param employee - employee being added
     * @return - the employee added
     */
    Employee saveEmployee (Employee employee);

    /**
     *
     * @return - a list of all employee objects
     */
    List<Employee> getAllEmployees();

    /**
     * Gets a specific employee by id
     *
     * @param id - ID of specific Employee
     * @return - the Employee at given ID
     */
    Employee getEmployeeByID(long id);

    /**
     *
     * @param employee - the new information that needs to be input
     * @param id - id of employee that is being updated
     * @return - updated employee
     */
    Employee updateEmployee(Employee employee, long id);

    /**
     *
     * @param id - ID of employee being deleted
     */
    void deleteEmployee (long id);
}

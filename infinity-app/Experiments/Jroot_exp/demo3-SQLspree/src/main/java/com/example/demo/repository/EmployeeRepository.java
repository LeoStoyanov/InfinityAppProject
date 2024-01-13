package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

//The JPARepo requires 2 params
//first is the type of entity, in this case is Employee
//Second is the type of primary key (this case is ID, which is a LONG)

public interface EmployeeRepository extends JpaRepository<Employee,Long>
{

}

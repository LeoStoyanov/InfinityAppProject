package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController
{

    //A Java bean (JSON)
    @GetMapping("/student")
    public Student getStudent()
    {
        return new Student("James","Root");
    }

    /*
    //http://localhost:8080/students
    creates a lost of students and shows it in a JSON list
     */
    @GetMapping("/students")
    public List<Student> getStudents()
    {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Cale", "Ros"));
        students.add(new Student("Aaron", "Mrbigshot"));
        students.add(new Student("Leo", "HitHisKneeo"));
        students.add(new Student("poopoo", "peepee"));

        return students;
    }

    //Path Variables
    //http://localhost:8080/student/James/Root
    @GetMapping("{firstName}/{lastName}")
    public Student studentPathVar(@PathVariable("firstName") String firstName,@PathVariable("lastName") String lastName)
    //also allowed to do @PathVariable("firstName") String firstNameblahblah,@PathVariable("lastName") String last)
    //it will also work as long at PathVar is the same as mapping
    {
        return new Student(firstName,lastName);
    }

    //@RequestParams
    //http://localhost:8080/student/query?firstName=James&lastName=Root
    @GetMapping("/student/query")
    public Student studentQueryParam(@RequestParam(name = "firstName") String firstName,@RequestParam(name = "lastName") String lastName)
    {
        return new Student(firstName,lastName);
    }


}

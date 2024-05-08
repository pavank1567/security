package com.example.demo.controller;


import com.example.demo.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {


    List<Student> studentList = Arrays.asList(
            new Student(1,"Pavan"),
            new Student(2,"Kalyan"),
            new Student(3,"Roman")
    );


    @GetMapping("/get/{id}")
    public Student getStudentWithId(@PathVariable("id") Integer studentId){

        return studentList.stream()
                .filter(student -> student.getStudentId().equals(studentId)).
                findFirst()
                .orElseThrow(() -> new IllegalStateException("Student not found with given id!" + studentId));

    }
}

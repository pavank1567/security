package com.example.demo.controller;

import com.example.demo.model.Student;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/manage/students")
public class StudentManagementController {
    List<Student> studentList = new ArrayList<>();


    public StudentManagementController(){
        studentList.add(new Student(1,"Pavan"));
        studentList.add(new Student(2,"Kalyan"));
        studentList.add(new Student(3,"Roman"));
    }

    @GetMapping
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE')")
    public List<Student> getStudents(){
        return studentList;
    }

    @PostMapping
//    @PreAuthorize("hasAuthority('student:write')")
    public void addStudent(@RequestBody Student student){
        studentList.add(student);
    }

//    @PostMapping
//    public void addStudent(@RequestBody List<Student> student){
//        studentList.addAll(student);
//    }


}

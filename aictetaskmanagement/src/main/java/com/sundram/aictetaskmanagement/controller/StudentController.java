package com.sundram.aictetaskmanagement.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sundram.aictetaskmanagement.model.Student;
import com.sundram.aictetaskmanagement.repository.StudentRepository;

@RestController
public class StudentController {
    
    Logger logger = Logger.getLogger("StudentController logger");

    @Autowired
    StudentRepository studentRepository;

    @PostMapping("/student")
    public Student saveStudent(@RequestBody Student students){
        logger.info("Initiating of create student");
        Student student= studentRepository.save(students);
        return student;
    }

    @GetMapping("/student/{id}")
    public Student getStudent(@PathVariable(name = "id") String studentId){
        logger.info("Starting getting student by id = "+ studentId.toString());
        Student student= studentRepository.getStudentBystudentId(Integer.parseInt(studentId));
        return student;
    }
    /* Delete Operation not working */
    @DeleteMapping("/student/{id}")
    public String deleteStudent(@PathVariable(name = "id") String studentId){
        logger.info("Starting Deleting student with student id = "+ studentId.toString());
        Student student = studentRepository.getStudentBystudentId(Integer.parseInt(studentId));
        try{
            studentRepository.delete(student);
            return "Successfully Deleted student " + studentId;
        }catch(Exception e){
            return "Student Not Found with student id" + studentId;
        }
    }
}

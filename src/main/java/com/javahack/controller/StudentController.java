package com.javahack.controller;

import com.javahack.exception.ResourceNotFoundException;
import com.javahack.model.Student;
import com.javahack.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public List<Student> getStudents(@RequestParam String clientKey){
        log.info("Client: {}", clientKey);
        return studentService.findAll();
    }

    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable(value="studentId") Long studentId){
        return studentService.findById(studentId).orElseThrow(() ->new ResourceNotFoundException("studentId "+ studentId + " not found"));
    }

    @PostMapping("/students")
    public String createStudent(@RequestBody Student student) {
        Student studentAdded= studentService.save(student);
        return "Student added: StudentId="+studentAdded.getId();
    }

    @PutMapping("/students/{studentId}")
    public String updateStudent(@PathVariable(value="studentId") Long studentId, @RequestBody Student student){
        return studentService.findById(studentId).map(s -> {
            s.setName(student.getName());
            s.setCourse(student.getCourse());
            s.setFee(student.getFee());
            studentService.save(s);
            return "Student updated: studentId="+studentId;
        }).orElseThrow(() ->new ResourceNotFoundException("studentId "+ studentId + " not found"));


    }

    @DeleteMapping("/students/{studentId}")
    public String deleteStudent(@PathVariable(value="studentId") Long studentId){
        return studentService.findById(studentId).map(s -> {
            studentService.deleteById(s.getId());
            return "Student deleted: StudentId="+studentId;
        }).orElseThrow(() ->new ResourceNotFoundException("studentId "+ studentId + " not found"));
    }
}

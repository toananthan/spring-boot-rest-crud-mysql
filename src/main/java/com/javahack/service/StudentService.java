package com.javahack.service;

import com.javahack.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student save(Student student);
    void deleteById(Long id);
    Optional<Student> findById(Long id);
    List<Student> findAll();
}

package org.hogwarts.school.service;

import org.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {

        Student add(String name, int age);

        Student update(long id, String name, Integer age);

        Student delete(Long id);

        Student get(Long id);

        List<Student> getByAge(int age);

        Collection<Student> getAll();
    }


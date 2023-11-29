package org.hogwarts.school.service;

import org.hogwarts.school.model.Student;
import org.hogwarts.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentService {
    @Autowired
    private final StudentRepository repository;
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student add(String name, int age) {
        Student newStudent = new Student( name,age);
        newStudent = repository.save(newStudent);
        return newStudent;
    }


    public Student update(long id, String name, Integer age) {
        Student studentForUpdate = get(id);
        studentForUpdate.setName(name);
        studentForUpdate.setAge(age);
        return repository.save(studentForUpdate);
    }

    public Student delete(Long id) {
        Student studentForDelete = get(id);
        repository.deleteById(id);
        return studentForDelete;
    }

  public Student get(Long id) {
      return repository.findById(id).orElse(null); // вывели
   }


//    public List<Student> getByAge(int age) {
//        return repository.findAllByAge(age);
//    }
//
//
//    public Collection<Student> getAll() {
//        return repository.findAll();
//    }


}


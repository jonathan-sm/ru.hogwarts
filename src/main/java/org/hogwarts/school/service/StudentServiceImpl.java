package org.hogwarts.school.service;

import org.hogwarts.school.model.Faculty;
import org.hogwarts.school.model.Student;
import org.hogwarts.school.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;
    public StudentServiceImpl(StudentRepository repository) {
        this.studentRepository = repository;
    }


    public Student add(String name, int age) {
        logger.info("Был вызван метод add");
        Student newStudent = new Student(name, age);
        newStudent = studentRepository.save(newStudent);
        return newStudent;
    }
    //    public Student add(Student student) { return repository.save(student); // добавили
//}
    @Override
    public Student update(long id, String name, Integer age) {
        logger.info("Был вызван метод update");
        Student studentForUpdate = get(id);
        studentForUpdate.setName(name);
        studentForUpdate.setAge(age);
        return studentRepository.save(studentForUpdate);
    }
    @Override
    public Student delete(Long id) {
        logger.info("Был вызван метод delete");
        Student studentForDelete = get(id);
        studentRepository.deleteById(id);
        return studentForDelete;
    }
    @Override
    public Student get(Long id) {
        logger.info("Был вызван метод get");
        return studentRepository.findById(id).orElse(null); // вывели
    }

    @Override
    public List<Student> getByAge(int age) {
        logger.info("Был вызван метод getByAge");
        return studentRepository.findAllByAge(age);
    }

    @Override
    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }
    @Override
    public List<Student> findByAgeBetween(int min, int max) {
        logger.info("Был вызван метод findByAgeBetween");
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Faculty getFaculty(Long studentId) {
        logger.info("Был вызван метод getFaculty");
        return studentRepository.findById(studentId).get().getFaculty();
    }

    @Override
    public List<Student> findByFacultyId(long facultyId) {
        logger.info("Был вызван метод findByFacultyId");
        return studentRepository.findByFacultyId(facultyId);
    }
    @Override
    public Integer getCount(){
        logger.info("Был вызван метод getCount");
        return studentRepository.getCount();
    }
    @Override
    public Double getAvgAge(){
        logger.info("Был вызван метод getAvgAge");
        return studentRepository.getAvgAge();
    }
    @Override
    public List<Student> getLastFiveStudent(){
        logger.info("Был вызван метод getLastFiveStudent");
        return studentRepository.getLastFiveStudent();
    }
    public List<String> getAllNamesStartWithA() {
        String firstSymbol = "A";
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(name -> name.startsWith(firstSymbol))
                .sorted()
                .collect(Collectors.toList());
    }

    public double getAvgAgeWithStream() {
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(-1);
    }


}
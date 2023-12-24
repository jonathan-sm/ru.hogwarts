package org.hogwarts.school.controller;

import org.hogwarts.school.model.Faculty;
import org.hogwarts.school.model.Student;
import org.hogwarts.school.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/student")
public class  StudentController {

        private final StudentService studentService;

        public StudentController(StudentService studentService) {
            this.studentService = studentService;
        }

        @PostMapping
        public Student add(@RequestBody Student student) {
            return studentService.add(student.getName(), student.getAge());
        }

        @PutMapping
        public Student update(@RequestBody Student student) {
            return studentService.update(student.getId(), student.getName(), student.getAge());
        }

        @DeleteMapping("{id}")
        public Student delete(@RequestParam long id) {
            return studentService.delete(id);
        }

        @GetMapping("{id}")
        public Student get(@RequestParam long id) {
            return studentService.get(id);
        }

        @GetMapping("/by-age")
        public List<Student> getByAge(@RequestParam int age) {
        return studentService.getByAge(age);
    }
    @GetMapping("/all")
    public Collection<Student> getAll() {
        return studentService.getAll();
    }

    @GetMapping("/by-age-between")
    public List<Student> getByAgeBetween(@RequestParam int min, @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/faculty-by-id")
    public Faculty getFaculty(@RequestParam long id) {
        return studentService.getFaculty(id);
    }
    @GetMapping("/count")
    public Integer getCount() {
        return studentService.getCount();
    }
    @GetMapping("/avg-age")
    public Double getAvgAge(){
        return studentService.getAvgAge();
    }

    @GetMapping("/last-five-student")
    public List<Student> getLastFiveStudent() {
        return studentService.getLastFiveStudent();
    }
    @GetMapping("/names-start-with-a")
    public List<String> getAllNamesStartWithA() {
        return studentService.getAllNamesStartWithA();
    }
    @GetMapping("/avg-age-with-stream")
    public double getAvgAgeWithStream() {
        return studentService.getAvgAgeWithStream();
    }
    @GetMapping("/print-to-console")
    public void printStudents() {
        studentService.printStudents();
    }

    @GetMapping("/print-to-console-sync")
    public void printStudentsSync() {
        studentService.printStudentsSync();
    }

}





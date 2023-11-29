package org.hogwarts.school.controller;

import org.hogwarts.school.model.Student;
import org.hogwarts.school.service.StudentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

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

//        @GetMapping("/by-age")
//        public List<Student> getByAge(@RequestParam int age) {
//            return studentService.getByAge(age);
//        }
//
//        @GetMapping("/all")
//        public Collection<Student> getAll() {
//            return studentService.getAll();
//        }
    }


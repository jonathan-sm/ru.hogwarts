package org.hogwarts.school.service;

import org.hogwarts.school.model.Faculty;
import org.hogwarts.school.model.Student;
import org.hogwarts.school.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository repository;
    private StudentService studentService;

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty add(Faculty faculty) {
        return repository.save(faculty); // добавили
    }

    public Faculty update(Faculty faculty) {
        return repository.findById(faculty.getId())
                .map(entity -> repository.save(faculty))
                .orElse(null);
    }

    public Faculty delete(Long id) {
        var entity = repository.findById(id).orElse(null);
        if (entity != null) {
            repository.delete(entity);
        }
        return entity; // удалили
    }

    public Faculty get(Long id) {
        return repository.findById(id).orElse(null); // вывели
    }
    public List<Faculty> getByColor(String color) {
        return repository.findAllByColor(color);
    }

    public List<Faculty> getByColorOrName(String param) {
        return repository.findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(param, param);
    }

    public List<Student> getStudents(Long id) {
        return studentService.findByFacultyId(id);
    }

}
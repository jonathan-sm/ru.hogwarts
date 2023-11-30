package org.hogwarts.school.repository;

import org.hogwarts.school.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findAllByColor(String color);

    List<Faculty> findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(String color, String name);

}

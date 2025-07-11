package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.models.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findByColor(String color);

    Collection<Faculty> findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(String color, String name);
}

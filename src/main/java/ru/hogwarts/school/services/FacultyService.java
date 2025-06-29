package ru.hogwarts.school.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public Collection<Faculty> getFacultyAll() {
        return facultyRepository.findAll();
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id).get();
    }

    public Collection<Faculty> getFacultyByColorOrName(String color, String name) {
        return facultyRepository.findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(color, name);
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }
}

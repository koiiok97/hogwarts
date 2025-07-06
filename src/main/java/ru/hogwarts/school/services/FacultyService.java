package ru.hogwarts.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Collection<Faculty> getFacultyAll() {
        logger.debug("Was invoked method for get all faculty");
        return facultyRepository.findAll();
    }

    public Faculty getFacultyById(Long id) {
        logger.debug("Was invoked method for get faculty by id");
        return facultyRepository.findById(id).get();
    }

    public Collection<Faculty> getFacultyByColorOrName(String color, String name) {
        logger.debug("Was invoked method for get faculty by color or name");
        return facultyRepository.findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(color, name);
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.debug("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.debug("Was invoked method for update faculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        logger.debug("Was invoked method for delete faculty");
        facultyRepository.deleteById(id);
    }
}

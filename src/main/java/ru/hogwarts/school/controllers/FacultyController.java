package ru.hogwarts.school.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.services.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping
    public Collection<Faculty> getFacultyAll() {
        return facultyService.getFacultyAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Long id) {
        Faculty faculty = facultyService.getFacultyById(id);
        if (faculty == null) ResponseEntity.notFound().build();

        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/find")
    public ResponseEntity<Collection<Faculty>> getFacultyByColorOrName(
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String name) {
        Collection<Faculty> faculty = facultyService.getFacultyByColorOrName(color, name);

        if (faculty == null || faculty.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty facultyUpdated = facultyService.updateFaculty(faculty);
        if (facultyUpdated == null) ResponseEntity.notFound().build();

        return ResponseEntity.ok(facultyUpdated);
    }

    @DeleteMapping("{id}")
    public String deleteFacultyById(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return "Факультет удален";
    }
}

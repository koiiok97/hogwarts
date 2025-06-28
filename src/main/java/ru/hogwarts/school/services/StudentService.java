package ru.hogwarts.school.services;



import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public Collection<Student> getStudentAll() {
        return studentRepository.findAll();
    }

    public Collection<String> getAllNames() {
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .toList();
    }

    public Collection<Student> getStudentByName(String name) {
        return studentRepository.findByNameContains(name);
    }

    public Collection<Student> getStudentsByAgeBetween(int ageFrom, int ageTo) {
        return studentRepository.findByAgeBetween(ageFrom, ageTo);
    }

    public Collection<Student> getStudentsByAgeBetween10And20() {
        return studentRepository.findByAgeBetween(10, 20);
    }

    public Faculty getFacultyByStudentId(Long id) {
        return studentRepository.findById(id)
                .map(Student::getFaculty)
                .orElse(null);
    }

    public Collection<Student> getStudentsByFacultyId(Long id) {
        return studentRepository.findByFacultyId(id);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).get();
    }

    public Collection<Student> getStudentByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public Student createStudent(Student faculty) {
        return studentRepository.save(faculty);
    }

    public Student updateStudent(Student faculty) {
        return studentRepository.save(faculty);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}

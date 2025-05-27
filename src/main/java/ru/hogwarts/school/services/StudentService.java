package ru.hogwarts.school.services;



import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Collection<Student> getStudentAll() {
        return studentRepository.findAll();
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

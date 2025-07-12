package ru.hogwarts.school.services;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Collection<Student> getStudentAll() {
        logger.debug("Was invoked method for get all student");
        return studentRepository.findAll();
    }

    public Collection<String> getAllNames() {
        logger.debug("Was invoked method for get all names student");
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .toList();
    }

    public Collection<Student> getStudentByName(String name) {
        logger.debug("Was invoked method for get student by name");
        return studentRepository.findByNameContains(name);
    }

    public Collection<Student> getStudentsByAgeBetween(int ageFrom, int ageTo) {
        logger.debug("Was invoked method for get student by age between {} and {}", ageFrom, ageTo);
        return studentRepository.findByAgeBetween(ageFrom, ageTo);
    }

    public Collection<Student> getStudentsByAgeBetween10And20() {
        logger.debug("Was invoked method for get student by age between 10 and 20");
        return studentRepository.findByAgeBetween(10, 20);
    }

    public Faculty getFacultyByStudentId(Long id) {
        logger.debug("Was invoked method for get student faculty");
        return studentRepository.findById(id)
                .map(Student::getFaculty)
                .orElse(null);
    }

    public Collection<Student> getStudentsByFacultyId(Long id) {
        logger.debug("Was invoked method for get student by faculty id");
        return studentRepository.findByFacultyId(id);
    }

    public Student getStudentById(Long id) {
        logger.debug("Was invoked method for get student by id");
        return studentRepository.findById(id).get();
    }

    public Collection<Student> getStudentByAge(int age) {
        logger.debug("Was invoked method for get student by age");
        return studentRepository.findByAge(age);
    }

    public Integer getCountStudent(){
        logger.debug("Was invoked method for get student count");
        return studentRepository.getCountStudent();
    }

    public Double getAvgAge(){
        logger.debug("Was invoked method for get avg age");
        return studentRepository.getAvgAge();
    }

    public Collection<Student> getLastFiveStudents() {
        logger.debug("Was invoked method for get last five students");
        return studentRepository.getLastFiveStudent();}

    public Student createStudent(Student faculty) {
        logger.debug("Was invoked method for create student");
        return studentRepository.save(faculty);
    }

    public Student updateStudent(Student faculty) {
        logger.debug("Was invoked method for update student");
        return studentRepository.save(faculty);
    }

    public void deleteStudent(Long id) {
        logger.debug("Was invoked method for delete student");
        studentRepository.deleteById(id);
    }
}

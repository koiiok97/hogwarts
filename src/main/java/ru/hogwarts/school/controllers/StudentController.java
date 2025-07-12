package ru.hogwarts.school.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.services.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping
    public ResponseEntity getStudentAll(@RequestParam(required = false) Boolean onlyName) {
        if (onlyName != null && onlyName) return ResponseEntity.ok(studentService.getAllNames());
        return ResponseEntity.ok(studentService.getStudentAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(student);
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<Collection<Student>> getStudentByAge(@PathVariable int age) {
        Collection<Student> students = studentService.getStudentByAge(age);
        if (students.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(students);
    }

    @GetMapping("/age")
    public Collection<Student> getStudentsByAgeBetween(@RequestParam int ageFrom, @RequestParam int ageTo) {
        return studentService.getStudentsByAgeBetween(ageFrom, ageTo);
    }

    @GetMapping("/age/10And20")
    public Collection<Student> getStudentByAgeBetween() {
        return studentService.getStudentsByAgeBetween10And20();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Collection<Student>> getStudentByName(@PathVariable String name) {
        Collection<Student> students = studentService.getStudentByName(name);
        if (students.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/faculty/{id}")
    public ResponseEntity<Faculty> getFacultyByStudentId(@PathVariable Long id) {
        Faculty faculty = studentService.getFacultyByStudentId(id);
        if (faculty == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/facultyId/{id}")
    public ResponseEntity<Collection<Student>> getStudentByFacultyId(@PathVariable Long id) {
        Collection<Student> students = studentService.getStudentsByFacultyId(id);
        if (students.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/count")
    public Integer getCountStudent() {
        return studentService.getCountStudent();
    }

    @GetMapping("/avgAge")
    public Double getAvgAge() {
        return studentService.getAvgAge();
    }

    @GetMapping("/lastFive")
    public Collection<Student> getLastFive() {
        return studentService.getLastFiveStudents();
    }


    @GetMapping("/print-parallel")
    public void printParallel() {
        Collection<String> studentsList = studentService.getAllNames();

        System.out.println(studentsList.toArray()[0]);
        System.out.println(studentsList.toArray()[1]);

        new Thread(() -> {
            System.out.println(studentsList.toArray()[2]);
            System.out.println(studentsList.toArray()[3]);
        }).start();

        new Thread(() -> {
            System.out.println(studentsList.toArray()[4]);
            System.out.println(studentsList.toArray()[5]);
        }).start();
    }

    @GetMapping("/print-synchronized")
    public void printSynchronized() {
        synchronizedMethod(0);
        synchronizedMethod(1);

        new Thread(() -> {
            synchronizedMethod(2);
            synchronizedMethod(3);
        }).start();

        new Thread(() -> {
            synchronizedMethod(4);
            synchronizedMethod(5);
        }).start();
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(student);
        if (updatedStudent == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{id}")
    public String deleteStudentById(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "Студент удален";
    }

    private synchronized void synchronizedMethod(int index){
        Collection<String> studentsList = studentService.getAllNames();
        System.out.println(studentsList.toArray()[index]);
    }
}

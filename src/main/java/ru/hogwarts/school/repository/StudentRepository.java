package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.models.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int age1, int age2);

    Collection<Student> findByNameContains(String part);

    Collection<Student> findByFacultyId(Long facultyId);

    @Query(value = "SELECT COUNT(*) AS count FROM student", nativeQuery = true)
    Integer getCountStudent();

    @Query(value = "SELECT AVG(age) AS avgAge FROM student",nativeQuery = true)
    Double getAvgAge();

    @Query(value = "select * from student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Collection<Student> getLastFiveStudent();
}

package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;


    private String getUrl() {
        return "http://localhost:" + port + "/student";
    }

    @Test
    public void testPostStudent() {
        Student student = new Student();
        student.setName("Maxim");
        student.setAge(19);

        Student response = testRestTemplate.postForObject(getUrl(), student, Student.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getName()).isEqualTo("Maxim");
    }

    @Test
    public void testGetStudentAll() {
        Assertions.assertThat(testRestTemplate.getForObject(getUrl(), Student[].class)).isNotNull();
    }

    @Test
    public void testGetStudent() {
        Assertions.assertThat(testRestTemplate.getForObject(getUrl() + "/11", Student.class)).isNotNull();
    }

    @Test
    public void testGetStudentByAge(){
        Assertions.assertThat(testRestTemplate.getForObject(getUrl() + "/age/11", Student[].class)).isNotNull();
    }

    @Test
    public void testGetStudentsByAgeBetween() {
        Assertions.assertThat(testRestTemplate.getForObject(getUrl() + "/age?ageFrom=10&ageTo=20", Student[].class)).isNotNull();
    }

    @Test
    public void testGetStudentByAgeBetween() {
        Assertions.assertThat(testRestTemplate.getForObject(getUrl() + "/age/10And20", Student[].class)).isNotNull();
    }

    @Test
    public void testGetStudentByName(){
        Assertions.assertThat(testRestTemplate.getForObject(getUrl() + "/name/Maxim", Student[].class)).isNotNull();
    }

    @Test
    public void testGetFacultyByStudentId(){
        Assertions.assertThat(testRestTemplate.getForObject(getUrl() + "/faculty/1", Faculty.class)).isNotNull();
    }

    @Test
    public void testGetStudentByFacultyId(){
        Assertions.assertThat(testRestTemplate.getForObject(getUrl() + "/facultyId/1", Student[].class)).isNotNull();
    }

    @Test
    public void testUpdateStudent(){
        Student student = new Student();
        student.setId(302L);
        student.setName("MaximTest");

        testRestTemplate.put(getUrl(), student);

        Assertions.assertThat(testRestTemplate.getForObject(getUrl() + "/302", Student.class)
                .getName())
                .isEqualTo("MaximTest");
    }

    @Test
    public void testDeleteStudentById(){
        testRestTemplate.delete(getUrl() + "/402");
        Assertions.assertThat(testRestTemplate.getForEntity(getUrl() + "/402", Student.class).getStatusCode().isError()).isTrue();
    }
}

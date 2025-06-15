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
public class FacultyControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String getUrl() {
        return "http://localhost:" + port + "/faculty";
    }

    @Test
    public void testPostFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Test Faculty");
        faculty.setColor("red");

        Faculty response = testRestTemplate.postForObject(getUrl(), faculty, Faculty.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getName()).isEqualTo("Test Faculty");
    }

    @Test
    public void testGetFaculty() {
        Assertions.assertThat(testRestTemplate.getForObject(getUrl(), Faculty[].class)).isNotNull();
    }

    @Test
    public void testGetFacultyById() {
        Assertions.assertThat(testRestTemplate.getForObject(getUrl() + "/102", Faculty.class)).isNotNull();
    }

    @Test
    public void testGetFacultyByColorOrName() {
        Assertions.assertThat(testRestTemplate.getForObject(getUrl() + "/find?color=red&name=Test Faculty", Faculty[].class)).isNotNull();
    }

    @Test
    public void testUpdateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setId(102L);
        faculty.setName("FACULTY TEST");

        testRestTemplate.put(getUrl(), faculty);

        Assertions.assertThat(testRestTemplate.getForObject(getUrl() + "/102", Faculty.class)
                        .getName())
                .isEqualTo("FACULTY TEST");
    }


    @Test
    public void testDeleteFacultyById(){
        testRestTemplate.delete(getUrl() + "/52");
        Assertions.assertThat(testRestTemplate.getForEntity(getUrl() + "/52", Faculty.class).getStatusCode().isError()).isTrue();
    }
}

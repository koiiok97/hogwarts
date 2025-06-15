package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controllers.StudentController;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.services.StudentService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = StudentController.class,
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = StudentService.class
        ))
public class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentRepository studentRepository;

    @MockitoSpyBean
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addStudentTest() throws Exception {
        Long id = 1L;
        String name = "Oleg";
        int age = 18;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void getAllStudentTest() throws Exception {
        Student student = new Student(1L, "Maxim", 12);
        when(studentRepository.findAll()).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Maxim"))
                .andExpect(jsonPath("$[0].age").value(12));
    }

    @Test
    public void getStudentByIdTest() throws Exception {
        Student student = new Student(1L, "Maxim", 12);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Maxim"))
                .andExpect(jsonPath("$.age").value(12));
    }

    @Test
    void getStudentsByAgeTest() throws Exception {
        Student student = new Student(1L, "Maxim", 12);
        when(studentRepository.findByAge(12)).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age/12")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Maxim"))
                .andExpect(jsonPath("$[0].age").value(12));
    }

    @Test
    void getStudentsByAgeBetweenTest() throws Exception {
        Student student = new Student(1L, "Maxim", 12);
        when(studentRepository.findByAgeBetween(10, 15)).thenReturn(List.of(student));
        when(studentService.getStudentsByAgeBetween10And20()).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age")
                        .param("ageFrom", "10")
                        .param("ageTo", "15")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Maxim"))
                .andExpect(jsonPath("$[0].age").value(12));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age/10And20")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].age").value(12));
    }

    @Test
    void getStudentByNameTest() throws Exception {
        String name = "Maxim";
        Student student = new Student(1L, name, 12);
        when(studentRepository.findByNameContains(name)).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/name/" + name)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(12));
    }

    @Test
    void getFacultyByStudentIdTest() throws Exception {
        Faculty faculty = new Faculty(1L, "Faculty Test", "red");

        when(studentService.getFacultyByStudentId(any(Long.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Faculty Test"))
                .andExpect(jsonPath("$.color").value("red"));
    }

    @Test
    void getStudentByFacultyIdTest() throws Exception {
        Student student = new Student(1L, "Maxim", 12);

        when(studentRepository.findByFacultyId(any(Long.class))).thenReturn(List.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/facultyId/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Maxim"))
                .andExpect(jsonPath("$[0].age").value("12"));
    }

    @Test
    void updateStudentTest() throws Exception {
        Student updatedStudent = new Student(1L, "Maxim", 19);
        when(studentService.updateStudent(any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(objectMapper.writeValueAsString(updatedStudent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Maxim"));
    }

    @Test
    void deleteStudentTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/1")
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("Студент удален"));
    }


}

package ru.hogwarts.school.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "students"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Faculty {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String color;

    @OneToMany(mappedBy = "faculty")
    private Collection<Student> students;


}

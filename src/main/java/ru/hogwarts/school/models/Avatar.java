package ru.hogwarts.school.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Avatar {
    @Id
    @GeneratedValue
    private Long id;

    private String filePath;
    private long fileSize;
    private String mediaType;

    private byte[] data;

    @OneToOne
    private Student student;

}

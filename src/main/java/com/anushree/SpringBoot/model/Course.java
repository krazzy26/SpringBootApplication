package com.anushree.SpringBoot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "Courses")
public class Course {
    @Id
    private String id;

    @NotNull(message = "course cannot be null")
    private String course;

    @NotNull(message = "days cannot be null")
    private String days;

    @NotNull(message = "instructor cannot be null")
    private String instructor;

    @NotNull(message = "completed cannot be null")
    private Boolean completed;

    private Date updatedAt;

}

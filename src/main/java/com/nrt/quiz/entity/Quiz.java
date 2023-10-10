package com.nrt.quiz.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "quiz_details")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private String maxMarks;
    private String numberOfQuestions;
    private boolean active= false;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category categories;

}

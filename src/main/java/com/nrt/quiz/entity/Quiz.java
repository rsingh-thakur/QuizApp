package com.nrt.quiz.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
	private boolean active = false;

	@ManyToOne(fetch = FetchType.EAGER)
	private Category categories;

	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
	private Set<Questions> questions;
}

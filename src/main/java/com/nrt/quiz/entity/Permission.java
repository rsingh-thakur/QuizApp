package com.nrt.quiz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="Permissions_Details")
public class Permission {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long pid;
	
	private String name ; 
	
	@ManyToOne
	private Role role;
	
}

package com.nrt.quiz.entity;

import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "roles_details")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;

	@OneToMany(mappedBy = "role" )
	private List<Permission> permission;
	
	@OneToMany(mappedBy = "role" )
	private List<User> user;

}

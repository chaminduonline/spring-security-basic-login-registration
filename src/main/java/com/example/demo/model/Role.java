package com.example.demo.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name="role")
@Data
public class Role implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name="role")
	private String role;
	
	@ManyToMany(mappedBy =  "roles")
	private List<User> users;
}

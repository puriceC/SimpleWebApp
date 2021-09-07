package com.forgacea.WebApp.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "Users")
public class User {
	@Id
	@Column(nullable = false, length = 50)
	String username;

	@Column(nullable = false, length = 100)
	String password;

	@Column(nullable = false, columnDefinition = "TINYINT DEFAULT 1")
	boolean enabled;

	@OneToMany(mappedBy = "director")
	Set<Movie> movies;
}

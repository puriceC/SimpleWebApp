package com.forgacea.WebApp.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "Directors")
public class Director {
	@Id
	@Column(name = "id_director")
	@GeneratedValue
	int id;

	String name;

	@OneToMany(mappedBy = "director")
	Set<Movie> movies;
}

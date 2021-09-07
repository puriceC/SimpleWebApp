package com.forgacea.WebApp.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "Directors")
public class Director {
	@Id
	@GeneratedValue
	int id_director;

	String name;

	@OneToMany(mappedBy = "director")
	Set<Movie> movies;
}

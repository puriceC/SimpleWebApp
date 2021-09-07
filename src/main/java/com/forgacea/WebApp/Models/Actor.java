package com.forgacea.WebApp.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Data
@Table(name = "Actors")
public class Actor {
	@Id
	@GeneratedValue
	int id_actor;

	String name;

	@ManyToMany(mappedBy = "actors")
	Set<Movie> movies;

}

package com.forgacea.WebApp.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "Genres")
public class Genre {
	@Id
	@GeneratedValue
	int id_genre;

	String genre;

	@OneToMany(mappedBy = "genre")
	Set<Movie> movies;
}

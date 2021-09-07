package com.forgacea.WebApp.Models;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Data
@Table(name = "Movies")
public class Movie {
	@Id
	@Column(name = "id_movie")
	@GeneratedValue
	int id;

	String title;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name="genre")
	Genre genre;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name="director")
	Director director;

	@ManyToMany(cascade=ALL)
	@JoinTable(
			name = "Movies_to_actors",
			joinColumns = { @JoinColumn(name = "movie_id") },
			inverseJoinColumns = { @JoinColumn(name = "actor_id") }
	)
	Set<Actor> actors;

	@OneToMany(mappedBy = "movie")
	Set<Rating> ratings;

}

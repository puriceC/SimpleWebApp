package com.forgacea.WebApp.Models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "Movies")
public class Movie {
	@Id
	@Column(name = "id_movie")
	@GeneratedValue
	int id;

	String title;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name="genre")
	@ToString.Exclude
	Genre genre;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name="director")
	@ToString.Exclude
	Director director;

	@ManyToMany(cascade=ALL)
	@JoinTable(
			name = "Movies_to_actors",
			joinColumns = { @JoinColumn(name = "movie_id") },
			inverseJoinColumns = { @JoinColumn(name = "actor_id") }
	)
	@ToString.Exclude
	Set<Actor> actors;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
	@ToString.Exclude
	Set<Rating> ratings;

	public float getRating(){
		float sum = 0;
		for(Rating r : ratings){
			sum += r.rating;
		}
		return sum/ratings.size();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Movie movie = (Movie) o;

		return Objects.equals(id, movie.id);
	}

	@Override
	public int hashCode() {
		return 693411677;
	}
}

package com.forgacea.WebApp.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties("hibernateLazyInitializer")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "Actors")
public class Actor {
	@Id
	@Column(name = "id_actor")
	@GeneratedValue
	int id;

	String name;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "actors")
	@JsonIgnoreProperties("actors")
	@ToString.Exclude
	Set<Movie> movies;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Actor actor = (Actor) o;

		return Objects.equals(id, actor.id);
	}

	@Override
	public int hashCode() {
		return 1133552734;
	}
}

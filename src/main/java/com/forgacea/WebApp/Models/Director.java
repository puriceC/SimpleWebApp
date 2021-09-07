package com.forgacea.WebApp.Models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "Directors")
public class Director {
	@Id
	@Column(name = "id_director")
	@GeneratedValue
	int id;

	String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "director")
	@ToString.Exclude
	Set<Movie> movies;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Director director = (Director) o;

		return Objects.equals(id, director.id);
	}

	@Override
	public int hashCode() {
		return 1208779318;
	}
}

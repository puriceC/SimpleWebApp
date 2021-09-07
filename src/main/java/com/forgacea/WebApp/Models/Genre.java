package com.forgacea.WebApp.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "Genres")
public class Genre {
	@Id
	@Column(name = "id_genre")
	@GeneratedValue
	int id;

	String genre;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "genre")
	@ToString.Exclude
	@JsonIgnoreProperties("genre")
	Set<Movie> movies;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Genre genre = (Genre) o;

		return Objects.equals(id, genre.id);
	}

	@Override
	public int hashCode() {
		return 1887069089;
	}
}

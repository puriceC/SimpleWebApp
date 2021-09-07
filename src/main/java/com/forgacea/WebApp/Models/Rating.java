package com.forgacea.WebApp.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "Ratings")
public class Rating {
	@Id
	@Column(name = "id_rating")
	@GeneratedValue
	int id;

	float rating;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_movie")
	@JsonIgnoreProperties("ratings")
	@ToString.Exclude
	Movie movie;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_user")
	@JsonIgnoreProperties("ratings")
	@ToString.Exclude
	User user;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Rating rating = (Rating) o;

		return Objects.equals(id, rating.id);
	}

	@Override
	public int hashCode() {
		return 708680207;
	}
}

package com.forgacea.WebApp.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Data
@Table(name = "Ratings")
public class Rating {
	@Id
	@Column(name = "id_rating")
	@GeneratedValue
	int id;

	float rating;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_movie")
	Movie movie;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_user")
	User user;

}

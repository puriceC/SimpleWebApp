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
	@GeneratedValue
	int id_rating;

	float rating;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_movie")
	Movie movie;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_user")
	User user;

}

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
@Table(name = "Users")
public class User {
	@Id
	@Column(nullable = false, length = 50)
	String username;

	@Column(nullable = false, length = 100)
	String password;

	@Column(nullable = false, columnDefinition = "TINYINT DEFAULT 1")
	boolean enabled;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@JsonIgnoreProperties("user")
	@ToString.Exclude
	Set<Rating> ratings;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		User user = (User) o;

		return Objects.equals(username, user.username);
	}

	@Override
	public int hashCode() {
		return 562048007;
	}
}

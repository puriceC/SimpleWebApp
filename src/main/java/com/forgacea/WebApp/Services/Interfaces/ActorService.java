package com.forgacea.WebApp.Services.Interfaces;

import com.forgacea.WebApp.Models.Actor;

import java.util.List;
import java.util.Optional;

public interface ActorService {

	List<Actor> getPage(int pageSize, int pageNumber, String sortOrder);

	Optional<Actor> findById(Integer id);

	Actor insert(Actor actor);

	void update(Integer id, Actor actor);

	void delete(Integer id);
}

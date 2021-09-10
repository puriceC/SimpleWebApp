package com.forgacea.WebApp.Repositories;

import com.forgacea.WebApp.Models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
}

package com.forgacea.WebApp.Repositories;

import com.forgacea.WebApp.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}

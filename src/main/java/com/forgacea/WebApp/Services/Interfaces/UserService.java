package com.forgacea.WebApp.Services.Interfaces;

import com.forgacea.WebApp.Models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

	List<User> getPage(int pageSize, int pageNumber, String sortOrder);

	Optional<User> findById(String userName);

	User insert(User user);

	void update(String userName, User user);

	void delete(String userName);
}

package com.forgacea.WebApp.Services.Interfaces;

import com.forgacea.WebApp.Models.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {

	List<Item> getPage(int pageSize, int pageNumber, String sortOrder);

	Optional<Item> findById(Integer id);

	Item insert(Item item);

	void update(Integer id, Item item);

	void delete(Integer id);
}

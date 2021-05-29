package com.forgacea.WebApp.Services;

import com.forgacea.WebApp.Models.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
	List<Item> getItems();

	List<Item> getItemPage(int pageSize, int pageNumber);

	List<Item> getItemSortedPage(int pageSize, int pageNumber, String sortOrder);

	Optional<Item> findItem(Integer id);

	Item insertItem(Item item);

	void updateItem(Integer id, Item item);

	void deleteItem(Integer id);
}

package com.forgacea.WebApp;

import java.util.List;

public interface ItemService {
	List<Item> getItems();

	Item getItemById(Integer id);

	void insertItem(Item item);

	void updateItem(Integer id, Item item);

	void deleteItem(Integer id);
}

package com.forgacea.WebApp;

import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

	ItemRepository repository;

	public ItemServiceImpl(ItemRepository repository) {
		this.repository = repository;
	}

	@Override
	public Iterable<Item> getItems() {
		return repository.findAll();
	}

}

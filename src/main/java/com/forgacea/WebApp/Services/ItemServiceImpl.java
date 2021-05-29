package com.forgacea.WebApp.Services;

import com.forgacea.WebApp.Repositories.ItemRepository;
import com.forgacea.WebApp.Models.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
	private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

	ItemRepository repository;

	public ItemServiceImpl(ItemRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Item> getItems() {
		logger.debug("getItems called");
		return repository.findAll();
	}

	@Override
	public List<Item> getItemPage(int pageSize, int pageNumber) {
		logger.debug("getItemPage called with pageSize = " + pageSize + " and pageNumber = " + pageNumber);
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Item> itemPage = repository.findAll(pageable);
		return itemPage.getContent();
	}
	@Override
	public List<Item> getItemSortedPage(int pageSize, int pageNumber, String sortOrder) {
		logger.debug("getItemSortedPage called with pageSize = " + pageSize + " and pageNumber = " + pageNumber + " and getItemSortedPage = '" + sortOrder + "'");
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortOrder));
		Page<Item> itemPage = repository.findAll(pageable);
		return itemPage.getContent();
	}

	@Override
	public Optional<Item> findItem(Integer id) {
		logger.debug("findItem called with id = " + id);
		return repository.findById(id);
	}

	@Override
	public Item insertItem (Item item) {
		logger.debug("insertItem called with " + item);
		return repository.save(item);
	}

	@Override
	public void updateItem(Integer id, Item item) {
		logger.debug("updateItem called for item with id = " + id + " and " + item);
		item.setId(id);
		repository.save(item);
	}

	@Override
	public void deleteItem(Integer id) {
		logger.debug("deleteItem called on item with id = " + id);
		Item itemToBeDeleted = repository.getById(id);
		repository.delete(itemToBeDeleted);
	}
}

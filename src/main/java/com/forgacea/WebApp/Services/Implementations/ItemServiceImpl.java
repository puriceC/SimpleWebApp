package com.forgacea.WebApp.Services.Implementations;

import com.forgacea.WebApp.Repositories.ItemRepository;
import com.forgacea.WebApp.Models.Item;
import com.forgacea.WebApp.Services.Interfaces.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

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
		logger.debug(format("getItemPage called with pageSize = %d and pageNumber = %d", pageSize, pageNumber));
		return getItemSortedPage(pageSize, pageNumber, "");
	}

	@Override
	public List<Item> getItemSortedPage(int pageSize, int pageNumber, String sortBy) {
		logger.debug(format("getItemSortedPage called with pageSize = %d, pageNumber = %d and sortBy = '%s'", pageSize, pageNumber, sortBy));
		Sort sort = sortBy.isEmpty() ? Sort.unsorted() : Sort.by(sortBy);

		if (Arrays.stream(Item.class.getDeclaredFields()).noneMatch(field -> field.getName().equals(sortBy))) {
			logger.warn(format("Value of sortBy ('%s') was not found in class Item and will be ignored", sortBy));
			sort = Sort.unsorted();
		}
		if (pageSize < 1){
			logger.warn(format("Value of page size (%d) less then 1 and will be ignored", pageSize));
			pageSize = 20;
		}
		if (pageNumber < 0){
			logger.warn(format("Value of page number (%d) less then 0 and will be set to 0", pageNumber));
			pageNumber = 0;
		}

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Item> itemPage = repository.findAll(pageable);
		return itemPage.getContent();
	}

	@Override
	public Optional<Item> findItem(Integer id) {
		logger.debug(format("findItem called with id = %d", id));
		return repository.findById(id);
	}

	@Override
	public Item insertItem (Item item) {
		logger.debug(format("insertItem called with %s", item));
		return repository.save(item);
	}

	@Override
	public void updateItem(Integer id, Item item) {
		logger.debug(format("updateItem called for item with id = %d and %s", id, item));
		item.setId(id);
		repository.save(item);
	}

	@Override
	public void deleteItem(Integer id) {
		logger.debug(format("deleteItem called on item with id = %d", id));
		Item itemToBeDeleted = repository.getById(id);
		repository.delete(itemToBeDeleted);
	}
}

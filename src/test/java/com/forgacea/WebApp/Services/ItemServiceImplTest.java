package com.forgacea.WebApp.Services;

import com.forgacea.WebApp.Models.Item;
import com.forgacea.WebApp.Repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

	@Mock private ItemRepository itemRepository;

	private ItemServiceImpl underTest;

	@BeforeEach
	void setUp() {
		underTest = new ItemServiceImpl(itemRepository);
	}

	@Test
	void getItemsCallsRepositoryFindAll() {
		// when
		underTest.getItems();
		// then
		verify(itemRepository).findAll();
	}

	@Test
	void findItemCallsRepositoryFindById() {
		// given
		Integer id = 1;
		// when
		underTest.findItem(id);
		// then
		ArgumentCaptor<Integer> idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(itemRepository).findById(idArgumentCaptor.capture());
		Integer capturedId = idArgumentCaptor.getValue();
		assertThat(capturedId).isEqualTo(id);
	}

	@Test
	void insertItemCallsRepositorySave() {
		// given
		Item item = new Item();
		item.setId(1);
		item.setName("item1");
		// when
		underTest.insertItem(item);
		// then
		ArgumentCaptor<Item> itemArgumentCaptor = ArgumentCaptor.forClass(Item.class);
		verify(itemRepository).save(itemArgumentCaptor.capture());
		Item capturedItem = itemArgumentCaptor.getValue();
		assertThat(capturedItem).isEqualTo(item);
	}

	@Test
	void updateItemCallsRepositorySave() {
		// given
		Integer id = 1;
		Item item = new Item();
		item.setName("item1");
		// when
		underTest.updateItem(id, item);
		// then
		ArgumentCaptor<Item> itemArgumentCaptor = ArgumentCaptor.forClass(Item.class);
		verify(itemRepository).save(itemArgumentCaptor.capture());
		Item capturedItem = itemArgumentCaptor.getValue();
		assertThat(capturedItem.getId()).isEqualTo(id);
		assertThat(capturedItem.getName()).isEqualTo(item.getName());
	}

	@Test
	void deleteItemCallsRepositoryDelete() {
		// given
		Integer id = 1;
		Item item = new Item();
		item.setId(id);
		item.setName("item1");
		given(itemRepository.findById(id)).willReturn(Optional.of(item));
		given(itemRepository.getById(id)).willReturn(item);
		// when
		underTest.deleteItem(id);
		// then
		ArgumentCaptor<Item> itemArgumentCaptor = ArgumentCaptor.forClass(Item.class);
		verify(itemRepository).delete(itemArgumentCaptor.capture());
		Item capturedItem = itemArgumentCaptor.getValue();
		assertThat(capturedItem.getId()).isEqualTo(id);
	}
}
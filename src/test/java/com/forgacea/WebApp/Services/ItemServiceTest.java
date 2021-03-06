package com.forgacea.WebApp.Services;

import com.forgacea.WebApp.Models.Item;
import com.forgacea.WebApp.Repositories.ItemRepository;
import com.forgacea.WebApp.Services.Implementations.ItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

	@Mock private ItemRepository itemRepository;

	private ItemServiceImpl underTest;

	@BeforeEach
	void setUp() {
		underTest = new ItemServiceImpl(itemRepository);
	}

	@Test
	void findItemCallsRepositoryFindById() {
		// given
		Integer id = 1;
		// when
		underTest.findById(id);
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
		underTest.insert(item);
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
		underTest.update(id, item);
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
		given(itemRepository.getById(id)).willReturn(item);
		// when
		underTest.delete(id);
		// then
		ArgumentCaptor<Item> itemArgumentCaptor = ArgumentCaptor.forClass(Item.class);
		verify(itemRepository).delete(itemArgumentCaptor.capture());
		Item capturedItem = itemArgumentCaptor.getValue();
		assertThat(capturedItem.getId()).isEqualTo(id);
	}
}
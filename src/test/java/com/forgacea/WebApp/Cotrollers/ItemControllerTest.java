package com.forgacea.WebApp.Cotrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forgacea.WebApp.Cotrollers.ApiControllers.ItemController;
import com.forgacea.WebApp.Models.Item;
import com.forgacea.WebApp.Services.Interfaces.ItemService;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private DataSource dataSource;

	@MockBean
	private ItemService itemService;

	@Test
	public void whenGetWithParamsISValid() throws Exception {
		// given
		int pageSize = 20;
		int pageNumber = 0;
		String sortBy = "name";
		given(itemService.getItemSortedPage(pageSize, pageNumber, sortBy)).willReturn(List.of());
		// when
		ResultActions results = mockMvc.perform(get("/api/items/")
				.param("page_size", String.valueOf(pageSize))
				.param("page_number", String.valueOf(pageNumber))
				.param("sort_by", sortBy));
		// then
		results.andExpect(status().isOk());
		results.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		results.andExpect(content().string("[]"));
	}

	@Test
	public void whenGetAllIsValid() throws Exception {
		// given
		given(itemService.getItems()).willReturn(List.of());
		// when
		ResultActions results = mockMvc.perform(get("/api/items/all"));
		// then
		results.andExpect(status().isOk());
		results.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		results.andExpect(content().string("[]"));
	}

	@Test
	public void whenGetWithIdIsValid() throws Exception {
		// given
		int id = 0;
		Item item = new Item();
		item.setId(id);
		item.setName("item1");
		given(itemService.findItem(id)).willReturn(Optional.of(item));
		// when
		ResultActions results = mockMvc.perform(get("/api/items/{id}", id));
		// then
		results.andExpect(status().isOk());
		results.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		results.andExpect(jsonPath("$.id").isNumber());
		results.andExpect(jsonPath("$.name").isString());
	}

	@Test
	public void whenInsertIsValid() throws Exception {
		// given
		Item item = new Item();
		item.setName("item1");
		given(itemService.insertItem(item)).willReturn(item);
		// when
		ResultActions results = mockMvc.perform(post("/api/items")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(item)));
		// then
		results.andExpect(status().isOk());
		results.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		results.andExpect(jsonPath("$.id").isNumber());
		results.andExpect(jsonPath("$.name").isString());
	}

	@Test
	public void whenUpdateIsValid() throws Exception {
		// given
		int id = 0;
		Item item = new Item();
		item.setName("item1");
		given(itemService.findItem(id)).willReturn(Optional.of(item));
		// when
		ResultActions results = mockMvc.perform(put("/api/items/{id}", id)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(item)));
		// then
		results.andExpect(status().isOk());
		results.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		results.andExpect(jsonPath("$.id").isNumber());
		results.andExpect(jsonPath("$.name").isString());
	}

	@Test
	public void whenDeleteIsValid() throws Exception {
		// given
		int id = 0;
		Item item = new Item();
		item.setName("item1");
		given(itemService.findItem(id)).willReturn(Optional.of(item));
		// when
		ResultActions results = mockMvc.perform(delete("/api/items/{id}", id));
		// then
		results.andExpect(status().isNoContent());
	}
}
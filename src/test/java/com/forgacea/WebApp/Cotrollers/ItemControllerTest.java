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
	public void whenInsertIsValid() throws Exception {
		// given
		Item item = new Item();
		item.setName("item1");
		given(itemService.insert(item)).willReturn(item);
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
		given(itemService.findById(id)).willReturn(Optional.of(item));
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
		given(itemService.findById(id)).willReturn(Optional.of(item));
		// when
		ResultActions results = mockMvc.perform(delete("/api/items/{id}", id));
		// then
		results.andExpect(status().isNoContent());
	}
}
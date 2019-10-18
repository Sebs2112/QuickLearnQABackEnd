package com.cardflasher.cardflasher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CardflasherApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testControllerGetMethod() throws Exception{
		this.mockMvc.perform(get("/api/cards")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void testControllerGetByIdMethod() throws Exception{
		this.mockMvc.perform(get("/api/cards/7").accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("id").value("7"))
				.andExpect(jsonPath("frontText").value("William I Reigned between?"))
				.andExpect(jsonPath("backText").value("1066-1087"))
				.andExpect(jsonPath("title").value("William I"))
				.andExpect(jsonPath("category").value("Kings & Queens"));
	}

	@Test
	public void testControllerPostMethod() throws Exception{
		this.mockMvc.perform(post("/api/cards/")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":7,\"frontText\":\"William I Reigned between?\",\"backText\":\"1066-1087\",\"category\":\"Kings & Queens\",\"title\":\"William I\"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("id").value("7"))
				.andExpect(jsonPath("frontText").value("William I Reigned between?"))
				.andExpect(jsonPath("backText").value("1066-1087"))
				.andExpect(jsonPath("title").value("William I"))
				.andExpect(jsonPath("category").value("Kings & Queens"));

	}

	@Test
	public void testControllerUpdateMethod() throws Exception{
		this.mockMvc.perform(put("/api/cards/7")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":7,\"frontText\":\"William I Reigned between?\",\"backText\":\"1066-1087\",\"category\":\"Kings & Queens\",\"title\":\"William I\"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("id").value("7"))
				.andExpect(jsonPath("frontText").value("William I Reigned between?"))
				.andExpect(jsonPath("backText").value("1066-1087"))
				.andExpect(jsonPath("title").value("William I"))
				.andExpect(jsonPath("category").value("Kings & Queens"));

	}



}

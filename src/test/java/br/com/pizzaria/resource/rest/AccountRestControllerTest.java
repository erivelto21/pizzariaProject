package br.com.pizzaria.resource.rest;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pizzaria.config.Application;
import br.com.pizzaria.domain.Flavor;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class AccountRestControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testAddFavoriteFlavor() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/account/favorite/add/1/flavor/1").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testGetFavoriteList() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/account/favorite/1").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		ObjectMapper objectMapper = new ObjectMapper();
		
		List<Flavor> list =  objectMapper.readValue(result.getResponse().getContentAsString(),
				objectMapper.getTypeFactory().constructCollectionType(List.class, Flavor.class));
		
		assertTrue(list.size() > 0);
		
		for (Flavor flavor : list) {
			System.out.println(flavor.getName());
		}
	}
	
	@Test
	public void testRemoveFavoriteFlavor() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/account/favorite/remove/1/flavor/3").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		System.out.println(result.getResponse().getContentAsString());
	}
}

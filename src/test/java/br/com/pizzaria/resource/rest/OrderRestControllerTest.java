package br.com.pizzaria.resource.rest;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
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
import br.com.pizzaria.domain.Order;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderRestControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void TestSingleOrder() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/order/199").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test
	public void TestOrderNotFound() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/order/20").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
	}

	@Test
	public void TestOrderlistByUserIsEmpty() throws Exception {
		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.get("/order/user/3").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		ObjectMapper objectMapper = new ObjectMapper();

		List<Order> list = objectMapper.readValue(result.getResponse().getContentAsString(),
				objectMapper.getTypeFactory().constructCollectionType(List.class, Order.class));

		assertTrue(list.size() == 0);
	}

	@Test
	public void TestOrderlistByUserIsNotEmpty() throws Exception {
		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.get("/order/user/1").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		ObjectMapper objectMapper = new ObjectMapper();
		result.getResponse().setCharacterEncoding("UTF-8");
		List<Order> list = objectMapper.readValue(result.getResponse().getContentAsString(),
				objectMapper.getTypeFactory().constructCollectionType(List.class, Order.class));

		assertTrue(list.size() > 0);
	}
	
	@Test
	public void TestOrderlistByUserIsNotFound() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/order/user/100").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
	}
}

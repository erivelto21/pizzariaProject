package br.com.pizzaria.resource.rest;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import br.com.pizzaria.domain.Flavor;
import br.com.pizzaria.domain.RankItem;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class PizzaRestControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testListIsNotEmpty() throws Exception {
		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.get("/pizza").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		
		ObjectMapper objectMapper = new ObjectMapper();

		List<Flavor> list = objectMapper.readValue(result.getResponse().getContentAsString(),
				objectMapper.getTypeFactory().constructCollectionType(List.class, RankItem.class));

		assertTrue(list.size() > 0);
	}
}

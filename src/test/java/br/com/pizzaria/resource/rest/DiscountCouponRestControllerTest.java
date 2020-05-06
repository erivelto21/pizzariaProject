package br.com.pizzaria.resource.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.pizzaria.config.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class DiscountCouponRestControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testGetValidCoupon() throws Exception {
		String code = "w6gZCcWbuPdMfqtKponAWe38x";
		
		mockMvc.perform(MockMvcRequestBuilders.get("/discountcoupon/" + code).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testGetCouponExpired() throws Exception {
		String code = "f6gZCcWbuPdMfqtKponAWe38x";
		
		mockMvc.perform(MockMvcRequestBuilders.get("/discountcoupon/" + code).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void testNotFoundCoupon() throws Exception {
		String code = "w6gZCcWbuPdMfqtKponAWe38";
		
		mockMvc.perform(MockMvcRequestBuilders.get("/discountcoupon/" + code).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}

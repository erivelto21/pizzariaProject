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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.pizzaria.config.Application;
import br.com.pizzaria.domain.Role;
import br.com.pizzaria.domain.SystemUser;
import br.com.pizzaria.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class UserRestControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testNewUser() throws Exception {
		Role role = new Role();
		role.setId(2);

		SystemUser user = new SystemUser();
		user.setRole(role);
		user.setFirstName("test");
		user.setLastName("1");
		user.setEmail("test@email.com");
		user.setPassword("test@email.com");

//		this.controller.save(user);
		
		String u = JsonUtil.objectToJson(user);
		System.out.println(u);
		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON_VALUE).contentType(u)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

		System.out.println(result.getResponse().getContentAsString());
	}
}

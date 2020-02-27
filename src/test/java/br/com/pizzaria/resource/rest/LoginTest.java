package br.com.pizzaria.resource.rest;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.pizzaria.config.Application;
import br.com.pizzaria.domain.SystemUser;
import br.com.pizzaria.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class LoginTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(springSecurityFilterChain)
				.build();
	}

	@Test
	public void testFailedLogin() throws Exception {
		SystemUser user = new SystemUser();
		user.setEmail("adm@email.com");
		user.setPassword("13");

		String u = JsonUtil.objectToJson(user);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON_VALUE).content(u))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	public void TestgetAuthenticatedOrder() throws Exception {
		SystemUser user = new SystemUser();
		user.setEmail("adm@email.com");
		user.setPassword("123");

		String u = JsonUtil.objectToJson(user);

		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON_VALUE).content(u))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		String s = result.getResponse().getHeader("Authorization").replace("Bearer", "");

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/order/102").header("Authorization", s)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void TestgetNotAuthenticatedOrder() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/order/102").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	public void testJWTLogin() throws Exception {
		SystemUser user = new SystemUser();
		user.setEmail("adm@email.com");
		user.setPassword("123");

		String u = JsonUtil.objectToJson(user);

		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON_VALUE).content(u))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		assertTrue(result.getResponse().getHeader("Authorization").length() > 0);
	}

	@Test
	public void testSuccessfulLogin() throws Exception {
		SystemUser user = new SystemUser();
		user.setEmail("adm@email.com");
		user.setPassword("123");

		String u = JsonUtil.objectToJson(user);

		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON_VALUE).content(u))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		assertTrue(result.getResponse().getHeader("Authorization").length() > 0);
	}
}

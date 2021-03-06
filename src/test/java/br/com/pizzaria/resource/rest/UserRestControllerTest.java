package br.com.pizzaria.resource.rest;

import static org.junit.Assert.assertNotSame;

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
import br.com.pizzaria.domain.Address;
import br.com.pizzaria.domain.Role;
import br.com.pizzaria.domain.SystemUser;
import br.com.pizzaria.service.SystemUserService;
import br.com.pizzaria.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserRestControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private SystemUserService service;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testCreateANewAddress() throws Exception {
		Address address = new Address();
		address.setCity("Testx");
		address.setNeighborhood("testx");
		address.setNumber(1);
		address.setState("testx");
		address.setStreet("testx");
		
		String a = JsonUtil.objectToJson(address);
		
		this.mockMvc
		.perform(MockMvcRequestBuilders.patch("/user/10/address").contentType(MediaType.APPLICATION_JSON_VALUE).content(a))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testCreateANewPhone() throws Exception {
		String phone = "(31) 3333-3131";
		
		this.mockMvc
		.perform(MockMvcRequestBuilders.patch("/user/1/phone").contentType(MediaType.APPLICATION_JSON_VALUE).content(phone))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testCreateANewUser() throws Exception {
		Role role = new Role();
		role.setId(2);

		SystemUser user = new SystemUser();
		user.setRole(role);
		user.setFirstName("test");
		user.setLastName("1");
		user.setEmail("tet4@email.com");
		user.setPassword("test@email.com");

		String u = JsonUtil.objectToJson(user);

		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON_VALUE).content(u))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
		
		ObjectMapper objectMapper = new ObjectMapper();
		user = objectMapper.readValue(result.getResponse().getContentAsString(), SystemUser.class);
		
		assertNotSame(0L, user.getId());
	}
	
	@Test
	public void testEmailIsinvalid() throws Exception {
		Role role = new Role();
		role.setId(2);

		SystemUser user = new SystemUser();
		user.setRole(role);
		user.setFirstName("test");
		user.setLastName("1");
		user.setEmail("tet1111email.com");
		user.setPassword("test@email.com");

		String u = JsonUtil.objectToJson(user);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON_VALUE).content(u))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void testEmailIsInUse() throws Exception {
		Role role = new Role();
		role.setId(2);

		SystemUser user = new SystemUser();
		user.setRole(role);
		user.setFirstName("test");
		user.setLastName("1");
		user.setEmail("tet@email.com");
		user.setPassword("test@email.com");

		String u = JsonUtil.objectToJson(user);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON_VALUE).content(u))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void testInvalidAddress() throws Exception {
		Address address = new Address();
		address.setCity("Testx");
		address.setNeighborhood("testx");
		address.setNumber(0);
		address.setState("qq");
		address.setStreet("testx");
		
		String a = JsonUtil.objectToJson(address);
		
		this.mockMvc
		.perform(MockMvcRequestBuilders.patch("/user/10/address").contentType(MediaType.APPLICATION_JSON_VALUE).content(a))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void testInvalidPhone() throws Exception {
		String phone = "(85) q3434-2799";
		
		this.mockMvc
		.perform(MockMvcRequestBuilders.patch("/user/10/phone").contentType(MediaType.APPLICATION_JSON_VALUE).content(phone))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void testInvalidUserPassword() throws Exception {
		SystemUser user = this.service.get(10L);
		
		user.setPassword("123");

		this.mockMvc
				.perform(MockMvcRequestBuilders.patch("/user/" + user.getId() + "/password")
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(user.getPassword()))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void testNullNewAddress() throws Exception {
		Address address = null;

		String a = JsonUtil.objectToJson(address);
		
		this.mockMvc
		.perform(MockMvcRequestBuilders.patch("/user/1/address").contentType(MediaType.APPLICATION_JSON_VALUE).content(a))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void testNullNewPhone() throws Exception {
		String phone = "";
		
		this.mockMvc
		.perform(MockMvcRequestBuilders.patch("/user/10/phone").contentType(MediaType.APPLICATION_JSON_VALUE).content(phone))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void testNullNewUser() throws Exception {
		Role role = new Role();
		role.setId(2);

		SystemUser user = new SystemUser();
		user.setRole(role);
		user.setFirstName("test");
		user.setLastName("1");
		user.setEmail("tet@email.com");
		user.setPassword("test@email.com");
		user = null;
		String u = JsonUtil.objectToJson(user);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON_VALUE).content(u))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void testUpdateUserPassword() throws Exception {
		SystemUser user = this.service.get(10L);
		
		user.setPassword("123456781");

		this.mockMvc
				.perform(MockMvcRequestBuilders.patch("/user/" + user.getId() + "/password")
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(user.getPassword()))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}

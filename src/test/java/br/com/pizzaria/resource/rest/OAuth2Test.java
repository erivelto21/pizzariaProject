package br.com.pizzaria.resource.rest;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import br.com.pizzaria.config.Application;
import br.com.pizzaria.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class OAuth2Test {

	private MockMvc mockMvc;
	private String url = "/oauth/token";
	private String clientid = "Y2xpZW50aWRfcGl6emFyaWE=";
	private String clientSecret = "U2VjcmV0X2NsaWVudF9waXp6YXJpYQ==";

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
	public void testAuthentication() throws Exception{
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "password");
	    params.add("username", "adm@email.com");
	    params.add("password", "123");
	    
	    MvcResult result 
	      = mockMvc.perform(MockMvcRequestBuilders.post(this.url)
	        .params(params)
	        .with(SecurityMockMvcRequestPostProcessors.httpBasic(this.clientid,this.clientSecret))
	        .accept("application/json;charset=UTF-8"))
	        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	 
	    String token = JsonUtil.getJsonValue(result.getResponse().getContentAsString(), "access_token");
	    String refreshToken = JsonUtil.getJsonValue(result.getResponse().getContentAsString(), "refresh_token");

	    assertTrue(!token.isEmpty());
	    assertTrue(!refreshToken.isEmpty());
	}
	
	@Test
	public void testBadCredentials() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "password");
	    params.add("username", "adm@email.com");
	    params.add("password", "13");
	    
	    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(this.url)
	        .params(params)
	        .with(SecurityMockMvcRequestPostProcessors.httpBasic(this.clientid,this.clientSecret))
	        .accept("application/json;charset=UTF-8"))
	        .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
	    
	    String errorDescription = JsonUtil.getJsonValue(result.getResponse().getContentAsString(), "error_description");
	    
	    assertTrue(errorDescription.equals("Bad credentials"));
	}
	
	@Test
	public void testFailedAuthentication() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "password");
	    params.add("username", "adm@email.com");
	    params.add("password", "123");
	    
	    mockMvc.perform(MockMvcRequestBuilders.post("/oauth/token")
	        .params(params)
	        .accept("application/json;charset=UTF-8"))
	        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}
	
	@Test
	public void testRefreshToken() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "password");
	    params.add("username", "adm@email.com");
	    params.add("password", "123");
	    
	    MvcResult result 
	      = mockMvc.perform(MockMvcRequestBuilders.post(this.url)
	        .params(params)
	        .with(SecurityMockMvcRequestPostProcessors.httpBasic(this.clientid,this.clientSecret))
	        .accept("application/json;charset=UTF-8"))
	        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	 
	    String token = JsonUtil.getJsonValue(result.getResponse().getContentAsString(), "access_token");
	    String refreshToken = JsonUtil.getJsonValue(result.getResponse().getContentAsString(), "refresh_token");

	    assertTrue(!token.isEmpty());
	    assertTrue(!refreshToken.isEmpty());

		params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "refresh_token");
	    params.add("refresh_token", refreshToken);

	    result = mockMvc.perform(MockMvcRequestBuilders.post(this.url)
	        .params(params)
	        .with(SecurityMockMvcRequestPostProcessors.httpBasic(this.clientid,this.clientSecret))
	        .accept("application/json;charset=UTF-8"))
	        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	    
	    token = JsonUtil.getJsonValue(result.getResponse().getContentAsString(), "access_token");

	    assertTrue(!token.isEmpty());
	}
}

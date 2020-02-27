package br.com.pizzaria.resource.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import br.com.pizzaria.config.Application;
import br.com.pizzaria.domain.CreditCard;
import br.com.pizzaria.domain.CustomFlavor;
import br.com.pizzaria.domain.CustomIngredient;
import br.com.pizzaria.domain.Customer;
import br.com.pizzaria.domain.Pizza;
import br.com.pizzaria.domain.SystemUser;
import br.com.pizzaria.domain.Type;
import br.com.pizzaria.service.SystemUserService;
import br.com.pizzaria.util.JsonUtil;
import br.com.pizzaria.util.Validation.CustomerValidation;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class PaymentRestControllerTest {

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
	public void testCreateNewCreditCardPaidPayment() throws Exception {
		SystemUser user = this.service.getSystemUser("adm@email.com");

		CreditCard credit = new CreditCard();
		credit.setCardCvv(123);
		credit.setCardExpirationDate("0922");
		credit.setCardHolderName("Morpheus Fishburne");
		credit.setCardNumber(4111111111111111L);

		CustomIngredient i = new CustomIngredient();
		i.setId(0);
		i.setAmount(1);
		i.setName("Palmito");

		List<CustomIngredient> is = new ArrayList<CustomIngredient>();
		is.add(i);

		CustomFlavor customFlavor = new CustomFlavor();
		customFlavor.setName("Palmito");
		customFlavor.setType(Type.SALGADA);
		customFlavor.setCustomIngredient(is);
		customFlavor.setImage("/assets/img/Palmito.jpg");
		customFlavor.setPrice(new BigDecimal("17.00"));
		customFlavor.setAdditionalsValue(new BigDecimal("2"));

		Pizza pizza = new Pizza();
		pizza.setAmount(1);
		pizza.setCustomFlavor(customFlavor);

		List<Pizza> lista = new ArrayList<Pizza>();
		lista.add(pizza);

		Customer customer = new Customer();
		customer.setSystemUser(user);
		customer.setCart(lista);
		customer.setCreditCard(credit);
		customer.setPaymentWay("Cartão de crédito");

		CustomerValidation.customerIsValid(customer);

		String c = JsonUtil.objectToJson(customer);

		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/payment/creditcard")
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(c))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

		String r = JsonUtil.getJsonValue(result.getResponse().getContentAsString(), "statusValue");

		assertEquals("paid", r);
	}

	@Test
	public void testCreateNewCreditCardRefusedPayment() throws Exception {
		SystemUser user = this.service.getSystemUser("adm@email.com");

		CreditCard credit = new CreditCard();
		credit.setCardCvv(123);
		credit.setCardExpirationDate("0922");
		credit.setCardHolderName("Morpheus Fishburne");
		credit.setCardNumber(4111111111111112L);

		CustomIngredient i = new CustomIngredient();
		i.setId(0);
		i.setAmount(1);
		i.setName("Palmito");

		List<CustomIngredient> is = new ArrayList<CustomIngredient>();
		is.add(i);

		CustomFlavor customFlavor = new CustomFlavor();
		customFlavor.setName("Palmito");
		customFlavor.setType(Type.SALGADA);
		customFlavor.setCustomIngredient(is);
		customFlavor.setImage("/assets/img/Palmito.jpg");
		customFlavor.setPrice(new BigDecimal("17.00"));
		customFlavor.setAdditionalsValue(new BigDecimal("2"));

		Pizza pizza = new Pizza();
		pizza.setAmount(1);
		pizza.setCustomFlavor(customFlavor);

		List<Pizza> lista = new ArrayList<Pizza>();
		lista.add(pizza);

		Customer customer = new Customer();
		customer.setSystemUser(user);
		customer.setCart(lista);
		customer.setCreditCard(credit);
		customer.setPaymentWay("Cartão de crédito");

		CustomerValidation.customerIsValid(customer);

		String c = JsonUtil.objectToJson(customer);

		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/payment/creditcard")
						.contentType(MediaType.APPLICATION_JSON_VALUE).content(c))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

		String r = JsonUtil.getJsonValue(result.getResponse().getContentAsString(), "statusValue");

		assertEquals("refused", r);
	}

	@Test
	public void testInvalideNewCreditCardPayment() throws Exception {
		SystemUser user = this.service.getSystemUser("adm@email.com");

		CreditCard credit = new CreditCard();
		credit.setCardCvv(123);
		credit.setCardExpirationDate("0922");
		credit.setCardHolderName("Morpheus Fishburne");
		credit.setCardNumber(4111111111111111L);

		CustomIngredient i = new CustomIngredient();
		i.setId(0);
		i.setAmount(1);
		i.setName("Palmito");

		List<CustomIngredient> is = new ArrayList<CustomIngredient>();
		is.add(i);

		CustomFlavor customFlavor = new CustomFlavor();
		customFlavor.setName("Palmito");
		customFlavor.setType(Type.SALGADA);
		customFlavor.setCustomIngredient(is);
		customFlavor.setImage("/assets/img/Palmito.jpg");
		customFlavor.setPrice(new BigDecimal("17.00"));
		customFlavor.setAdditionalsValue(new BigDecimal("2"));

		Pizza pizza = new Pizza();
		pizza.setAmount(1);
		pizza.setCustomFlavor(customFlavor);

		List<Pizza> lista = new ArrayList<Pizza>();
		lista.add(pizza);

		Customer customer = new Customer();
		customer.setSystemUser(user);
		customer.setCart(lista);
		customer.setCreditCard(credit);
		customer.setPaymentWay("Cartã de crédito");

		String c = JsonUtil.objectToJson(customer);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/payment/creditcard").contentType(MediaType.APPLICATION_JSON_VALUE).content(c))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
	}
}

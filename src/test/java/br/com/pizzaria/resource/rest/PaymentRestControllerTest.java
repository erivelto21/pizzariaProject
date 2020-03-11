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
import br.com.pizzaria.domain.Dough;
import br.com.pizzaria.domain.Pizza;
import br.com.pizzaria.domain.PizzaEdge;
import br.com.pizzaria.domain.Size;
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
		customFlavor.setIngredients(is);
		customFlavor.setImage("/assets/img/Palmito.jpg");
		customFlavor.setPrice(new BigDecimal("17.00"));
		
		CustomIngredient i1 = new CustomIngredient();
		i1.setId(0);
		i1.setAmount(2);
		i1.setName("Nutella");
		
		List<CustomIngredient> is1 = new ArrayList<CustomIngredient>();
		is1.add(i1);
		
		i1 = new CustomIngredient();
		i1.setId(0);
		i1.setAmount(3);
		i1.setName("Chocolate");
		is1.add(i1);
		
		CustomFlavor customFlavor1 = new CustomFlavor();
		customFlavor1.setName("Nutella");
		customFlavor1.setType(Type.DOCE);
		customFlavor1.setIngredients(is1);
		customFlavor1.setImage("/assets/img/Nutella.jpg");
		customFlavor1.setPrice(new BigDecimal("20.20"));

		Pizza pizza = new Pizza();
		pizza.setAmount(1);
		pizza.setCustomFlavor(customFlavor);
		pizza.setDough(Dough.NOVAIORQUINA);
		pizza.setSize(Size.GRANDE);
		pizza.setPizzaEdge(PizzaEdge.CHEDDAR);
		pizza.setAdditionalsValue(new BigDecimal("2"));

		Pizza pizza1 = new Pizza();
		pizza1.setAmount(2);
		pizza1.setCustomFlavor(customFlavor1);
		pizza1.setDough(Dough.TRADICIONAL);
		pizza1.setSize(Size.EXTRAGRANDE);
		pizza1.setPizzaEdge(PizzaEdge.CATUPIRY);
		pizza1.setAdditionalsValue(new BigDecimal("4"));

		List<Pizza> lista = new ArrayList<Pizza>();
		lista.add(pizza);
		lista.add(pizza1);

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
	public void testCreateNewCreditCardPaidPaymentWithAdditionals() throws Exception {
		SystemUser user = this.service.getSystemUser("adm@email.com");

		CreditCard credit = new CreditCard();
		credit.setCardCvv(123);
		credit.setCardExpirationDate("0922");
		credit.setCardHolderName("Morpheus Fishburne");
		credit.setCardNumber(4111111111111111L);

		CustomIngredient i = new CustomIngredient();
		i.setId(0);
		i.setAmount(3);
		i.setName("Molho");
		
		CustomIngredient i1 = new CustomIngredient();
		i1.setId(0);
		i1.setAmount(3);
		i1.setName("Orégano");
		
		CustomIngredient i2 = new CustomIngredient();
		i2.setId(0);
		i2.setAmount(1);
		i2.setName("Mussarela");
		
		CustomIngredient i3 = new CustomIngredient();
		i3.setId(0);
		i3.setAmount(3);
		i3.setName("Calabresa");
		
		CustomIngredient i4 = new CustomIngredient();
		i4.setId(0);
		i4.setAmount(3);
		i4.setName("Cebola");

		List<CustomIngredient> is = new ArrayList<CustomIngredient>();
		is.add(i);
		is.add(i1);
		is.add(i2);
		is.add(i3);
		is.add(i4);

		CustomFlavor customFlavor = new CustomFlavor();
		customFlavor.setName("Calabresa");
		customFlavor.setType(Type.SALGADA);
		customFlavor.setIngredients(is);
		customFlavor.setImage("/assets/img/Calabresa.jpg");
		customFlavor.setPrice(new BigDecimal("16.50"));

		Pizza pizza = new Pizza();
		pizza.setAmount(1);
		pizza.setCustomFlavor(customFlavor);
		pizza.setDough(Dough.NOVAIORQUINA);
		pizza.setSize(Size.EXTRAGRANDE);
		pizza.setPizzaEdge(PizzaEdge.CHEDDAR);
		pizza.setAdditionalsValue(new BigDecimal("0"));

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
		customFlavor.setIngredients(is);
		customFlavor.setImage("/assets/img/Palmito.jpg");
		customFlavor.setPrice(new BigDecimal("17.00"));
		
		CustomIngredient i1 = new CustomIngredient();
		i1.setId(0);
		i1.setAmount(2);
		i1.setName("Nutella");
		
		List<CustomIngredient> is1 = new ArrayList<CustomIngredient>();
		is1.add(i1);
		
		i1 = new CustomIngredient();
		i1.setId(0);
		i1.setAmount(3);
		i1.setName("Chocolate");
		is1.add(i1);
		
		CustomFlavor customFlavor1 = new CustomFlavor();
		customFlavor1.setName("Nutella");
		customFlavor1.setType(Type.DOCE);
		customFlavor1.setIngredients(is1);
		customFlavor1.setImage("/assets/img/Nutella.jpg");
		customFlavor1.setPrice(new BigDecimal("20.20"));

		Pizza pizza = new Pizza();
		pizza.setAmount(1);
		pizza.setCustomFlavor(customFlavor);
		pizza.setDough(Dough.NOVAIORQUINA);
		pizza.setSize(Size.GRANDE);
		pizza.setPizzaEdge(PizzaEdge.CHEDDAR);
		pizza.setAdditionalsValue(new BigDecimal("2"));

		Pizza pizza1 = new Pizza();
		pizza1.setAmount(2);
		pizza1.setCustomFlavor(customFlavor1);
		pizza1.setDough(Dough.TRADICIONAL);
		pizza1.setSize(Size.EXTRAGRANDE);
		pizza1.setPizzaEdge(PizzaEdge.CATUPIRY);
		pizza1.setAdditionalsValue(new BigDecimal("4"));
		
		List<Pizza> lista = new ArrayList<Pizza>();
		lista.add(pizza);
		lista.add(pizza1);

		Customer customer = new Customer();
		customer.setSystemUser(user);
		customer.setCart(lista);
		customer.setCreditCard(credit);
		customer.setPaymentWay("Cartão de crédito");

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
		customFlavor.setName("");
		customFlavor.setType(Type.SALGADA);
		customFlavor.setIngredients(is);
		customFlavor.setImage("/assets/img/Palmito.jpg");
		customFlavor.setPrice(new BigDecimal("17.00"));
		
		CustomIngredient i1 = new CustomIngredient();
		i1.setId(0);
		i1.setAmount(2);
		i1.setName("Nutella");
		
		List<CustomIngredient> is1 = new ArrayList<CustomIngredient>();
		is1.add(i1);
		
		i1 = new CustomIngredient();
		i1.setId(0);
		i1.setAmount(3);
		i1.setName("Chocolate");
		is1.add(i1);
		
		CustomFlavor customFlavor1 = new CustomFlavor();
		customFlavor1.setName("Nutella");
		customFlavor1.setType(Type.DOCE);
		customFlavor1.setIngredients(is1);
		customFlavor1.setImage("/assets/img/Nutella.jpg");
		customFlavor1.setPrice(new BigDecimal("20.20"));

		Pizza pizza = new Pizza();
		pizza.setAmount(1);
		pizza.setCustomFlavor(customFlavor);
		pizza.setDough(Dough.NOVAIORQUINA);
		pizza.setSize(Size.GRANDE);
		pizza.setPizzaEdge(PizzaEdge.CHEDDAR);
		pizza.setAdditionalsValue(new BigDecimal("2"));

		Pizza pizza1 = new Pizza();
		pizza1.setAmount(2);
		pizza1.setCustomFlavor(customFlavor1);
		pizza1.setDough(Dough.TRADICIONAL);
		pizza1.setSize(Size.EXTRAGRANDE);
		pizza1.setPizzaEdge(PizzaEdge.CATUPIRY);
		pizza1.setAdditionalsValue(new BigDecimal("4"));
		
		List<Pizza> lista = new ArrayList<Pizza>();
		lista.add(pizza);
		lista.add(pizza1);

		Customer customer = new Customer();
		customer.setSystemUser(user);
		customer.setCart(lista);
		customer.setCreditCard(credit);
		customer.setPaymentWay("Cartão de crédito");

		String c = JsonUtil.objectToJson(customer);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/payment/creditcard").contentType(MediaType.APPLICATION_JSON_VALUE).content(c))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
	}
}

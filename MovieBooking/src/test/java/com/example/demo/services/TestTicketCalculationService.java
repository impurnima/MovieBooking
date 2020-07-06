package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = TicketCalculationServiceImp.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class TestTicketCalculationService {
	
	@Autowired
	TicketCalculationService ticketCalculationService;
	
	@Value("${category.cost.platinum}")
	private Integer platinumcost;

	@Value("${category.cost.gold}")
	private Integer goldcost;

	@Value("${tax.service}")
	private float servicetax;
	
	@Value("${tax.swachhbharatcess}")
	private float swachhbharatcesstax;
	
	@Value("${tax.krishikalyancess}")
	private float krishikalyancesstax;
	
	@Value("${category.cost.silver}")
	private Integer silvercost;
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("------------------ Test Starts-------------------------");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("------------------ Test Ends-------------------------");
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSubtotalOfDiffCategory() {
		List<String> seats=new ArrayList<>();
		seats.add("A1");
		seats.add("B1");
		seats.add("C1");
		assertEquals(platinumcost+goldcost+silvercost, ticketCalculationService.getSubtotal(seats));
	}
	
	@Test
	void testSubtotalBeyondCategory() {
		List<String> seats=new ArrayList<>();
		seats.add("G1");
		seats.add("F1");
		seats.add("E1");
		assertEquals(0, ticketCalculationService.getSubtotal(seats));
	}
	
	@Test
	void testServiceTexLessThanZero() {
		assertEquals(0, ticketCalculationService.getServiceTax(-1));
	}
	
	@Test
	void testServiceTexOfZero() {
		assertEquals(0, ticketCalculationService.getServiceTax(0));
	}
	
	@Test
	void testServiceTexOfOne() {
		assertEquals(servicetax/100, ticketCalculationService.getServiceTax(1));
	}
	
	@Test
	void testSwachhBharatCessLessThenZero() {
		assertEquals(0, ticketCalculationService.getSwachhBharatCess(-1));
	}
	
	@Test
	void testSwachhBharatCessOfZero() {
		assertEquals(0, ticketCalculationService.getSwachhBharatCess(0));
	}
	
	@Test
	void testSwachhBharatCessOfOne() {
		assertEquals(swachhbharatcesstax/100, ticketCalculationService.getSwachhBharatCess(1));
	}
	
	@Test
	void testKrishiKalyanCessThenZero() {
		assertEquals(0, ticketCalculationService.getKrishiKalyanCess(-1));
	}
	
	@Test
	void testKrishiKalyanCessOfZero() {
		assertEquals(0, ticketCalculationService.getKrishiKalyanCess(0));
	}
	
	@Test
	void testKrishiKalyanCessOfOne() {
		assertEquals(krishikalyancesstax/100, ticketCalculationService.getKrishiKalyanCess(1));
	}

}

package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = SeatBookingServicesImp.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class TestSeatBookingServices {

	@Autowired
	SeatBookingServices seatBookingServices;

	@Value("${audi}")
	private Integer audi;


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
	void testTheatreIsNotEmpty() {
		boolean status = false;
		HashMap<Integer, HashMap<String, List<String>>> theater = seatBookingServices.getTheatre();
		if (!theater.isEmpty())
			status = true;
		assertEquals(true, status);
	}

	@Test
	void testGetAudiGreaterThenAudiSize() {
		HashMap<Integer, HashMap<String, List<String>>> theater = seatBookingServices.getTheatre();
		int audino = audi + 1;
		Optional<HashMap<String, List<String>>> opt = Optional.ofNullable(seatBookingServices.getAudi(theater, audino));
		assertEquals(opt.isPresent(), false);
	}
	
	@Test
	void testGetAudiNoZero() {
		HashMap<Integer, HashMap<String, List<String>>> theater = seatBookingServices.getTheatre();
		Optional<HashMap<String, List<String>>> opt = Optional.ofNullable(seatBookingServices.getAudi(theater, 0));
		assertEquals(opt.isPresent(), false);
	}

	@Test
	void checkSeatsAvailableFromDiffCategory() {		
		Optional<Boolean> opt = Optional.ofNullable(seatBookingServices.checkAllSeatsAvailable(seatBookingServices.getAudi(seatBookingServices.getTheatre(), 1),
				Arrays.asList(new String[] {"A1,B2,C2"})));
		assertEquals(opt.isPresent(), true);
	}
	
	@Test
	void checkSeatsAvailableGreaterThanCategorySeats() {		
		Optional<Boolean> opt = Optional.ofNullable(seatBookingServices.checkAllSeatsAvailable(seatBookingServices.getAudi(seatBookingServices.getTheatre(), 1),
				Arrays.asList(new String[] {"A10,B15,C19"})));
		if(opt.isPresent()) {
			assertEquals(opt.get(), false);
		}
	}
	
	@Test
	void checkSeatsAvailableBeyondCategorySeats() {		
		Optional<Boolean> opt = Optional.ofNullable(seatBookingServices.checkAllSeatsAvailable(seatBookingServices.getAudi(seatBookingServices.getTheatre(), 1),
				Arrays.asList(new String[] {"G0,F1,G4"})));
		if(opt.isPresent()) {
			assertEquals(opt.get(), false);
		}
	}
	
	@Test
	void seatsNotAvailableGreaterThanCategorySeats() {		
		Optional<String> opt = Optional.ofNullable(seatBookingServices.seatsNotAvailable(seatBookingServices.getAudi(seatBookingServices.getTheatre(), 1),
				Arrays.asList(new String[] {"A10,B15,C19"})));
		if(opt.isPresent()) {
			assertEquals(opt.get(), "A10,B15,C19 Not Available.Please select difference seats.");
		}
	}
	
	@Test
	void seatsNotAvailableBeyondCategorySeats() {		
		Optional<String> opt = Optional.ofNullable(seatBookingServices.seatsNotAvailable(seatBookingServices.getAudi(seatBookingServices.getTheatre(), 1),
				Arrays.asList(new String[] {"G0,F1,G4"})));
		if(opt.isPresent()) {
			assertEquals(opt.get(), "Please select difference seats.");
		}
	}
	
	@Test
	void bookSeatsBeyondCategory() {
		Optional<HashMap<Integer, HashMap<String, List<String>>>> opt = Optional.ofNullable(seatBookingServices.bookSeats(seatBookingServices.getTheatre(), 1, Arrays.asList(new String[] {"G0,F1,G4"})));
		if(opt.isPresent()) {
			assertEquals(opt.get(), seatBookingServices.getTheatre());
		}
	}
}

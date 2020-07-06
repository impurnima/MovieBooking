package com.example.demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.services.SeatBookingServices;
import com.example.demo.services.TicketCalculationService;

@SpringBootApplication
public class MovieBookingApplication implements CommandLineRunner {

	@Autowired
	SeatBookingServices seatBookingServices;
	@Autowired
	TicketCalculationService ticketCalculationService;
	static Logger logger = LoggerFactory.getLogger(MovieBookingApplication.class);

	@Value("${initial.bookseats.audi1}")
	private String audi1;

	@Value("${initial.bookseats.audi2}")
	private String audi2;

	@Value("${initial.bookseats.audi3}")
	private String audi3;

	@Value("${tax.service}")
	private float servicetaxval;

	@Value("${tax.swachhbharatcess}")
	private float swachhbharatcesstaxval;

	@Value("${tax.krishikalyancess}")
	private float krishikalyancesstaxval;

	Consumer<HashMap<String, List<String>>> print = t -> System.out.println(t);

	public static void main(String[] args) {
		SpringApplication.run(MovieBookingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		HashMap<Integer, HashMap<String, List<String>>> theater = seatBookingServices.getTheatre();
		// ---------------------Initial seats booking------------------
		theater = seatBookingServices.bookSeats(theater, 1, Arrays.asList(audi1.split(",")));
		theater = seatBookingServices.bookSeats(theater, 2, Arrays.asList(audi2.split(",")));
		theater = seatBookingServices.bookSeats(theater, 3, Arrays.asList(audi3.split(",")));
		// ---------------------End Initial seats booking--------------
		System.out.println("Show 1 Running in Audi 1: \n" + seatBookingServices.getAudi(theater, 1));
		System.out.println("Show 2 Running in Audi 2: \n" + seatBookingServices.getAudi(theater, 2));
		System.out.println("Show 3 Running in Audi 3: \n" + seatBookingServices.getAudi(theater, 3));

		try (Scanner scn = new Scanner(System.in)) {
			System.out.println("Enter Show no:");
			int audiNo = scn.nextInt();
			for (;;) {
				System.out.println("Enter seats:");
				String seats = scn.next();
				String[] seatsList = seats.split(",");

				List<String> bookseats = Arrays.asList(seatsList);

				if (!seatBookingServices.checkAllSeatsAvailable(seatBookingServices.getAudi(theater, audiNo),
						bookseats)) {
					String msg = seatBookingServices.seatsNotAvailable(seatBookingServices.getAudi(theater, audiNo),
							bookseats);
					System.out.println("Print:" + msg);
				} else {
					theater = seatBookingServices.bookSeats(theater, audiNo, bookseats);
					System.out.println("Print: Successfully Booked - Show " + audiNo);
					Integer subtotal = ticketCalculationService.getSubtotal(bookseats);
					System.out.println("Subtotal: Rs. " + subtotal);
					float servicetax = ticketCalculationService.getServiceTax(subtotal);
					System.out.println(
							"Service Tax @" + servicetaxval + "%: Rs. " + Math.round(servicetax * 100.0) / 100.0);
					float swachhbharatcesstax = ticketCalculationService.getSwachhBharatCess(subtotal);
					System.out.println("Swachh Bharat Cess @" + swachhbharatcesstaxval + "%: Rs. "
							+ Math.round(swachhbharatcesstax * 100.0) / 100.0);
					float krishikalyancesstax = ticketCalculationService.getKrishiKalyanCess(subtotal);
					System.out.println("Krishi Kalyan Cess @" + krishikalyancesstaxval + "%: Rs. "
							+ Math.round(krishikalyancesstax * 100.0) / 100.0);
					int total = Math.round(subtotal + servicetax + swachhbharatcesstax + krishikalyancesstax);
					System.out.println("Total: Rs. " + total);
					break;
				}
			}
			scn.close();
		}
	}
}

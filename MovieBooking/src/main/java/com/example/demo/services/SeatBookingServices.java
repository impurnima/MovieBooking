package com.example.demo.services;

import java.util.HashMap;
import java.util.List;


public interface SeatBookingServices {
	public HashMap<Integer,HashMap<String, List<String>>> getTheatre();
	public HashMap<String, List<String>> getAudi(HashMap<Integer,HashMap<String, List<String>>> theater,int audiNo);
	public boolean checkAllSeatsAvailable(HashMap<String, List<String>> availableSeats, List<String> bookseats);
	public String seatsNotAvailable(HashMap<String, List<String>> availableSeats, List<String> bookseats);
	public HashMap<Integer,HashMap<String, List<String>>> bookSeats(HashMap<Integer,HashMap<String, List<String>>> theater,int audiNo,List<String> bookseats);
}

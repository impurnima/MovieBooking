package com.example.demo.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SeatBookingServicesImp implements SeatBookingServices {
	@Value("${audi}")
	private Integer audi;

	@Value("${audi.category}")
	private Integer category;

	@Value("${audi.category.seats}")
	private Integer categoryseats;

	public static <K, V> V getValue(HashMap<K, V> map, K key) {
		for (Map.Entry<K, V> entry : map.entrySet()) {
			if (key.equals(entry.getKey())) {
				return entry.getValue();
			}
		}
		return null;
	}

	BiFunction<HashMap<String, List<String>>, List<String>, Boolean> avail = (availseats, bookseats) -> {
		Boolean status = false;

		for (String seat : bookseats) {
			for (Map.Entry<String, List<String>> category : availseats.entrySet()) {
				if (category.getKey().equals(String.valueOf(seat.charAt(0)))) {
					if (category.getValue().contains(seat)) {
						status = true;
					} else {
						status = false;
						break;
					}
				}
			}
		}
		return status;
	};
	BiFunction<HashMap<String, List<String>>, List<String>, String> notavailableseats = (availseats, bookseats) -> {
		String status = "";
		for (String seat : bookseats) {
			for (Map.Entry<String, List<String>> category : availseats.entrySet()) {
				if (category.getKey().equals(String.valueOf(seat.charAt(0)))) {
					if (!category.getValue().contains(seat)) {
						status += seat + " Not Available.";
					}
				}
			}
		}
		if (!status.isEmpty()) {
			status += "Please select difference seats.";
		}else if(status.isEmpty()) {
			status = "Please select difference seats.";
		}
		return status;
	};

	@Override
	public HashMap<Integer, HashMap<String, List<String>>> getTheatre() {

		HashMap<Integer, HashMap<String, List<String>>> theatre = new HashMap<Integer, HashMap<String, List<String>>>();
		for (int a = 1; a <= audi; a++) {
			HashMap<String, List<String>> categorySeats = new HashMap<>();
			for (int i = 1; i <= category; i++) {
				List<String> platinumseats = new ArrayList<String>();
				List<String> goldseats = new ArrayList<String>();
				List<String> silverseats = new ArrayList<String>();
				for (int j = 1; j <= categoryseats; j++) {
					platinumseats.add("A" + j);
					goldseats.add("B" + j);
					silverseats.add("C" + j);					
				}
				categorySeats.put("A", platinumseats);
				categorySeats.put("B", goldseats);
				categorySeats.put("C", silverseats);
			}
			theatre.put(a, categorySeats);
		}

		return theatre;
	}

	@Override
	public HashMap<String, List<String>> getAudi(HashMap<Integer, HashMap<String, List<String>>> theater, int audiNo) {
		return getValue(theater, audiNo);
	}

	@Override
	public boolean checkAllSeatsAvailable(HashMap<String, List<String>> availableSeats, List<String> bookseats) {
		return avail.apply(availableSeats, bookseats);
	}

	@Override
	public String seatsNotAvailable(HashMap<String, List<String>> availableseats, List<String> bookseats) {
		return notavailableseats.apply(availableseats, bookseats);
	}

	@Override
	public HashMap<Integer, HashMap<String, List<String>>> bookSeats(
			HashMap<Integer, HashMap<String, List<String>>> theater, int audiNo, List<String> bookseats) {
		HashMap<String, List<String>> audi = getValue(theater, audiNo);
		
		for (String seat : bookseats) {
			for (Map.Entry<String, List<String>> category : audi.entrySet()) {
				if (category.getKey().equals(String.valueOf(seat.charAt(0)))) {
					List<String> booked = category.getValue();
					if (booked.remove(seat)) {
						audi.replace(category.getKey(), booked);
					} else {
						break;
					}
				}
				theater.replace(audiNo, audi);
			}
		}
		return theater;
	}

}

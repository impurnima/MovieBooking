package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TicketCalculationServiceImp implements TicketCalculationService {
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

	@Override
	public Integer getSubtotal(List<String> bookseats) {
		Integer subtotal = 0;
		for (String category : bookseats) {
			if (category.startsWith("A")) {
				subtotal += platinumcost;
			} else if (category.startsWith("B")) {
				subtotal += goldcost;
			} else if (category.startsWith("C")) {
				subtotal += silvercost;
			}
		}
		return subtotal;
	}

	@Override
	public float getServiceTax(int subtotal) {
		if (subtotal > 0) {
			return (float) servicetax / 100 * subtotal;
		}
		return 0;
	}

	@Override
	public float getSwachhBharatCess(int subtotal) {
		if (subtotal > 0) {
			return (float) swachhbharatcesstax / 100 * subtotal;
		} else {
			return 0;
		}
	}

	@Override
	public float getKrishiKalyanCess(int subtotal) {
		if (subtotal > 0) {
			return (float) krishikalyancesstax / 100 * subtotal;
		} else {
			return 0;
		}
	}

}

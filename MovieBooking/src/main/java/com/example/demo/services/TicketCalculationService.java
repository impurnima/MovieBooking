package com.example.demo.services;

import java.util.List;

public interface TicketCalculationService {
	public Integer getSubtotal(List<String> bookseats);

	public float getServiceTax(int subtotal);

	public float getSwachhBharatCess(int subtotal);
	public float getKrishiKalyanCess(int subtotal);
}

package com.narola.pharmacy.paymentgateway;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestOrderResponseEntity {

	
	
	@Test
	public void testPrintMessage() {
		String id="21";
		OrderResponseEntity response=new OrderResponseEntity();
		response.setId(id);
		assertEquals( response.getId(), 21);
		
	}
}

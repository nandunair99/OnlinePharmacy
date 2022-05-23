package com.narola.pharmacy.paymentgateway;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.narola.pharmacy.utility.Constant;

public class RazerPaymentgateway {

	private static final String CURRENCY_INR = "INR";

	public String createOrder(OrderRequestEntity orderRequestEntity, Integer orderId) {
		ObjectMapper objectMapper = new ObjectMapper();
		/**
		 * OrderResponseEntity orderResponseEntity = new OrderResponseEntity();
		 */
		Map<String, Object> responseMap = new HashMap<>();
		try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
			HttpPost createOrderRequest = new HttpPost("https://api.razorpay.com/v1/orders");
			createOrderRequest.addHeader("Content-Type", "application/json");
			createOrderRequest.addHeader("Authorization", "Basic " + Constant.RAZORPAY_ID_SECRET_BASE64STRING);
			System.out.println(objectMapper.writeValueAsString(orderRequestEntity));
			orderRequestEntity.setCurrency(CURRENCY_INR);
			createOrderRequest.setEntity(new StringEntity(objectMapper.writeValueAsString(orderRequestEntity)));
			String result = "";

			try (CloseableHttpResponse createOrderResponse = httpClient.execute(createOrderRequest)) {
				if (createOrderResponse.getStatusLine().getStatusCode() == 200) {
					result = EntityUtils.toString(createOrderResponse.getEntity());
					responseMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {
					});
					System.out.println(responseMap.get("id"));
					/**
					 * orderResponseEntity.setId(responseMap.get("id").toString());
					 */

				}
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return responseMap.get("id").toString();
	}

}

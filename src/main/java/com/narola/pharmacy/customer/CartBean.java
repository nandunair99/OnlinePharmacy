package com.narola.pharmacy.customer;

import com.narola.pharmacy.medicine.model.MedicineBean;
import com.narola.pharmacy.test.model.TestBean;

public class CartBean {
	private Integer cartId;
	private Integer userId;
	private Integer quantity;
	private MedicineBean medicineBean=null;
	private TestBean testBean=null;
	
	public Integer getCartId() {
		return cartId;
	}
	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public MedicineBean getMedicineBean() {
		return medicineBean;
	}
	public void setMedicineBean(MedicineBean medicineBean) {
		this.medicineBean = medicineBean;
	}
	public TestBean getTestBean() {
		return testBean;
	}
	public void setTestBean(TestBean testBean) {
		this.testBean = testBean;
	}
}

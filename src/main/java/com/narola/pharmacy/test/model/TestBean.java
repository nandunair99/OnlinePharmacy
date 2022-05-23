package com.narola.pharmacy.test.model;

import java.io.InputStream;
import java.time.LocalDateTime;

public class TestBean {

	private Integer testId;
	private String testName;
	private Double testPrice;
	private Double testDiscount;
	private Double testDiscountedPrice;
	private String testDescription;
	private String testPreparation;
	private InputStream picStream = null;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
	private String base64String;
	// private String fileName;
	private Boolean popular;

	public Double getTestDiscountedPrice() {
		return testDiscountedPrice;
	}

	public void setTestDiscountedPrice(Double testDiscountedPrice) {
		this.testDiscountedPrice = testDiscountedPrice;
	}

	public String getBase64String() {
		return base64String;
	}

	public void setBase64String(String base64String) {
		this.base64String = base64String;
	}

	public Boolean getPopular() {
		return popular;
	}

	public void setPopular(Boolean popular) {
		this.popular = popular;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public Double getTestDiscount() {
		return testDiscount;
	}

	public void setTestDiscount(Double testDiscount) {
		this.testDiscount = testDiscount;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	/*
	 * public String getFileName() { return fileName; }
	 * 
	 * public void setFileName(String fileName) { this.fileName = fileName; }
	 */

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Double getTestPrice() {
		return testPrice;
	}

	public void setTestPrice(Double testPrice) {
		this.testPrice = testPrice;
	}

	public String getTestDescription() {
		return testDescription;
	}

	public void setTestDescription(String medDescription) {
		this.testDescription = medDescription;
	}

	public String getTestPreparation() {
		return testPreparation;
	}

	public void setTestPreparation(String testPreparation) {
		this.testPreparation = testPreparation;
	}

	public InputStream getPicStream() {
		return picStream;
	}

	public void setPicStream(InputStream picStream) {
		this.picStream = picStream;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	public Integer getTestId() {
		return this.testId;
	}

	@Override
	public String toString() {
		return "TestBean [testName=" + testName + ", testPrice=" + testPrice + ", testDiscount=" + testDiscount
				+ ", testDescription=" + testDescription + ", testPreparation=" + testPreparation + ", picStream="
				+ picStream + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", popular=" + popular
				+ ", testId=" + testId + "]";
	}
}

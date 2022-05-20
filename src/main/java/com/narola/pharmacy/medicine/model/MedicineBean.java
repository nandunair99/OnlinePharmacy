package com.narola.pharmacy.medicine.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MedicineBean {

	private Integer medId;
	private Integer catId;
	private String medName;
	private Double medPrice;
	private Double medDiscount;
	private Double medDiscountedPrice;
	private String medManufacturer;
	private String medDescription;
	private LocalDate medMfgDate;
	private LocalDate medExpDate;
	private Integer quantity;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
	private Boolean popular;

	private List<String> imagesPath;

	public Double getMedDiscountedPrice() {
		return medDiscountedPrice;
	}

	public void setMedDiscountedPrice(Double medDiscoutedPrice) {
		this.medDiscountedPrice = medDiscoutedPrice;
	}

	public List<String> getImagesPath() {
		return imagesPath;
	}

	public void setImagesPath(List<String> imagesPath) {
		this.imagesPath = imagesPath;
	}

	public Double getMedDiscount() {
		return medDiscount;
	}

	public void setMedDiscount(Double medDiscount) {
		this.medDiscount = medDiscount;
	}

	public Boolean getPopular() {
		return popular;
	}

	public void setPopular(Boolean popular) {
		this.popular = popular;
	}

	public MedicineBean() {

	}

	public void setMedId(Integer medId) {
		this.medId = medId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public void setMedName(String medName) {
		this.medName = medName;
	}

	public void setMedPrice(Double medPrice) {
		this.medPrice = medPrice;
	}

	public void setMedManufacturer(String medManufacturer) {
		this.medManufacturer = medManufacturer;
	}

	public void setMedDescription(String medDescription) {
		this.medDescription = medDescription;
	}

	public void setMedMfgDate(LocalDate medMfgDate) {
		this.medMfgDate = medMfgDate;
	}

	public void setMedExpDate(LocalDate medExpDate) {
		this.medExpDate = medExpDate;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Integer getMedId() {
		return this.medId;
	}

	public Integer getCatId() {
		return this.catId;
	}

	public String getMedName() {
		return this.medName;
	}

	public Double getMedPrice() {
		return this.medPrice;
	}

	public String getMedManufacturer() {
		return this.medManufacturer;
	}

	public String getMedDescription() {
		return this.medDescription;
	}

	public LocalDate getMedMfgDate() {
		return this.medMfgDate;
	}

	public LocalDate getMedExpDate() {
		return this.medExpDate;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public LocalDateTime getCreatedOn() {
		return this.createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return this.updatedOn;
	}

	@Override
	public String toString() {
		return "MedicineBean [medId=" + medId + ", catId=" + catId + ", medName=" + medName + ", medPrice=" + medPrice
				+ ", medDiscount=" + medDiscount + ", medDiscoutedPrice=" + medDiscountedPrice + ", medManufacturer="
				+ medManufacturer + ", medDescription=" + medDescription + ", medMfgDate=" + medMfgDate
				+ ", medExpDate=" + medExpDate + ", quantity=" + quantity + ", createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + ", popular=" + popular + ", imagesPath=" + imagesPath + "]";
	}

}

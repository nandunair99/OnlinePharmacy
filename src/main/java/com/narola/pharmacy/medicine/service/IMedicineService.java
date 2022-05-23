package com.narola.pharmacy.medicine.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.narola.pharmacy.PharmacyServiceException;
import com.narola.pharmacy.medicine.model.MedicineBean;

public interface IMedicineService {

	void addMedicine(MedicineBean mb, HttpServletRequest request) throws PharmacyServiceException;

	void managePopularMedicine(Integer medId, String action) throws PharmacyServiceException;

	void deleteMedicine(int medId) throws PharmacyServiceException;

	List<MedicineBean> getAllMedicine(HttpServletRequest request) throws PharmacyServiceException;

	String updateMedicine(HttpServletRequest request, MedicineBean mb, String imagesToBeDeleted)
			throws PharmacyServiceException;

	MedicineBean getMedicine(HttpServletRequest request, Integer medId) throws PharmacyServiceException;

	MedicineBean updateMedicineForm(Integer medId) throws PharmacyServiceException;
}

package com.narola.pharmacy.medicine.dao;

import java.util.List;

import com.narola.pharmacy.PharmacyDBException;
import com.narola.pharmacy.medicine.model.MedicineBean;

public interface IMedicineDAO {

	int InsertMedicine(MedicineBean mb) throws PharmacyDBException;
	List<MedicineBean> searchMedicineByName(String name) throws PharmacyDBException;
	List<MedicineBean> getMedicineByCatid(Integer catId) throws PharmacyDBException;
	void updatePictureId(Integer medId) throws PharmacyDBException;
	void deleteMedicine(Integer medId) throws PharmacyDBException;
	void updateMedicine(Integer medId, MedicineBean mb) throws PharmacyDBException;
	List<MedicineBean> showAllMedicine() throws PharmacyDBException;
	List<MedicineBean> showDiscountMedicine() throws PharmacyDBException;
	List<MedicineBean> showPopularMedicine() throws PharmacyDBException;
	MedicineBean getMedicineById(Integer medId) throws PharmacyDBException;
	boolean medicineIsExist(String medName) throws PharmacyDBException;
	void managePopularity(Integer medId, String action) throws PharmacyDBException;
}

package com.narola.pharmacy.utility;

import com.narola.pharmacy.medicine.service.IMedicineService;
import com.narola.pharmacy.medicine.service.impl.MedicineServiceImpl;

public class ServiceFactory {

	private static ServiceFactory ServiceHelper = null;
	private IMedicineService medicineService = null;
	private IMedicineService testService = null;
	
	private ServiceFactory() {

	}

	public static ServiceFactory getInstance() {
		if (ServiceHelper == null) {
			ServiceHelper = new ServiceFactory();
		}
		return ServiceHelper;
	}

	public IMedicineService getMedicineService() {

		if (medicineService == null) {
			medicineService = new MedicineServiceImpl();
		} 
			return medicineService;
		
	}

	

	public void init() throws Exception {
		medicineService = new MedicineServiceImpl();
	}
}

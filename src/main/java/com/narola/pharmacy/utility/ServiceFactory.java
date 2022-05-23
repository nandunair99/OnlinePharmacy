package com.narola.pharmacy.utility;

import com.narola.pharmacy.medicine.service.IMedicineService;
import com.narola.pharmacy.medicine.service.impl.MedicineServiceImpl;
import com.narola.pharmacy.test.service.ITestService;
import com.narola.pharmacy.test.service.impl.TestServiceImpl;

public class ServiceFactory {

	private static ServiceFactory ServiceHelper = null;
	private IMedicineService medicineService = null;
	private ITestService testService = null;

	private ServiceFactory() {

	}

	public static ServiceFactory getInstance() {
		if (ServiceHelper == null) {
			ServiceHelper = new ServiceFactory();
		}
		return ServiceHelper;
	}

	public IMedicineService getMedicineService() {
		return medicineService;
	}

	public ITestService getTestService() {
		return testService;
	}

	public void init() throws Exception {
		medicineService = new MedicineServiceImpl();
		testService = new TestServiceImpl();
	}
}

package com.narola.pharmacy.test.service;

import java.util.List;

import javax.servlet.http.Part;

import com.narola.pharmacy.PharmacyServiceException;
import com.narola.pharmacy.test.model.TestBean;

public interface ITestService {
	void addTest(TestBean tb) throws PharmacyServiceException;

	void managePopularTest(Integer testId, String action) throws PharmacyServiceException;

	void deleteTest(int testId) throws PharmacyServiceException;

	List<TestBean> getAllTest() throws PharmacyServiceException;

	void updateTest(Part image, TestBean tb) throws PharmacyServiceException;

	TestBean updateTestForm(int testId) throws PharmacyServiceException;

	TestBean getTestForm(int testId) throws PharmacyServiceException;
}

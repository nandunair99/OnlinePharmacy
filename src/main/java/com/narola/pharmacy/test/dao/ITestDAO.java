package com.narola.pharmacy.test.dao;

import java.util.List;

import com.narola.pharmacy.PharmacyDBException;
import com.narola.pharmacy.test.model.TestBean;

public interface ITestDAO {
	int InsertTest(TestBean tb) throws PharmacyDBException;

	void deleteTest(Integer testId) throws PharmacyDBException;

	void updateTest(Integer testId, TestBean tb) throws PharmacyDBException;

	TestBean getTestById(Integer testId) throws PharmacyDBException;

	boolean TestIsExist(String testName) throws PharmacyDBException;

	List<TestBean> showAllTest() throws PharmacyDBException;

	List<TestBean> searchTestByName(String name) throws PharmacyDBException;

	List<TestBean> showPopularTest() throws PharmacyDBException;

	void managePopularity(Integer testId, String action) throws PharmacyDBException;
}

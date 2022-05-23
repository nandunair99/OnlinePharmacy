package com.narola.pharmacy.test.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.Part;

import com.narola.pharmacy.PharmacyDBException;
import com.narola.pharmacy.PharmacyServiceException;
import com.narola.pharmacy.test.dao.ITestDAO;
import com.narola.pharmacy.test.model.TestBean;
import com.narola.pharmacy.test.service.ITestService;
import com.narola.pharmacy.utility.Constant;
import com.narola.pharmacy.utility.DAOFactory;
import com.narola.pharmacy.utility.UtilityMethods;

public class TestServiceImpl implements ITestService {

	ITestDAO testDAO = DAOFactory.getInstance().getTestDAO();

	public void addTest(TestBean tb) throws PharmacyServiceException {
		try {
			testDAO.InsertTest(tb);
		} catch (PharmacyDBException e) {
			throw new PharmacyServiceException(Constant.CONST_PHARMACY_DB_EXCEPTION_MESSAGE, e);
		}
	}

	public void managePopularTest(Integer testId, String action) throws PharmacyServiceException {
		try {
			testDAO.managePopularity(testId, action);
		} catch (PharmacyDBException e) {
			throw new PharmacyServiceException(Constant.CONST_PHARMACY_DB_EXCEPTION_MESSAGE, e);
		}
	}

	public void deleteTest(int testId) throws PharmacyServiceException {
		try {
			testDAO.deleteTest(testId);
		} catch (PharmacyDBException e) {
			throw new PharmacyServiceException(Constant.CONST_PHARMACY_DB_EXCEPTION_MESSAGE, e);
		}
	}

	public List<TestBean> getAllTest() throws PharmacyServiceException {
		try {
			List<TestBean> list;
			list = testDAO.showAllTest();
			for (TestBean tbean : list) {
				tbean.setBase64String(UtilityMethods.inputStreamToBase64String(tbean));

			}
			return list;
		} catch (PharmacyDBException e) {
			throw new PharmacyServiceException(Constant.CONST_PHARMACY_DB_EXCEPTION_MESSAGE, e);
		}
	}

	public void updateTest(Part image, TestBean tb) throws PharmacyServiceException {
		try {
			String fileName = image.getSubmittedFileName();
			InputStream filecontent = null;
			if (!fileName.equals(null) && !fileName.equals("")) {
				filecontent = image.getInputStream();
			}
			tb.setPicStream(filecontent);
			testDAO.updateTest(tb.getTestId(), tb);
		} catch (IOException | PharmacyDBException e) {
			throw new PharmacyServiceException(Constant.ERR_SOMETHING_WENT_WRONG, e);
		}

	}

	public TestBean updateTestForm(int testId) throws PharmacyServiceException {

		try {
			TestBean tb;
			tb = testDAO.getTestById(testId);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			final byte[] bytes = new byte[1024];
			int read = 0;
			while ((read = tb.getPicStream().read(bytes)) != -1) {
				bos.write(bytes, 0, read);
			}
			String encode = Base64.getEncoder().encodeToString((byte[]) bos.toByteArray());
			tb.setBase64String(encode);
			return tb;
		} catch (PharmacyDBException e) {
			throw new PharmacyServiceException(Constant.CONST_PHARMACY_DB_EXCEPTION_MESSAGE, e);
		} catch (IOException e) {
			throw new PharmacyServiceException(Constant.ERR_SOMETHING_WENT_WRONG, e);
		}

	}

	public TestBean getTestForm(int testId) throws PharmacyServiceException {
		try {
			TestBean tb;
			tb = testDAO.getTestById(testId);
			tb.setBase64String(UtilityMethods.inputStreamToBase64String(tb));
			return tb;
		} catch (PharmacyDBException e) {
			throw new PharmacyServiceException(Constant.CONST_PHARMACY_DB_EXCEPTION_MESSAGE, e);
		}

	}

}

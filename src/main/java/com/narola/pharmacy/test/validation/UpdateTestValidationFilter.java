package com.narola.pharmacy.test.validation;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.narola.pharmacy.PharmacyDBException;
import com.narola.pharmacy.test.dao.ITestDAO;
import com.narola.pharmacy.test.dao.TestDAOMysql;
import com.narola.pharmacy.test.model.TestBean;
import com.narola.pharmacy.utility.DAOFactory;

public class UpdateTestValidationFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public UpdateTestValidationFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			ITestDAO testDao = DAOFactory.getInstance().getTestDAO();
			boolean status = false;
			System.out.println("In updateTestValidationFilter...");
			String testName = request.getParameter("testNametxt");
			String testPrice = request.getParameter("testPricetxt");
			String testDiscount = request.getParameter("testDiscounttxt");
			String testDesc = request.getParameter("testDesctxt");
			String testPreparation = request.getParameter("testPreparationtxt");
			String base64 = request.getParameter("imageBase64");

			TestBean tb = new TestBean();
			Double testPricing = -1.0;
			Double testDiscounting = -1.0;
			if (!testPrice.equals(null) && !testPrice.equals("")) {
				if (isNumeric(testPrice)) {
					if (Double.valueOf(testPrice) >= 0.0)
						testPricing = Double.valueOf(testPrice);
					else
						status = true;
				} else
					status = true;
			}
			if (!testDiscount.equals(null) && !testDiscount.equals("")) {
				if (isNumeric(testDiscount)) {
					if (Double.valueOf(testDiscount) >= 0.0 && Double.valueOf(testDiscount) <= 100.0)
						testDiscounting = Double.valueOf(testDiscount);
					else
						status = true;
				} else
					status = true;
			}
			tb.setTestId(Integer.valueOf(request.getParameter("testId")));
			tb.setTestName(testName);
			tb.setTestPrice(Double.valueOf(testPricing));
			tb.setTestDiscount(Double.valueOf(testDiscounting));
			tb.setTestDescription(testDesc);
			tb.setTestPreparation(testPreparation);
			request.setAttribute("imageBase64", base64);

			if (testName.equals(null) || testName.equals("") || testPrice.equals(null) || testPrice.equals("")
					|| testDiscount.equals(null) || testDiscount.equals("") || testDesc.equals(null)
					|| testDesc.equals("") || testPreparation.equals(null) || testPreparation.equals("")) {
				request.setAttribute("errMsg", "Please fill all the fields...");
				RequestDispatcher rd = request.getRequestDispatcher("UpdateTestForm");
				request.setAttribute("TestBean", tb);
				rd.forward(request, response);
			} else {

				if (!status) {
					if (testDao.TestIsExist(testName) && (!testName.toLowerCase().equals(
							testDao.getTestById(Integer.valueOf(request.getParameter("testId"))).getTestName()))) {
						request.setAttribute("errMsg", "Test Already exists...");
						RequestDispatcher rd = request.getRequestDispatcher("UpdateTestForm");
						request.setAttribute("TestBean", tb);
						rd.forward(request, response);
					} else {
						request.setAttribute("errMsg", "");
						chain.doFilter(request, response);
					}
				} else {
					if (!isNumeric(testPrice))
						request.setAttribute("errMsg", "Please enter valid value for price...");
					else if (!isNumeric(testDiscount))
						request.setAttribute("errMsg", "Please enter valid value for Discount...");
					if (isNumeric(testPrice)) {
						if (Double.valueOf(testPrice) < 0.0)
							request.setAttribute("errMsg", "Please enter positive value for price...");
					}
					if (isNumeric(testDiscount)) {
						if (Double.valueOf(testDiscount) < 0.0 || Double.valueOf(testDiscount) > 100.0)
							request.setAttribute("errMsg", "Please enter discount value between 0-100...");
					}
					RequestDispatcher rd = request.getRequestDispatcher("UpdateTestForm");
					request.setAttribute("TestBean", tb);
					rd.forward(request, response);
				}

			}
		} catch (PharmacyDBException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}

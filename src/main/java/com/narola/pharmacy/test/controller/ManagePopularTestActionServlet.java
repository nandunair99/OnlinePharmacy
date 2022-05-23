package com.narola.pharmacy.test.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.narola.pharmacy.PharmacyServiceException;
import com.narola.pharmacy.test.service.ITestService;
import com.narola.pharmacy.utility.Constant;
import com.narola.pharmacy.utility.ServiceFactory;

public class ManagePopularTestActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ManagePopularTestActionServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			ITestService testService = ServiceFactory.getInstance().getTestService();
			Integer testId = Integer.valueOf(request.getParameter(Constant.CONST_TEST_ID));
			String action = request.getParameter(Constant.CONST_ACTION);
			testService.managePopularTest(testId, action);
		} catch (PharmacyServiceException e) {
			request.setAttribute(Constant.CONST_ERROR_MESSAGE, Constant.ERR_TEST_INSERT);
			RequestDispatcher rd = request.getRequestDispatcher("ShowAllTest");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

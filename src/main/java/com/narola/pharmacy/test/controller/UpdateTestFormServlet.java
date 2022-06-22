package com.narola.pharmacy.test.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.narola.pharmacy.exception.PharmacyServiceException;
import com.narola.pharmacy.test.model.TestBean;
import com.narola.pharmacy.test.service.ITestService;
import com.narola.pharmacy.utility.Constant;
import com.narola.pharmacy.utility.ServiceFactory;

public class UpdateTestFormServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			int testId = Integer.valueOf(request.getParameter(Constant.CONST_TEST_ID));
			ITestService testService = ServiceFactory.getInstance().getTestService();
			TestBean tb = testService.updateTestForm(testId);
			request.setAttribute("TestBean", tb);
			RequestDispatcher rd = request.getRequestDispatcher("test-update-form.jsp");
			rd.forward(request, response);
		} catch (PharmacyServiceException e) {
			request.setAttribute(Constant.CONST_ERROR_MESSAGE, Constant.ERR_TEST_SHOW_UPDATE_FORM);
			RequestDispatcher rd = request.getRequestDispatcher("ShowAllTest");
			rd.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
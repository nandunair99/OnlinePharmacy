package com.narola.pharmacy.test.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.narola.pharmacy.PharmacyServiceException;
import com.narola.pharmacy.test.model.TestBean;
import com.narola.pharmacy.test.service.ITestService;
import com.narola.pharmacy.utility.Constant;
import com.narola.pharmacy.utility.ServiceFactory;

public class UpdateTestActionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			int testId = Integer.valueOf(request.getParameter(Constant.CONST_TEST_ID));
			ITestService testService = ServiceFactory.getInstance().getTestService();
			Part image = request.getPart(Constant.CTRL_IMAGE_CONTROL);
			TestBean tb = new TestBean();
			tb.setTestId(Integer.valueOf(request.getParameter(Constant.CONST_TEST_ID)));
			tb.setTestName(request.getParameter(Constant.CTRL_TEST_NAME));
			tb.setTestPrice(Double.valueOf(request.getParameter(Constant.CTRL_TEST_PRICE)));
			tb.setTestDiscount(Double.valueOf(request.getParameter(Constant.CTRL_TEST_DISCOUNT)));
			tb.setTestDescription(request.getParameter(Constant.CTRL_TEST_DESCRIPTION));
			tb.setTestPreparation(request.getParameter(Constant.CTRL_TEST_PREPARATION));
			tb.setTestId(testId);
			testService.updateTest(image, tb);
			response.sendRedirect("ShowAllTest");
		} catch (PharmacyServiceException e) {
			request.setAttribute(Constant.CONST_ERROR_MESSAGE, Constant.ERR_TEST_UPDATE);
			RequestDispatcher rd = request.getRequestDispatcher("test-update-form.jsp");
			rd.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

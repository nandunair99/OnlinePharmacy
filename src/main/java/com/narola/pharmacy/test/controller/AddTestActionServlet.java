package com.narola.pharmacy.test.controller;

import java.io.IOException;
import java.io.InputStream;

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

public class AddTestActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			ITestService testService = ServiceFactory.getInstance().getTestService();
			Part image = request.getPart(Constant.CTRL_IMAGE_CONTROL);
			InputStream filecontent = image.getInputStream();

			TestBean tb = new TestBean();
			tb.setTestName(request.getParameter(Constant.CTRL_TEST_NAME));
			tb.setTestPrice(Double.valueOf(request.getParameter(Constant.CTRL_TEST_PRICE)));
			tb.setTestDiscount(Double.valueOf(request.getParameter(Constant.CTRL_TEST_DISCOUNT)));
			tb.setTestDescription(request.getParameter(Constant.CTRL_TEST_DESCRIPTION));
			tb.setTestPreparation(request.getParameter(Constant.CTRL_TEST_PREPARATION));
			tb.setPicStream(filecontent);
			testService.addTest(tb);
			response.sendRedirect("ShowAllTest");
		} catch (PharmacyServiceException e) {
			request.setAttribute("errMsg", Constant.ERR_TEST_INSERT);
			RequestDispatcher rd = request.getRequestDispatcher("addtest.jsp");
			rd.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}

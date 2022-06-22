package com.narola.pharmacy.medicine.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.narola.pharmacy.exception.PharmacyServiceException;
import com.narola.pharmacy.medicine.model.MedicineBean;
import com.narola.pharmacy.medicine.service.IMedicineService;
import com.narola.pharmacy.utility.Constant;
import com.narola.pharmacy.utility.ServiceFactory;

public class ViewMedicineFormServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			IMedicineService medicineService = ServiceFactory.getInstance().getMedicineService();
			Integer medId = Integer.valueOf(request.getParameter("medId"));
			MedicineBean mb = medicineService.getMedicine(request, medId);
			RequestDispatcher rd = request.getRequestDispatcher("viewmedicine.jsp");
			request.setAttribute("MedicineBean", mb);
			rd.forward(request, response);
		} catch (PharmacyServiceException e) {
			request.setAttribute(Constant.CONST_ERROR_MESSAGE, Constant.ERR_MED_GET_ALL_MED);
			RequestDispatcher rd = request.getRequestDispatcher("medicinemain.jsp");
			rd.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
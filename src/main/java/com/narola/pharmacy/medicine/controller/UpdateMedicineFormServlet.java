package com.narola.pharmacy.medicine.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.narola.pharmacy.PharmacyServiceException;
import com.narola.pharmacy.medicine.service.IMedicineService;
import com.narola.pharmacy.utility.Constant;
import com.narola.pharmacy.utility.ServiceFactory;

public class UpdateMedicineFormServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			IMedicineService medicineService = ServiceFactory.getInstance().getMedicineService();
			Integer medId = Integer.valueOf(request.getParameter(Constant.CONST_MED_ID));
			File dir = new File(getServletContext().getRealPath("/") + Constant.MEDICINE_IMG_FOLDER + medId.toString());
			request.setAttribute("FileList", dir.listFiles());
			request.setAttribute("MedicineBean", medicineService.updateMedicineForm(medId));
			RequestDispatcher rd = request.getRequestDispatcher("medicine-update-form.jsp");
			rd.forward(request, response);
		} catch (PharmacyServiceException e) {
			request.setAttribute(Constant.CONST_ERROR_MESSAGE, Constant.ERR_MED_SHOW_UPDATE_FORM);
			RequestDispatcher rd = request.getRequestDispatcher("medicinemain.jsp");
			rd.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
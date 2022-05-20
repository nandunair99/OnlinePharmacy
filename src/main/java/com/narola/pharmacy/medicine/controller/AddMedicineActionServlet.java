package com.narola.pharmacy.medicine.controller;

import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.narola.pharmacy.PharmacyServiceException;
import com.narola.pharmacy.medicine.model.MedicineBean;
import com.narola.pharmacy.medicine.service.IMedicineService;
import com.narola.pharmacy.utility.Constant;
import com.narola.pharmacy.utility.ServiceFactory;

public class AddMedicineActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			IMedicineService medicineService = ServiceFactory.getInstance().getMedicineService();
			MedicineBean mb = new MedicineBean();
			mb.setCatId(Integer.valueOf(request.getParameter(Constant.CONST_CATEGORY_NAME)));
			mb.setMedName(request.getParameter(Constant.CTRL_MED_NAME));
			mb.setMedPrice(Double.valueOf(request.getParameter(Constant.CTRL_MED_PRICE)));
			mb.setMedDiscount(Double.valueOf(request.getParameter(Constant.CTRL_MED_DISCOUNT)));
			mb.setMedManufacturer(request.getParameter(Constant.CTRL_MED_MANUFACTURER));
			mb.setMedDescription(request.getParameter(Constant.CTRL_MED_DESCRIPTION));
			mb.setMedMfgDate(LocalDate.parse(request.getParameter(Constant.CTRL_MED_MFGDATE)));
			mb.setMedExpDate(LocalDate.parse(request.getParameter(Constant.CTRL_MED_EXPDATE)));
			mb.setQuantity(Integer.valueOf(request.getParameter(Constant.CTRL_MED_QUANTITY)));
			medicineService.addMedicine(mb, request);

		} catch (PharmacyServiceException e) {

			request.setAttribute(Constant.CONST_ERROR_MESSAGE, Constant.ERR_MED_INSERT);
			RequestDispatcher rd = request.getRequestDispatcher("addmedicine.jsp");
			rd.forward(request, response);
			return;
		}

		response.sendRedirect("ShowAllMedicine");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}

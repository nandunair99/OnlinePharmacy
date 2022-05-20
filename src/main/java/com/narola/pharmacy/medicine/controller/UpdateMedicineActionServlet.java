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

public class UpdateMedicineActionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			IMedicineService medicineService = ServiceFactory.getInstance().getMedicineService();
			int catId = Integer.valueOf(request.getParameter(Constant.CONST_CATEGORY_NAME));

			Integer medId = Integer.valueOf(request.getParameter(Constant.CONST_MED_ID));
			String medName = request.getParameter(Constant.CTRL_MED_NAME);
			Double medPrice = Double.valueOf(request.getParameter(Constant.CTRL_MED_PRICE));
			Double medDiscount = Double.valueOf(request.getParameter(Constant.CTRL_MED_DISCOUNT));
			String medManufacturertxt = request.getParameter(Constant.CTRL_MED_MANUFACTURER);
			String medDescriptiontxt = request.getParameter(Constant.CTRL_MED_DESCRIPTION);
			LocalDate medMfgDatetxt = LocalDate.parse(request.getParameter(Constant.CTRL_MED_MFGDATE));
			LocalDate medExpDatetxt = LocalDate.parse(request.getParameter(Constant.CTRL_MED_EXPDATE));
			int quantity = Integer.valueOf(request.getParameter(Constant.CTRL_MED_QUANTITY));

			MedicineBean mb = null;
			mb = new MedicineBean();
			mb.setMedId(medId);
			mb.setCatId(catId);
			mb.setMedName(medName);
			mb.setMedPrice(medPrice);
			mb.setMedDiscount(medDiscount);
			mb.setMedManufacturer(medManufacturertxt);
			mb.setMedDescription(medDescriptiontxt);
			mb.setMedMfgDate(medMfgDatetxt);
			mb.setMedExpDate(medExpDatetxt);
			mb.setQuantity(quantity);
			String imagesToBeDeleted = request.getParameter("imageStringtxt");
			String fileName=medicineService.updateMedicine(request, mb,imagesToBeDeleted);
			request.setAttribute("message", fileName + Constant.SUCCESS_FILE_UPLOAD);
			response.sendRedirect("ShowAllMedicine");
		} catch (PharmacyServiceException e) {
			request.setAttribute(Constant.CONST_ERROR_MESSAGE, Constant.ERR_MED_UPDATE);
			RequestDispatcher rd = request.getRequestDispatcher("medicine-update-form.jsp");
			rd.forward(request, response);
			return;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
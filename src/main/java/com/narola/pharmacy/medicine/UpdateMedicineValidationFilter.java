package com.narola.pharmacy.medicine;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.narola.pharmacy.PharmacyDBException;
import com.narola.pharmacy.utility.DAOFactory;

/**
 * Servlet Filter implementation class UpdateMedicineValidationFilter
 */
public class UpdateMedicineValidationFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public UpdateMedicineValidationFilter() {

	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {

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
		// pass the request along the filter chain

		try {
			IMedicineDAO medicineDao=DAOFactory.getInstance().getMedicineDAO();
			boolean status = false;
			System.out.println("In UpdateMedicineValidationFilter...");
			Integer catId = Integer.valueOf(request.getParameter("categoryName"));
			String medName = request.getParameter("medNametxt");
			String medPrice = request.getParameter("medPricetxt");
			String medDiscount = request.getParameter("medDiscounttxt");
			String medManufacturer = request.getParameter("medManufacturertxt");
			String medDescription = request.getParameter("medDescriptiontxt");
			String medMfgDate = request.getParameter("medMfgDatetxt");
			String medExpDate = request.getParameter("medExpDatetxt");
			String quantity = request.getParameter("quantitytxt");
			HttpServletRequest req = (HttpServletRequest) request;

			Double medicinePrice = -1.0;
			Double medicineDiscount = -1.0;
			LocalDate medicineMfgDate = null;
			LocalDate medicineExpDate = null;

			System.out
					.println("referrer: " + req.getContextPath() + "/UpdateMedicineAction" + " " + req.getRequestURI());

			if (!medPrice.equals(null) && !medPrice.equals("")) {
				if (isNumeric(medPrice)) {
					if (Double.valueOf(medPrice) >= 0.0)
						medicinePrice = Double.valueOf(medPrice);
					else
						status = true;
				} else
					status = true;
			}
			if (!medDiscount.equals(null) && !medDiscount.equals("")) {
				if (isNumeric(medDiscount)) {
					if (Double.valueOf(medDiscount) >= 0.0 && Double.valueOf(medDiscount) <= 100.0)
						medicineDiscount = Double.valueOf(medDiscount);
					else
						status = true;
				} else
					status = true;
			}
			if (!medMfgDate.equals(null) && !medMfgDate.equals("")) {
				medicineMfgDate = LocalDate.parse(medMfgDate);
			}
			if (!medExpDate.equals(null) && !medExpDate.equals("")) {
				medicineExpDate = LocalDate.parse(medExpDate);
			}
			MedicineBean mb = new MedicineBean();
			mb.setMedId(Integer.valueOf(request.getParameter("medId")));
			mb.setCatId(catId);
			mb.setMedName(medName);
			mb.setMedPrice(medicinePrice);
			mb.setMedDiscount(medicineDiscount);
			mb.setMedManufacturer(medManufacturer);
			mb.setMedDescription(medDescription);
			mb.setMedMfgDate(medicineMfgDate);
			mb.setMedExpDate(medicineExpDate);
			mb.setQuantity(Integer.valueOf(quantity));

			if (medName.equals(null) || medName.equals("") || medPrice.equals(null) || medPrice.equals("")
					|| medDiscount.equals(null) || medDiscount.equals("") || medManufacturer.equals(null)
					|| medManufacturer.equals("") || medDescription.equals(null) || medDescription.equals("")
					|| medMfgDate.equals(null) || medMfgDate.equals("") || medExpDate.equals(null)
					|| medExpDate.equals("") || quantity.equals(null) || quantity.equals("")) {

				request.setAttribute("errMsg", "Please fill all the fields...");

				request.setAttribute("MedicineBean", mb);
				RequestDispatcher rd = request.getRequestDispatcher("UpdateMedicineForm");
				rd.forward(request, response);
			} else {
				if (!status) {
					System.out.println(
							medicineDao.getMedicineById(Integer.valueOf(request.getParameter("medId"))).getMedName());
					System.out.println(request.getParameter("medId"));
					if (medicineDao.medicineIsExist(medName) && (!medName.toLowerCase().equals(medicineDao
							.getMedicineById(Integer.valueOf(request.getParameter("medId"))).getMedName()))) {
						request.setAttribute("errMsg", "Medicine Already exists...");
						request.setAttribute("MedicineBean", mb);
						RequestDispatcher rd = request.getRequestDispatcher("UpdateMedicineForm");
						rd.forward(request, response);
					} else {
						System.out.println("Medicine does not Exist");

						request.setAttribute("errMsg", "");
						chain.doFilter(request, response);
					}
				} else {
					if (!isNumeric(medPrice))
						request.setAttribute("errMsg", "Please enter valid value for price...");
					else if (!isNumeric(medDiscount))
						request.setAttribute("errMsg", "Please enter valid value for Discount...");
					if (isNumeric(medPrice)) {
						if (Double.valueOf(medPrice) < 0.0)
							request.setAttribute("errMsg", "Please enter positive value for price...");
					}
					if (isNumeric(medDiscount)) {
						if (Double.valueOf(medDiscount) < 0.0 || Double.valueOf(medDiscount) > 100.0)
							request.setAttribute("errMsg", "Please enter discount value between 0-100...");
					}

					request.setAttribute("MedicineBean", mb);
					RequestDispatcher rd = request.getRequestDispatcher("UpdateMedicineForm");
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
		// TODO Auto-generated method stub
	}

}

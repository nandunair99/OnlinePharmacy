package com.narola.pharmacy.medicine.validation;

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
import javax.servlet.http.Part;

import com.narola.pharmacy.PharmacyDBException;
import com.narola.pharmacy.medicine.dao.IMedicineDAO;
import com.narola.pharmacy.medicine.model.MedicineBean;
import com.narola.pharmacy.utility.DAOFactory;

/**
 * Servlet Filter implementation class AddUpdateMedicineValidationFilter
 */
public class AddUpdateMedicineValidationFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public AddUpdateMedicineValidationFilter() {

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

		try {
			IMedicineDAO medicineDao = DAOFactory.getInstance().getMedicineDAO();
			boolean status = false;
			System.out.println("In AddUpdateMedicineValidationFilter...");
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

			Part part = req.getPart("picturetxt");
			String fileName = part.getSubmittedFileName();
			Double medicinePrice = -1.0;
			Double medicineDiscount = -1.0;
			LocalDate medicineMfgDate = null;
			LocalDate medicineExpDate = null;
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

			mb.setCatId(catId);
			mb.setMedName(medName);
			mb.setMedPrice(medicinePrice);
			mb.setMedDiscount(medicineDiscount);
			mb.setMedManufacturer(medManufacturer);
			mb.setMedDescription(medDescription);
			mb.setMedMfgDate(medicineMfgDate);
			mb.setMedExpDate(medicineExpDate);
			mb.setQuantity(Integer.valueOf(quantity));
			String formDestination = null;
			if (req.getRequestURI().equals(req.getContextPath() + "/AddMedicineAction")) {
				formDestination = "/AddMedicineForm";
			} else if (req.getRequestURI().equals(req.getContextPath() + "/UpdateMedicineAction")) {
				formDestination = "/UpdateMedicineForm";
			}
			System.out.println(req.getRequestURI() + " --------" + req.getContextPath() + "/AddMedicineAction");
			if (req.getRequestURI().equals(req.getContextPath() + "/AddMedicineAction")) {
				if (fileName.equals(null) || fileName.equals("")) {
					request.setAttribute("errMsg", "Please fill all the fields...");

					request.setAttribute("MedicineBean", mb);
					RequestDispatcher rd = request.getRequestDispatcher(formDestination);
					rd.forward(request, response);
					return;
				}
			}
			if (medName.equals(null) || medName.equals("") || medPrice.equals(null) || medPrice.equals("")
					|| medDiscount.equals(null) || medDiscount.equals("") || medManufacturer.equals(null)
					|| medManufacturer.equals("") || medDescription.equals(null) || medDescription.equals("")
					|| medMfgDate.equals(null) || medMfgDate.equals("") || medExpDate.equals(null)
					|| medExpDate.equals("") || quantity.equals(null) || quantity.equals("")) {

				request.setAttribute("errMsg", "Please fill all the fields...");

				request.setAttribute("MedicineBean", mb);
				RequestDispatcher rd = request.getRequestDispatcher(formDestination);
				rd.forward(request, response);
			} else {
				if (!status) {
					if (req.getRequestURI().equals(req.getContextPath() + "/AddMedicineAction")) {
						if (medicineDao.medicineIsExist(medName)) {
							request.setAttribute("errMsg", "Medicine Already exists...");
							request.setAttribute("MedicineBean", mb);
							RequestDispatcher rd = request.getRequestDispatcher(formDestination);
							rd.forward(request, response);
						} else {
							System.out.println("Medicine does not Exist");
							request.setAttribute("errMsg", "");
							chain.doFilter(request, response);
						}
					}
					if (req.getRequestURI().equals(req.getContextPath() + "/UpdateMedicineAction")) {
						if (medicineDao.medicineIsExist(medName) && (!medName.toLowerCase().equals(medicineDao
								.getMedicineById(Integer.valueOf(request.getParameter("medId"))).getMedName()))) {
							request.setAttribute("errMsg", "Medicine Already exists...");
							request.setAttribute("MedicineBean", mb);
							RequestDispatcher rd = request.getRequestDispatcher(formDestination);
							rd.forward(request, response);
						} else {
							System.out.println("Medicine does not Exist");

							request.setAttribute("errMsg", "");
							chain.doFilter(request, response);
						}
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
					RequestDispatcher rd = request.getRequestDispatcher(formDestination);
					rd.forward(request, response);
				}

			}

		} catch (PharmacyDBException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}

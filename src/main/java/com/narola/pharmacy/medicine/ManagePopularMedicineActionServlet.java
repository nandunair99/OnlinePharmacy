package com.narola.pharmacy.medicine;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.narola.pharmacy.PharmacyDBException;
import com.narola.pharmacy.utility.DAOFactory;

/**
 * Servlet implementation class ManagePopularMedicineActionServlet
 */
public class ManagePopularMedicineActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManagePopularMedicineActionServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			IMedicineDAO medicineDao=DAOFactory.getInstance().getMedicineDAO();
			Integer medId = Integer.valueOf(request.getParameter("medId"));
			String action = request.getParameter("action");
			medicineDao.managePopularity(medId, action);
		} catch (PharmacyDBException e) {

			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}

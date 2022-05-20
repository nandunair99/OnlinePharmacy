package com.narola.pharmacy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.narola.pharmacy.medicine.dao.IMedicineDAO;
import com.narola.pharmacy.medicine.model.MedicineBean;
import com.narola.pharmacy.utility.Constant;
import com.narola.pharmacy.utility.DAOFactory;

/**
 * Servlet implementation class ShowCustomerMedicineServlet
 */
public class ShowCustomerMedicineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowCustomerMedicineServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession pageSession=request.getSession(false);
		if(pageSession==null)
		{
			pageSession=request.getSession(true);
		}
		String qry;
		if(request.getQueryString()!=null)
		{
			qry="?"+request.getQueryString();
		}
			
		else {
			qry="";
		}
			
		pageSession.setAttribute("currentPage", request.getRequestURL()+qry);
		
		
		System.out.println(request.getRequestURL()+qry);

		try {
			IMedicineDAO medicineDAO=DAOFactory.getInstance().getMedicineDAO();
			Integer medId = Integer.valueOf(request.getParameter("medId"));
			MedicineBean mb = medicineDAO.getMedicineById(medId);
			File dir = new File(getServletContext().getRealPath("/") + Constant.MEDICINE_IMG_FOLDER + medId);
			File[] list = dir.listFiles();
			List<String> imagesPath = new ArrayList<>(list.length);
			for (int i = 0; i < list.length; i++) {
				imagesPath.add(request.getContextPath() + Constant.MEDICINE_IMG_FOLDER + medId.toString() + "/"
						+ list[i].getName());
			}
			mb.setImagesPath(imagesPath);
			request.setAttribute("MedicineBean", mb);
			RequestDispatcher rd = request.getRequestDispatcher("viewcustomermedicine.jsp");
			rd.forward(request, response);
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

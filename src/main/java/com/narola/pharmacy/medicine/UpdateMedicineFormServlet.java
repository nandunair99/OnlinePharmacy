package com.narola.pharmacy.medicine;

import java.io.File;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.narola.pharmacy.PharmacyDBException;
import com.narola.pharmacy.utility.Constant;
import com.narola.pharmacy.utility.DAOFactory;

public class UpdateMedicineFormServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		MedicineBean mb;
		try {
			IMedicineDAO medicineDao=DAOFactory.getInstance().getMedicineDAO();
			Integer medId = Integer.valueOf(request.getParameter("medId"));
			mb = medicineDao.getMedicineById(medId);
			
			File dir=new File(getServletContext().getRealPath("/")+Constant.MEDICINE_IMG_FOLDER+medId.toString());
			File[] filelist=dir.listFiles();
			
			request.setAttribute("FileList",filelist);
			request.setAttribute("MedicineBean", mb);
			RequestDispatcher rd = request.getRequestDispatcher("medicine-update-form.jsp");
			rd.forward(request, response);
		} catch (PharmacyDBException e) {

			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
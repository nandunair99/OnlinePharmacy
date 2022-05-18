package com.narola.pharmacy.medicine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.narola.pharmacy.PharmacyDBException;
import com.narola.pharmacy.utility.Constant;
import com.narola.pharmacy.utility.DAOFactory;

public class ShowAllMedicineServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			IMedicineDAO medicineDao=DAOFactory.getInstance().getMedicineDAO();
			System.out.println(getServletContext().getRealPath("/"));
			List<MedicineBean> list = medicineDao.showAllMedicine();
			for (MedicineBean medItem : list) {
				File dir = new File(
						getServletContext().getRealPath("/") + Constant.MEDICINE_IMG_FOLDER + medItem.getMedId());
				File[] flist = dir.listFiles();
				List<String> imagesPath = new ArrayList<>(flist.length);
				for (File file : flist) {
					imagesPath.add(request.getContextPath() + Constant.MEDICINE_IMG_FOLDER + medItem.getMedId() + "/"
							+ file.getName());
				}
				System.out.println(getServletContext().getRealPath("/") + "---" + request.getContextPath());
				medItem.setImagesPath(imagesPath);
			}

			request.setAttribute("MedicineList", list);
			RequestDispatcher rd2 = request.getRequestDispatcher("medicinemain.jsp");
			rd2.forward(request, response);
		} catch (PharmacyDBException e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}

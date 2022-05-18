package com.narola.pharmacy.medicine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.narola.pharmacy.PharmacyDBException;
import com.narola.pharmacy.utility.Constant;
import com.narola.pharmacy.utility.DAOFactory;

public class AddMedicineActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			IMedicineDAO medicineDao=DAOFactory.getInstance().getMedicineDAO();
			InputStream filecontent = null;
			MedicineBean mb = new MedicineBean();

			mb.setCatId(Integer.valueOf(request.getParameter("categoryName")));
			mb.setMedName(request.getParameter("medNametxt"));
			mb.setMedPrice(Double.valueOf(request.getParameter("medPricetxt")));
			mb.setMedDiscount(Double.valueOf(request.getParameter("medDiscounttxt")));
			mb.setMedManufacturer(request.getParameter("medManufacturertxt"));
			mb.setMedDescription(request.getParameter("medDescriptiontxt"));
			mb.setMedMfgDate(LocalDate.parse(request.getParameter("medMfgDatetxt")));
			mb.setMedExpDate(LocalDate.parse(request.getParameter("medExpDatetxt")));
			mb.setQuantity(Integer.valueOf(request.getParameter("quantitytxt")));
			Integer medId = medicineDao.InsertMedicine(mb);// inserting with random name

			String fileName = null;
			String destPath = getServletContext().getRealPath("/") + Constant.MEDICINE_IMG_FOLDER; // path for project
																									// deployed on
																									// server
			File dir = new File(destPath + medId.toString());// making a random named directory
			dir.mkdir();

			OutputStream out = null;

			for (Part part : request.getParts()) {

				if (part.getName().equals("picturetxt"))// of all the parts, filtering parts of picturetxt
				{

					fileName = part.getSubmittedFileName();
					out = new FileOutputStream(new File(destPath + medId.toString() + File.separator + fileName));// writing
																													// to
					// random named
					// directory
					filecontent = part.getInputStream();
					final byte[] bytes = new byte[1024];
					int read = 0;
					while ((read = filecontent.read(bytes)) != -1) {
						out.write(bytes, 0, read);// writing each bytes to another directory
					}
					out.flush();
					out.close();
				}

			}

			// 1. Create Folder with random number
			// 2. Write all images to folder created with step 1
			// 3. Perform INsert operation and get Primary key
			// 4. Rename folder created with step1 with primary key no.

			request.setAttribute("message", "File " + fileName + " has uploaded successfully!");

		} catch (FileNotFoundException fne) {
			request.setAttribute("message", "There was an error: " + fne.getMessage());
		} catch (PharmacyDBException e) {
			e.printStackTrace();
			request.setAttribute("errMsg", "Error occured while inserting medicine...");
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

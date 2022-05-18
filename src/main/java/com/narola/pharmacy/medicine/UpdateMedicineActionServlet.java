package com.narola.pharmacy.medicine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.narola.pharmacy.PharmacyDBException;
import com.narola.pharmacy.utility.Constant;
import com.narola.pharmacy.utility.DAOFactory;

public class UpdateMedicineActionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			IMedicineDAO medicineDao=DAOFactory.getInstance().getMedicineDAO();
			int catId = Integer.valueOf(request.getParameter("categoryName"));
			String destPath = getServletContext().getRealPath("/") + Constant.MEDICINE_IMG_FOLDER;
			Integer medId = Integer.valueOf(request.getParameter("medId"));
			String medName = request.getParameter("medNametxt");
			Double medPrice = Double.valueOf(request.getParameter("medPricetxt"));
			Double medDiscount = Double.valueOf(request.getParameter("medDiscounttxt"));
			String medManufacturertxt = request.getParameter("medManufacturertxt");
			String medDescriptiontxt = request.getParameter("medDescriptiontxt");
			LocalDate medMfgDatetxt = LocalDate.parse(request.getParameter("medMfgDatetxt"));
			LocalDate medExpDatetxt = LocalDate.parse(request.getParameter("medExpDatetxt"));
			int quantity = Integer.valueOf(request.getParameter("quantitytxt"));
			String fileName = request.getPart("picturetxt").getSubmittedFileName();
			String imagesToBeDeleted = request.getParameter("imageStringtxt");
			String[] listImagesToBeDeleted = imagesToBeDeleted.split(",");
			List<String> listImagesNotToBeDeleted = new ArrayList<>();

			File oldDir = new File(destPath + medId.toString());
			if (oldDir.isDirectory()) {
				for (File subfile : oldDir.listFiles()) {
					boolean status = false;
					for (String image : listImagesToBeDeleted) {
						if (image.equals(subfile.getName())) {
							System.out.println("images to be deleted: " + image);
							status = true;
							subfile.delete();
							break;
						}
							
					}
					if (!status)// medicine pics not to be deleted
						listImagesNotToBeDeleted.add(subfile.getName());
				}

			}
			// after this only the files not to be deleted remains
			System.out.println(listImagesNotToBeDeleted);
			// ----
			InputStream filecontent = null;
			MedicineBean mb = null;

			OutputStream out = null;

			for (Part part : request.getParts()) {

				if (part.getName().equals("picturetxt")) {

					fileName = part.getSubmittedFileName();
					System.out.println(fileName);
					if (!fileName.equals(null) && !fileName.isEmpty()) {
						out = new FileOutputStream(new File(destPath + medId.toString() + File.separator + fileName));// writing
						// to
						// random
						// named
						// directory
						filecontent = part.getInputStream();
						final byte[] bytes = new byte[1024];
						int read = 0;
						while ((read = filecontent.read(bytes)) != -1) {
							out.write(bytes, 0, read);
						}
						out.flush();
						out.close();
					}
				}

			}

			// 1. Create Folder with random number
			// 2. Write all images to folder created with step 1
			// 3. Perform INsert operation and get Primary key
			// 4. Rename folder created with step1 with primary key no.

			request.setAttribute("message", "File " + fileName + " has uploaded successfully!");

			mb = new MedicineBean();
			mb.setCatId(catId);
			mb.setMedName(medName);
			mb.setMedPrice(medPrice);
			mb.setMedDiscount(medDiscount);
			mb.setMedManufacturer(medManufacturertxt);
			mb.setMedDescription(medDescriptiontxt);
			mb.setMedMfgDate(medMfgDatetxt);
			mb.setMedExpDate(medExpDatetxt);
			mb.setQuantity(quantity);
			medicineDao.updateMedicine(medId, mb);

			/*
			 * if (dir.isDirectory()) { File newDir = new File(destPath + medId.toString());
			 * if (newDir.isDirectory())// deleting the old directory with files { for (File
			 * subfile : newDir.listFiles()) { subfile.delete(); } newDir.delete(); }
			 * dir.renameTo(newDir);// renaming random named directory to primary key named
			 * directory MedicineDAO.updatePictureId(medId);// updating the random no. with
			 * primary key in medicinetbl }
			 */

			// }

			response.sendRedirect("ShowAllMedicine");
		} catch (PharmacyDBException e) {
			e.printStackTrace();
			request.setAttribute("errMsg", "Error occured while updating medicine...");
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
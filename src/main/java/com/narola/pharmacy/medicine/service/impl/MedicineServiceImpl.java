package com.narola.pharmacy.medicine.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import com.narola.pharmacy.PharmacyDBException;
import com.narola.pharmacy.PharmacyServiceException;
import com.narola.pharmacy.medicine.dao.IMedicineDAO;
import com.narola.pharmacy.medicine.model.MedicineBean;
import com.narola.pharmacy.medicine.service.IMedicineService;
import com.narola.pharmacy.utility.Constant;
import com.narola.pharmacy.utility.DAOFactory;
import com.narola.pharmacy.utility.UtilityMethods;

public class MedicineServiceImpl implements IMedicineService {

	public void addMedicine(MedicineBean mb, HttpServletRequest request) throws PharmacyServiceException {
		try {

			IMedicineDAO medicineDao = DAOFactory.getInstance().getMedicineDAO();
			Integer medId;

			medId = medicineDao.InsertMedicine(mb);

			String fileName = null;
			String destPath = request.getServletContext().getRealPath("/") + Constant.MEDICINE_IMG_FOLDER;
			File dir = new File(destPath + medId.toString());// making a random named directory
			dir.mkdir();

			UtilityMethods.writeImagesToFolder(fileName, destPath, request.getParts(), medId);
			request.setAttribute("message", "Files has uploaded successfully!");

		} catch (PharmacyDBException e) {
			throw new PharmacyServiceException(Constant.CONST_PHARMACY_DB_EXCEPTION_MESSAGE, e);
		} catch (IOException | ServletException e) {
			throw new PharmacyServiceException(Constant.ERR_SOMETHING_WENT_WRONG, e);
		}
	}

	public void managePopularMedicine(Integer medId, String action) throws PharmacyServiceException {

		try {
			IMedicineDAO medicineDao = DAOFactory.getInstance().getMedicineDAO();
			medicineDao.managePopularity(medId, action);
		} catch (PharmacyDBException e) {
			throw new PharmacyServiceException(Constant.CONST_PHARMACY_DB_EXCEPTION_MESSAGE, e);
		} catch (Exception e) {
			throw new PharmacyServiceException(Constant.ERR_SOMETHING_WENT_WRONG, e);
		}
	}

	public void deleteMedicine(int medId) throws PharmacyServiceException {

		try {
			IMedicineDAO medicineDao = DAOFactory.getInstance().getMedicineDAO();
			medicineDao.deleteMedicine(medId);
		} catch (PharmacyDBException e) {
			throw new PharmacyServiceException(Constant.CONST_PHARMACY_DB_EXCEPTION_MESSAGE, e);
		} catch (Exception e) {
			throw new PharmacyServiceException(Constant.ERR_SOMETHING_WENT_WRONG, e);
		}
	}

	public List<MedicineBean> getAllMedicine(HttpServletRequest request) throws PharmacyServiceException {

		try {
			IMedicineDAO medicineDao = DAOFactory.getInstance().getMedicineDAO();

			List<MedicineBean> list;
			list = medicineDao.showAllMedicine();
			for (MedicineBean medItem : list) {
				File dir = new File(request.getServletContext().getRealPath("/") + Constant.MEDICINE_IMG_FOLDER
						+ medItem.getMedId());
				File[] flist = dir.listFiles();
				List<String> imagesPath = new ArrayList<>(flist.length);
				for (File file : flist) {
					imagesPath.add(request.getContextPath() + Constant.MEDICINE_IMG_FOLDER + medItem.getMedId() + "/"
							+ file.getName());
				}

				medItem.setImagesPath(imagesPath);
			}
			return list;
		} catch (PharmacyDBException e) {
			throw new PharmacyServiceException(Constant.CONST_PHARMACY_DB_EXCEPTION_MESSAGE, e);
		} catch (Exception e) {
			throw new PharmacyServiceException(Constant.ERR_SOMETHING_WENT_WRONG, e);
		}

	}

	public String updateMedicine(HttpServletRequest request, MedicineBean mb, String imagesToBeDeleted)
			throws PharmacyServiceException {
		String fileName;
		try {
			IMedicineDAO medicineDao = DAOFactory.getInstance().getMedicineDAO();

			fileName = request.getPart(Constant.CTRL_IMAGE_CONTROL).getSubmittedFileName();

			String[] listImagesToBeDeleted = imagesToBeDeleted.split(",");
			List<String> listImagesNotToBeDeleted = new ArrayList<>();
			String destPath = request.getServletContext().getRealPath("/") + Constant.MEDICINE_IMG_FOLDER;
			File oldDir = new File(destPath + mb.getMedId().toString());
			if (oldDir.isDirectory()) {
				for (File subfile : oldDir.listFiles()) {
					boolean status = false;
					for (String image : listImagesToBeDeleted) {
						if (image.equals(subfile.getName())) {
							status = true;
							subfile.delete();
							break;
						}

					}
					if (!status)
						listImagesNotToBeDeleted.add(subfile.getName());
				}

			}

			UtilityMethods.writeImagesToFolder(fileName, destPath, request.getParts(), mb.getMedId());

			medicineDao.updateMedicine(mb.getMedId(), mb);
			return fileName;
		} catch (IOException|ServletException e) {
			throw new PharmacyServiceException(Constant.ERR_SOMETHING_WENT_WRONG, e);
		} catch (PharmacyDBException e) {
			throw new PharmacyServiceException(Constant.CONST_PHARMACY_DB_EXCEPTION_MESSAGE, e);
		} 

	}

	public MedicineBean getMedicine(HttpServletRequest request, Integer medId) throws PharmacyServiceException {

		try {
			IMedicineDAO medicineDao = DAOFactory.getInstance().getMedicineDAO();
			MedicineBean mb;

			mb = medicineDao.getMedicineById(medId);
			File dir = new File(request.getServletContext().getRealPath("/") + Constant.MEDICINE_IMG_FOLDER + medId);
			File[] list = dir.listFiles();
			List<String> imagesPath = new ArrayList<>(list.length);
			for (int i = 0; i < list.length; i++) {
				imagesPath.add(request.getContextPath() + Constant.MEDICINE_IMG_FOLDER + medId.toString() + "/"
						+ list[i].getName());
			}
			mb.setImagesPath(imagesPath);

			return mb;
		} catch (PharmacyDBException e) {
			throw new PharmacyServiceException(Constant.CONST_PHARMACY_DB_EXCEPTION_MESSAGE, e);
		} catch (Exception e) {
			throw new PharmacyServiceException(Constant.ERR_SOMETHING_WENT_WRONG, e);
		}

	}

	public MedicineBean updateMedicineForm(Integer medId) throws PharmacyServiceException {

		try {
			MedicineBean mb;
			IMedicineDAO medicineDao = DAOFactory.getInstance().getMedicineDAO();
			mb = medicineDao.getMedicineById(medId);
			return mb;
		} catch (PharmacyDBException e) {
			throw new PharmacyServiceException(Constant.CONST_PHARMACY_DB_EXCEPTION_MESSAGE, e);
		} catch (Exception e) {
			throw new PharmacyServiceException(Constant.ERR_SOMETHING_WENT_WRONG, e);
		}

	}
}

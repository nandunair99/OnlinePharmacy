package com.narola.pharmacy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.narola.pharmacy.category.CategoryBean;
import com.narola.pharmacy.category.CategoryDAO;
import com.narola.pharmacy.medicine.IMedicineDAO;
import com.narola.pharmacy.medicine.MedicineBean;
import com.narola.pharmacy.test.TestBean;
import com.narola.pharmacy.test.TestDAO;
import com.narola.pharmacy.utility.Constant;
import com.narola.pharmacy.utility.DAOFactory;

/**
 * Servlet implementation class ShowCustomerHomeServlet
 */
public class ShowCustomerHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowCustomerHomeServlet() {
		super();
	}

	/**
	 * query null i.e the home page must display both medicine and test
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
			IMedicineDAO medicineDAO= DAOFactory.getInstance().getMedicineDAO(); 
			List<CategoryBean> categoryList = CategoryDAO.showAllCategory();
			request.setAttribute("CategoryList", categoryList);
			if (request.getParameter("query") == null) {
				List<MedicineBean> popularMedList = medicineDAO.showPopularMedicine();
				List<TestBean> popularTestList = TestDAO.showPopularTest();
				List<CategoryBean> popularCategoryList = CategoryDAO.showPopularCategory();
				List<MedicineBean> discountMedList = medicineDAO.showDiscountMedicine();

				for (MedicineBean medicineBean : discountMedList) {
					File dir = new File(getServletContext().getRealPath("/") + Constant.MEDICINE_IMG_FOLDER
							+ medicineBean.getMedId());
					File[] flist = dir.listFiles();
					List<String> imagesPath = new ArrayList<>(flist.length);
					for (File file : flist) {
						imagesPath.add(request.getContextPath() + Constant.MEDICINE_IMG_FOLDER + medicineBean.getMedId()
								+ "/" + file.getName());
					}
					medicineBean.setImagesPath(imagesPath);
				}
				for (MedicineBean medicineBean : popularMedList) {
					File dir = new File(getServletContext().getRealPath("/") + Constant.MEDICINE_IMG_FOLDER
							+ medicineBean.getMedId());
					File[] flist = dir.listFiles();
					List<String> imagesPath = new ArrayList<>(flist.length);
					for (File file : flist) {
						imagesPath.add(request.getContextPath() + Constant.MEDICINE_IMG_FOLDER + medicineBean.getMedId()
								+ "/" + file.getName());
					}
					medicineBean.setImagesPath(imagesPath);
				}
				for(TestBean tbean:popularTestList)
				{
					ByteArrayOutputStream bos=new ByteArrayOutputStream();
					final byte[] bytes=new byte[1024];
					int read=0;
					while((read=tbean.getPicStream().read(bytes))!=-1)
					{
						bos.write(bytes,0,read);
					}
					String encode = Base64.getEncoder().encodeToString((byte[]) bos.toByteArray());
					tbean.setBase64String(encode);
				}

				request.setAttribute("PopularCategoryList", popularCategoryList);
				request.setAttribute("PopularTestList", popularTestList);
				request.setAttribute("PopularMedicineList", popularMedList);
				request.setAttribute("DiscountMedicineList", discountMedList);

			} else {
				String query = request.getParameter("query").toString();
				if (query.equals("medicine")) // home page must show only medicines
				{
					List<MedicineBean> medList = medicineDAO.showAllMedicine();
					for(MedicineBean medicineBean:medList)
					{
						File dir = new File(getServletContext().getRealPath("/") + Constant.MEDICINE_IMG_FOLDER +medicineBean.getMedId().toString());
						File[] flist = dir.listFiles();
						List<String> imagesPath = new ArrayList<>(flist.length);
						for (File file : flist) {
							imagesPath.add(request.getContextPath() + Constant.MEDICINE_IMG_FOLDER + medicineBean.getMedId()
									+ "/" + file.getName());
						}
						medicineBean.setImagesPath(imagesPath);
					}
					request.setAttribute("MedicineList", medList);
				}
				else if (query.equals("search")) // home page must show only searched medicines
				{
					String searchString=request.getParameter("searchtxt");
					List<MedicineBean> medList = medicineDAO.searchMedicineByName(searchString);
					for(MedicineBean medicineBean:medList)
					{
						File dir = new File(getServletContext().getRealPath("/") + Constant.MEDICINE_IMG_FOLDER +medicineBean.getMedId().toString());
						File[] flist = dir.listFiles();
						List<String> imagesPath = new ArrayList<>(flist.length);
						for (File file : flist) {
							imagesPath.add(request.getContextPath() + Constant.MEDICINE_IMG_FOLDER + medicineBean.getMedId()
									+ "/" + file.getName());
						}
						medicineBean.setImagesPath(imagesPath);
					}
					request.setAttribute("MedicineList", medList);
					
					List<TestBean> testList = TestDAO.searchTestByName(searchString);
					for(TestBean tbean:testList)
					{
						ByteArrayOutputStream bos=new ByteArrayOutputStream();
						final byte[] bytes=new byte[1024];
						int read=0;
						while((read=tbean.getPicStream().read(bytes))!=-1)
						{
							bos.write(bytes,0,read);
						}
						String encode = Base64.getEncoder().encodeToString((byte[]) bos.toByteArray());
						tbean.setBase64String(encode);
					}
					request.setAttribute("TestList", testList);
				}
				else if (query.equals("test")) // home page must show only tests
				{
					List<TestBean> testList = TestDAO.showAllTest();
					for(TestBean tbean:testList)
					{
						ByteArrayOutputStream bos=new ByteArrayOutputStream();
						final byte[] bytes=new byte[1024];
						int read=0;
						while((read=tbean.getPicStream().read(bytes))!=-1)
						{
							bos.write(bytes,0,read);
						}
						String encode = Base64.getEncoder().encodeToString((byte[]) bos.toByteArray());
						tbean.setBase64String(encode);
					}
					request.setAttribute("TestList", testList);
				} else if (query.equals("category")) // home page must show only categories
				{
					List<CategoryBean> categoryList2 = CategoryDAO.showAllCategory();
					request.setAttribute("CategoryList2", categoryList2);
				} else {
					// home page must show the medicines of particular category
					List<MedicineBean> medList = medicineDAO.getMedicineByCatid(Integer.valueOf(query));
					for(MedicineBean medicineBean:medList)
					{
						File dir = new File(getServletContext().getRealPath("/") + Constant.MEDICINE_IMG_FOLDER +medicineBean.getMedId().toString());
						File[] flist = dir.listFiles();
						List<String> imagesPath = new ArrayList<>(flist.length);
						for (File file : flist) {
							imagesPath.add(request.getContextPath() + Constant.MEDICINE_IMG_FOLDER + medicineBean.getMedId()
									+ "/" + file.getName());
						}
						medicineBean.setImagesPath(imagesPath);
					}
					request.setAttribute("MedicineList", medList);
				}
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("customerhome.jsp");
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

		doGet(request, response);
	}

}

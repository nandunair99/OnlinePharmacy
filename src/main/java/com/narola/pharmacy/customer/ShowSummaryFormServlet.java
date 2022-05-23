package com.narola.pharmacy.customer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.narola.pharmacy.medicine.dao.IMedicineDAO;
import com.narola.pharmacy.medicine.model.MedicineBean;
import com.narola.pharmacy.test.dao.ITestDAO;
import com.narola.pharmacy.test.dao.TestDAOMysql;
import com.narola.pharmacy.test.model.TestBean;
import com.narola.pharmacy.utility.Constant;
import com.narola.pharmacy.utility.DAOFactory;
import com.narola.pharmacy.utility.UtilityMethods;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class ShowSummaryFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ShowSummaryFormServlet() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			IMedicineDAO medicineDAO=DAOFactory.getInstance().getMedicineDAO();
			ITestDAO testDAO=DAOFactory.getInstance().getTestDAO();
			Integer medId=null;
			Integer testId=null;
			MedicineBean medicineBean=null;
			TestBean testBean=null;
			CartBean cartBean=new CartBean();
			CartWrapper cartWrapper=new CartWrapper();
			List<CartBean> medList=new ArrayList<>();
			List<CartBean> testList=new ArrayList<>();
			cartBean.setQuantity(1);
			if (session != null && session.getAttribute("customerDetails") != null) {
				if(request.getParameter("medIdtxt")!=null)
				{
					medId=Integer.valueOf(request.getParameter("medIdtxt"));
					medicineBean=medicineDAO.getMedicineById(medId);
					File dir = new File(getServletContext().getRealPath("/") + Constant.MEDICINE_IMG_FOLDER
							+ medicineBean.getMedId());
					File[] flist = dir.listFiles();
					List<String> imagesPath = new ArrayList<>(flist.length);
					for (File file : flist) {
						imagesPath.add(request.getContextPath() + Constant.MEDICINE_IMG_FOLDER
								+ medicineBean.getMedId() + "/" + file.getName());
					}
					System.out.println(getServletContext().getRealPath("/") + "---" + request.getContextPath());
					medicineBean.setImagesPath(imagesPath);
					cartBean.setMedicineBean(medicineBean);
					medList.add(cartBean);
					
				}
				else if(request.getParameter("testIdtxt")!=null)
				{
					testId=Integer.valueOf(request.getParameter("testIdtxt"));
					testBean=testDAO.getTestById(testId);
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					final byte[] bytes = new byte[1024];
					int read = 0;
					while ((read = testBean.getPicStream().read(bytes)) != -1) {
						bos.write(bytes, 0, read);
					}
					String encode = Base64.getEncoder().encodeToString(bos.toByteArray());
					testBean.setBase64String(encode);
					cartBean.setTestBean(testBean);
					testList.add(cartBean);
					
				}
				
				cartWrapper.setMedicineBeans(medList);
				
				cartWrapper.setTestBeans(testList);
				
				request.setAttribute("CartWrapper", cartWrapper);
				String url = session.getAttribute("currentPage").toString();
				request.setAttribute("referrer", url);
				request.setAttribute("showCart", true);

				
				RequestDispatcher rd = request.getRequestDispatcher(UtilityMethods.getServletName(url));
				rd.forward(request, response);
	
			}else {
			response.sendRedirect("LoginForm");
		}
			
	} catch (Exception e) {

		e.printStackTrace();
		request.setAttribute("errMsg", "Error occured while showing order summary..");
		RequestDispatcher rd = request.getRequestDispatcher(UtilityMethods.getServletName( request.getHeader("referer")));
		rd.forward(request, response);
		return;
	}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

package com.narola.pharmacy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.narola.pharmacy.test.dao.ITestDAO;
import com.narola.pharmacy.test.dao.TestDAOMysql;
import com.narola.pharmacy.test.model.TestBean;
import com.narola.pharmacy.utility.DAOFactory;

/**
 * Servlet implementation class ShowCustomerTestServlet
 */
public class ShowCustomerTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShowCustomerTestServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ITestDAO testDao = DAOFactory.getInstance().getTestDAO();
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

		TestBean tb;
		try {
			Integer testId = Integer.valueOf(request.getParameter("testId"));
			tb = testDao.getTestById(testId);
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			final byte[] bytes=new byte[1024];
			int read=0;
			while((read=tb.getPicStream().read(bytes))!=-1)
			{
				bos.write(bytes,0,read);
			}
			String encode = Base64.getEncoder().encodeToString(bos.toByteArray());
			tb.setBase64String(encode);
			request.setAttribute("TestBean", tb);
			RequestDispatcher rd = request.getRequestDispatcher("viewcustomertest.jsp");
			rd.forward(request, response);
		} catch (PharmacyDBException e) {
			// TODO Auto-generated catch block
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

package com.narola.pharmacy.customer;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.narola.pharmacy.PharmacyDBException;


public class AddCartActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	public AddCartActionServlet() {
		super();

	}

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
		HttpSession session = request.getSession(false);

		try {
			if (session != null && session.getAttribute("customerDetails") != null) {
				RequestDispatcher rd = null;
				Integer medId = null;
				Integer testId = null;
				CustomerBean cb = (CustomerBean) session.getAttribute("customerDetails");
				if (request.getParameter("medId") != null) {
					medId = Integer.valueOf(request.getParameter("medId"));
					request.setAttribute("message", "Medicine added to cart...");
					rd = request.getRequestDispatcher("ShowCustomerMedicine?medId=" + medId);
				} else if (request.getParameter("testId") != null) {
					testId = Integer.valueOf(request.getParameter("testId"));
					request.setAttribute("message", "Test added to cart...");
					rd = request.getRequestDispatcher("ShowCustomerTest?testId=" + testId);
				}

				CustomerDAO.insertToCart(medId, testId, cb.getUserId());

				rd.forward(request, response);
			} else {
				response.sendRedirect("LoginForm");
			}
		} catch (PharmacyDBException e) {
			
			e.printStackTrace();
			request.setAttribute("errMsg", "Error occured while adding to cart...");
			String url = request.getHeader("referer");
			RequestDispatcher rd = request.getRequestDispatcher(url);
			rd.forward(request, response);
			return;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}

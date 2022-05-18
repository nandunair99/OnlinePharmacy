package com.narola.pharmacy.utility;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class GenerateConnectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public GenerateConnectionServlet() {
        super();
     
    }

    public void init(ServletConfig config) throws ServletException {	
		super.init(config);
		String dbtype=getServletContext().getInitParameter("DB-IN-USE");
		PharmacyDBConnection.getInstance().setDbname(getServletContext().getInitParameter(dbtype+"_dbname"));
		PharmacyDBConnection.getInstance().setUrl(getServletContext().getInitParameter(dbtype+"_dburl"));
		PharmacyDBConnection.getInstance().setUsername(getServletContext().getInitParameter(dbtype+"_username"));
		PharmacyDBConnection.getInstance().setPassword(getServletContext().getInitParameter(dbtype+"_password"));	
		try {
			PharmacyDBConnection.getInstance().getConnection();
			DAOFactory.getInstance().init(dbtype);
		} catch (Exception e) {
			 e.printStackTrace();
		}
    }
	

}

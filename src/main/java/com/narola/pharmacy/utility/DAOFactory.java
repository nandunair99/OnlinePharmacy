package com.narola.pharmacy.utility;

import com.narola.pharmacy.medicine.dao.IMedicineDAO;
import com.narola.pharmacy.medicine.dao.MedicineDAOMysql;
import com.narola.pharmacy.medicine.dao.MedicineDAOPostgres;
import com.narola.pharmacy.test.dao.ITestDAO;
import com.narola.pharmacy.test.dao.TestDAOMysql;
import com.narola.pharmacy.test.dao.TestDAOPostgres;

public class DAOFactory { // singleton

	private static DAOFactory DAOHelper = null;
	private IMedicineDAO medicineDAO = null;
	private ITestDAO testDAO = null;

	public static DAOFactory getInstance() {
		if(DAOHelper ==null)
		{
			DAOHelper = new DAOFactory();
		}
		return DAOHelper;
	}

	public IMedicineDAO getMedicineDAO() {
		return medicineDAO;

	}

	public ITestDAO getTestDAO() {
		return testDAO;
	}

	public void init(String daoType) throws Exception {
		if (daoType.equals(DBTypes.MYSQL.toString())) {
			medicineDAO = new MedicineDAOMysql();
			testDAO = new TestDAOMysql();
		} else if (daoType.equals(DBTypes.POSTGRES.toString())) {
			medicineDAO = new MedicineDAOPostgres();
			testDAO = new TestDAOPostgres();
		} else {
			throw new Exception("Type is not supported yet");
		}
	}

}

package com.narola.pharmacy.utility;

import com.narola.pharmacy.medicine.IMedicineDAO;
import com.narola.pharmacy.medicine.MedicineDAOMysql;
import com.narola.pharmacy.medicine.MedicineDAOPostgres;

public class DAOFactory { //singleton

	private static DAOFactory DAOHelper=null;
	public IMedicineDAO medicineDAO=null;
	
	public static DAOFactory getInstance()
	{
		if (DAOHelper == null) {
			DAOHelper = new DAOFactory();
		}
		return DAOHelper;
	}
	public IMedicineDAO getMedicineDAO()
	{
		return medicineDAO;
	}
	public void init(String daoType) throws Exception {
		if (daoType.equals(DBTypes.MYSQL.toString())) {
			medicineDAO = new MedicineDAOMysql();
		} else if (daoType.equals(DBTypes.POSTGRES.toString())) {
			medicineDAO = new MedicineDAOPostgres();
		} else {
			throw new Exception("Type is not supported yet");
		}
	}
	
}

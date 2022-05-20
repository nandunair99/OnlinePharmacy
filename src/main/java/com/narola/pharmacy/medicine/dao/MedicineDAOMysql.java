package com.narola.pharmacy.medicine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import com.narola.pharmacy.PharmacyDBException;
import com.narola.pharmacy.medicine.model.MedicineBean;
import com.narola.pharmacy.utility.PharmacyDBConnection;

public class MedicineDAOMysql implements IMedicineDAO {

	public int InsertMedicine(MedicineBean mb) throws PharmacyDBException {

		String sql = "insert into medicinetbl(catId,medName,medPrice,medManufacturer,medDescription,medMfgDate,medExpDate,quantity,createdOn,updatedOn,discount)values(?,?,?,?,?,?,?,?,now(),now(),?);";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int medId = -1;
		try {
			Connection con = PharmacyDBConnection.getInstance().getConnection();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, mb.getCatId().intValue());
			ps.setString(2, mb.getMedName().toLowerCase());
			ps.setDouble(3, mb.getMedPrice().doubleValue());
			ps.setString(4, mb.getMedManufacturer().toLowerCase());
			ps.setString(5, mb.getMedDescription().toLowerCase());

			String datestr = mb.getMedMfgDate().toString();
			Date date = Date.valueOf(datestr);
			ps.setDate(6, date);

			String datestr2 = mb.getMedExpDate().toString();
			Date date2 = Date.valueOf(datestr2);
			ps.setDate(7, date2);

			ps.setInt(8, mb.getQuantity().intValue());
			ps.setDouble(9, mb.getMedDiscount().doubleValue());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			rs.next();
			medId = rs.getInt(1);
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while Adding medicine");
		} catch (PharmacyDBException ex) {
			throw ex;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
		return medId;

	}

	public List<MedicineBean> searchMedicineByName(String name) throws PharmacyDBException {
		String sql = "select  md.*, round(md.medPrice-(md.medPrice * md.discount/100)) as 'medDiscoutedPrice' from medicinetbl md where medname like '%"
				+ name + "%'";
		List<MedicineBean> list = new ArrayList<>();
		Statement st = null;
		ResultSet rs = null;
		try {
			Connection con = PharmacyDBConnection.getInstance().getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				MedicineBean mb = new MedicineBean();

				mb.setMedId(rs.getInt(1));
				mb.setCatId(rs.getInt(2));
				mb.setMedName(rs.getString(3));
				mb.setMedPrice(rs.getDouble(4));
				mb.setMedManufacturer(rs.getString(5));
				mb.setMedDescription(rs.getString(6));
				mb.setMedMfgDate((rs.getString(7) != null) ? (rs.getDate(7).toLocalDate()) : (null));
				mb.setMedExpDate((rs.getString(8) != null) ? (rs.getDate(8).toLocalDate()) : (null));
				mb.setQuantity(rs.getInt(9));
				mb.setCreatedOn(rs.getTimestamp(10).toLocalDateTime());
				mb.setUpdatedOn(rs.getTimestamp(11).toLocalDateTime());
				mb.setPopular(rs.getBoolean(12));
				mb.setMedDiscount(rs.getDouble(13));
				mb.setMedDiscountedPrice(rs.getDouble(14));
				list.add(mb);
			}
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while searching  medicine by Name");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(st, rs);
		}
		return list;

	}

	public List<MedicineBean> getMedicineByCatid(Integer catId) throws PharmacyDBException {
		List<MedicineBean> list = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			Connection con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "select md.*, round(md.medPrice-(md.medPrice * md.discount/100)) as 'medDiscoutedPrice' from medicinetbl md where catid=?";

			ps = con.prepareStatement(sql);
			ps.setInt(1, catId);
			rs = ps.executeQuery();
			while (rs.next()) {

				MedicineBean mb = new MedicineBean();
				mb.setMedId(rs.getInt(1));
				mb.setCatId(catId);
				mb.setMedName(rs.getString(3));
				mb.setMedPrice(rs.getDouble(4));
				mb.setMedManufacturer(rs.getString(5));
				mb.setMedDescription(rs.getString(6));
				mb.setMedMfgDate((rs.getString(7) != null) ? (rs.getDate(7).toLocalDate()) : (null));
				mb.setMedExpDate((rs.getString(8) != null) ? (rs.getDate(8).toLocalDate()) : (null));
				mb.setQuantity(rs.getInt(9));
				mb.setCreatedOn(rs.getTimestamp(10).toLocalDateTime());
				mb.setUpdatedOn(rs.getTimestamp(11).toLocalDateTime());
				mb.setMedDiscount(rs.getDouble(13));
				mb.setMedDiscountedPrice(rs.getDouble(14));
				System.out.println(mb);
				list.add(mb);
			}
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while getting medicine by categoryId");
		} catch (PharmacyDBException e) {

			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
		return list;
	}

	public void updatePictureId(Integer medId) throws PharmacyDBException {
		PreparedStatement ps = null;
		try {
			Connection con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "update medicinetbl set picture=?,updatedOn=now() where medId=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, medId.intValue());
			ps.setInt(2, medId.intValue());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while updating Picture by medId");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps);
		}

	}

	public void deleteMedicine(Integer medId) throws PharmacyDBException {
		PreparedStatement ps = null;
		try {
			Connection con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "delete from medicinetbl where medId=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, medId.intValue());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while deleting medicine");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps);
		}

	}

	public void updateMedicine(Integer medId, MedicineBean mb) throws PharmacyDBException {

		PreparedStatement ps = null;
		try {
			String sql = "update medicinetbl set catId=?,medName=?,medPrice=?,medManufacturer=?,medDescription=?,medMfgDate=?,medExpDate=?,quantity=?,updatedOn=now(),discount=? where medId=?";

			Connection con = PharmacyDBConnection.getInstance().getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, mb.getCatId().intValue());
			ps.setString(2, mb.getMedName().toLowerCase());
			ps.setDouble(3, mb.getMedPrice().doubleValue());
			ps.setString(4, mb.getMedManufacturer().toLowerCase());
			ps.setString(5, mb.getMedDescription().toLowerCase());

			String datestr = mb.getMedMfgDate().toString();
			Date date = Date.valueOf(datestr);
			ps.setDate(6, date);

			String datestr2 = mb.getMedExpDate().toString();
			Date date2 = Date.valueOf(datestr2);
			ps.setDate(7, date2);

//			ps.setString(8,mb.getFilename());
			ps.setInt(8, mb.getQuantity().intValue());
			ps.setDouble(9, mb.getMedDiscount().doubleValue());
			ps.setInt(10, medId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while updating medicine" + e);
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps);
		}

	}

	public List<MedicineBean> showAllMedicine() throws PharmacyDBException {

		String sql = "select md.*, round(md.medPrice-(md.medPrice * md.discount/100)) as 'medDiscoutedPrice' from medicinetbl md";
		List<MedicineBean> list = new ArrayList<>();
		Statement st = null;
		ResultSet rs = null;
		try {
			Connection con = PharmacyDBConnection.getInstance().getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				MedicineBean mb = new MedicineBean();

				mb.setMedId(rs.getInt(1));
				mb.setCatId(rs.getInt(2));
				mb.setMedName(rs.getString(3));
				mb.setMedPrice(rs.getDouble(4));
				mb.setMedManufacturer(rs.getString(5));
				mb.setMedDescription(rs.getString(6));
				mb.setMedMfgDate((rs.getString(7) != null) ? (rs.getDate(7).toLocalDate()) : (null));
				mb.setMedExpDate((rs.getString(8) != null) ? (rs.getDate(8).toLocalDate()) : (null));
				mb.setQuantity(rs.getInt(9));
				mb.setCreatedOn(rs.getTimestamp(10).toLocalDateTime());
				mb.setUpdatedOn(rs.getTimestamp(11).toLocalDateTime());
				mb.setPopular(rs.getBoolean(12));
				mb.setMedDiscount(rs.getDouble(13));
				mb.setMedDiscountedPrice(rs.getDouble(14));
				System.out.println(mb);
				list.add(mb);
			}
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while displaying all medicine");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(st, rs);
		}
		return list;
	}

	public List<MedicineBean> showDiscountMedicine() throws PharmacyDBException {
		Statement st = null;
		ResultSet rs = null;
		List<MedicineBean> list = new ArrayList<>();
		try {
			Connection con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "select md.*, round(md.medPrice-(md.medPrice * md.discount/100)) as 'medDiscoutedPrice' from medicinetbl md order by discount desc";

			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {

				MedicineBean medicineBean = new MedicineBean();

				medicineBean.setMedId(rs.getInt(1));
				medicineBean.setCatId(rs.getInt(2));
				medicineBean.setMedName(rs.getString(3));
				medicineBean.setMedPrice(rs.getDouble(4));
				medicineBean.setMedDiscount(rs.getDouble(13));
				medicineBean.setMedDiscountedPrice(rs.getDouble(14));
				medicineBean.setMedManufacturer(rs.getString(5));
				medicineBean.setMedDescription(rs.getString(6));
				medicineBean.setMedMfgDate((rs.getString(7) != null) ? (rs.getDate(7).toLocalDate()) : (null));
				medicineBean.setMedExpDate((rs.getString(8) != null) ? (rs.getDate(8).toLocalDate()) : (null));
				medicineBean.setQuantity(rs.getInt(9));
				medicineBean.setCreatedOn(rs.getTimestamp(10).toLocalDateTime());
				medicineBean.setUpdatedOn(rs.getTimestamp(11).toLocalDateTime());
				medicineBean.setPopular(rs.getBoolean(12));
				System.out.println(medicineBean);
				list.add(medicineBean);
			}
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while displaying discounted medicine");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(st, rs);
		}
		return list;
	}

	public List<MedicineBean> showPopularMedicine() throws PharmacyDBException {

		String sql = "select md.*, round(md.medPrice-(md.medPrice * md.discount/100)) as 'medDiscoutedPrice' from medicinetbl md where popular=?";
		List<MedicineBean> list = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Connection con = PharmacyDBConnection.getInstance().getConnection();
			ps = con.prepareStatement(sql);
			ps.setBoolean(1, true);
			rs = ps.executeQuery();
			while (rs.next()) {

				MedicineBean mb = new MedicineBean();

				mb.setMedId(rs.getInt(1));
				mb.setCatId(rs.getInt(2));
				mb.setMedName(rs.getString(3));
				mb.setMedPrice(rs.getDouble(4));
				mb.setMedManufacturer(rs.getString(5));
				mb.setMedDescription(rs.getString(6));
				mb.setMedMfgDate((rs.getString(7) != null) ? (rs.getDate(7).toLocalDate()) : (null));
				mb.setMedExpDate((rs.getString(8) != null) ? (rs.getDate(8).toLocalDate()) : (null));
				mb.setQuantity(rs.getInt(9));
				mb.setCreatedOn(rs.getTimestamp(10).toLocalDateTime());
				mb.setUpdatedOn(rs.getTimestamp(11).toLocalDateTime());
				mb.setMedDiscount(rs.getDouble(13));
				mb.setMedDiscountedPrice(rs.getDouble(14));

				System.out.println(mb);
				list.add(mb);
			}
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while displaying popular medicine");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
		return list;
	}

	public MedicineBean getMedicineById(Integer medId) throws PharmacyDBException {
		MedicineBean mb = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select md.*, round(md.medPrice-(md.medPrice * md.discount/100)) as 'medDiscoutedPrice' from medicinetbl as md where medId=?;";

			Connection con = PharmacyDBConnection.getInstance().getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, medId.intValue());
			rs = ps.executeQuery();
			while (rs.next()) {
				mb = new MedicineBean();

				mb.setMedId(rs.getInt(1));
				mb.setCatId(rs.getInt(2));
				mb.setMedName(rs.getString(3));
				mb.setMedPrice(rs.getDouble(4));
				mb.setMedManufacturer(rs.getString(5));
				mb.setMedDescription(rs.getString(6));
				mb.setMedMfgDate((rs.getString(7) != null) ? (rs.getDate(7).toLocalDate()) : (null));
				mb.setMedExpDate((rs.getString(8) != null) ? (rs.getDate(8).toLocalDate()) : (null));
				mb.setQuantity(rs.getInt(9));
				mb.setCreatedOn(rs.getTimestamp(10).toLocalDateTime());
				mb.setUpdatedOn(rs.getTimestamp(11).toLocalDateTime());
				mb.setMedDiscount(rs.getDouble(13));
				mb.setMedDiscountedPrice(rs.getDouble(14));

				System.out.println(mb);
			}

		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while displaying medicine by medId" + e);
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}

		return mb;

	}

	public boolean medicineIsExist(String medName) throws PharmacyDBException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean status = false;
		try {

			Connection con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "select * from medicinetbl where medName =?";
			ps = con.prepareStatement(sql);
			ps.setString(1, medName.toLowerCase());
			rs = ps.executeQuery();
			status = rs.next();
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while checking if medicine exists");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
		return status;

	}

	public void managePopularity(Integer medId, String action) throws PharmacyDBException {

		String sql = "update medicinetbl set popular=?,updatedOn=now() where medId=?";
		PreparedStatement ps = null;
		try {
			Connection con = PharmacyDBConnection.getInstance().getConnection();
			ps = con.prepareStatement(sql);
			ps.setBoolean(1, Boolean.parseBoolean(action));
			ps.setInt(2, medId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while managing popular medicine");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps);
		}

	}
}

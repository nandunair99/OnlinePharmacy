package com.narola.pharmacy.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.narola.pharmacy.PharmacyDBException;
import com.narola.pharmacy.utility.PharmacyDBConnection;

public class TestDAO {

	private static Connection con;

	public static int InsertTest(TestBean tb) throws PharmacyDBException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int catId = -1;
		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "insert into testtbl (testName,testPrice,testDesc,testPreparation,picture,createdOn,updatedOn,discount) values(?,?,?,?,?,now(),now(),?)";
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, tb.getTestName().toLowerCase());
			ps.setDouble(2, tb.getTestPrice());
			ps.setString(3, tb.getTestDescription().toLowerCase());
			ps.setString(4, tb.getTestPreparation().toLowerCase());
			ps.setBlob(5, tb.getPicStream());
			ps.setDouble(6, tb.getTestDiscount());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			rs.next();
			catId = rs.getInt(1);

		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while Adding test");
		} catch (PharmacyDBException ex) {
			throw ex;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
		return catId;
	}

	public static void deleteTest(Integer testId) throws PharmacyDBException {
		PreparedStatement ps = null;
		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "delete from testtbl where testId=?";

			ps = con.prepareStatement(sql);
			ps.setInt(1, testId.intValue());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while deleting test");
		} catch (PharmacyDBException ex) {
			throw ex;
		} finally {
			PharmacyDBConnection.releaseResource(ps);
		}

	}

	public static void updateTest(Integer testId, TestBean tb) throws PharmacyDBException {
		PreparedStatement ps = null;
		try {
			con = PharmacyDBConnection.getInstance().getConnection();

			if (tb.getPicStream() == null) {
				String sql = "update testtbl set testName=?,testPrice=?,testDesc=?,testPreparation=?,updatedOn=now(),discount=? where testId=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, tb.getTestName().toLowerCase());
				ps.setDouble(2, tb.getTestPrice());
				ps.setString(3, tb.getTestDescription().toLowerCase());
				ps.setString(4, tb.getTestPreparation().toLowerCase());
				ps.setDouble(5, tb.getTestDiscount());
				ps.setInt(6, testId);
			} else {
				String sql = "update testtbl set testName=?,testPrice=?,testDesc=?,testPreparation=?,picture=?,updatedOn=now(),discount=? where testId=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, tb.getTestName().toLowerCase());
				ps.setDouble(2, tb.getTestPrice());
				ps.setString(3, tb.getTestDescription().toLowerCase());
				ps.setString(4, tb.getTestPreparation().toLowerCase());
				ps.setBlob(5, tb.getPicStream());
				ps.setDouble(6, tb.getTestDiscount());
				ps.setInt(7, testId);
			}

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while updating test");
		} catch (PharmacyDBException ex) {
			throw ex;
		} finally {
			PharmacyDBConnection.releaseResource(ps);
		}

	}

	public static TestBean getTestById(Integer testId) throws PharmacyDBException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		TestBean tb = new TestBean();
		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "select td.*, round(td.testPrice-(td.testPrice * td.discount/100)) as 'testDiscoutedPrice' from testtbl td where testId =?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, testId);
			rs = ps.executeQuery();
			while (rs.next()) {

				tb.setTestId(testId);
				tb.setTestName(rs.getString(2));
				tb.setTestPrice(rs.getDouble(3));
				tb.setTestDescription(rs.getString(4));
				tb.setTestPreparation(rs.getString(5));
				tb.setPicStream(rs.getBinaryStream(6));
				tb.setTestDiscount(rs.getDouble(10));
				tb.setTestDiscountedPrice(rs.getDouble(11));
			}

		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while getting test by ID");
		} catch (PharmacyDBException ex) {
			throw ex;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
		return tb;
	}

	public static boolean TestIsExist(String testName) throws PharmacyDBException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean status = false;
		try {

			con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "select * from testtbl where testName =?";
			ps = con.prepareStatement(sql);
			ps.setString(1, testName.toLowerCase());
			rs = ps.executeQuery();
			status=rs.next();
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while checking if test existst");
		} catch (PharmacyDBException ex) {
			throw ex;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
		return status;

	}

	public static List<TestBean> showAllTest() throws PharmacyDBException {
		List<TestBean> tlist = new ArrayList<>();
		Statement st = null;
		ResultSet rs = null;
		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "select td.*, round(td.testPrice-(td.testPrice * td.discount/100)) as 'testDiscoutedPrice' from testtbl td";
			st = con.createStatement();
			rs = st.executeQuery(sql);

			while (rs.next()) {

				TestBean tb = new TestBean();
				tb.setTestId(rs.getInt(1));
				tb.setTestName(rs.getString(2));
				tb.setTestPrice(rs.getDouble(3));
				tb.setTestDescription(rs.getString(4));
				tb.setTestPreparation(rs.getString(5));
				tb.setPicStream(rs.getBinaryStream(6));
				tb.setPopular(rs.getBoolean(9));
				tb.setTestDiscount(rs.getDouble(10));
				tb.setTestDiscountedPrice(rs.getDouble(11));
				tlist.add(tb);

			}

		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while displaying all test");
		} catch (PharmacyDBException ex) {
			throw ex;
		} finally {
			PharmacyDBConnection.releaseResource(st, rs);
		}
		return tlist;
	}
	
	public static List<TestBean> searchTestByName(String name) throws PharmacyDBException {
		List<TestBean> tlist = new ArrayList<>();
		Statement st = null;
		ResultSet rs = null;
		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "select td.*, round(td.testPrice-(td.testPrice * td.discount/100)) as 'testDiscoutedPrice' from testtbl td where testname like '%"+name+"%'";
			st = con.createStatement();
			rs = st.executeQuery(sql);

			while (rs.next()) {

				TestBean tb = new TestBean();
				tb.setTestId(rs.getInt(1));
				tb.setTestName(rs.getString(2));
				tb.setTestPrice(rs.getDouble(3));
				tb.setTestDescription(rs.getString(4));
				tb.setTestPreparation(rs.getString(5));
				tb.setPicStream(rs.getBinaryStream(6));
				tb.setPopular(rs.getBoolean(9));
				tb.setTestDiscount(rs.getDouble(10));
				tb.setTestDiscountedPrice(rs.getDouble(11));
				tlist.add(tb);

			}

		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while displaying all test");
		} catch (PharmacyDBException ex) {
			throw ex;
		} finally {
			PharmacyDBConnection.releaseResource(st, rs);
		}
		return tlist;
	}


	public static List<TestBean> showPopularTest() throws PharmacyDBException {
		List<TestBean> tlist = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "select td.*, round(td.testPrice-(td.testPrice * td.discount/100)) as 'testDiscoutedPrice' from testtbl td where popular=?";
			ps = con.prepareStatement(sql);
			ps.setBoolean(1, true);
			rs = ps.executeQuery();

			while (rs.next()) {

				TestBean tb = new TestBean();
				tb.setTestId(rs.getInt(1));
				tb.setTestName(rs.getString(2));
				tb.setTestPrice(rs.getDouble(3));
				tb.setTestDescription(rs.getString(4));
				tb.setTestPreparation(rs.getString(5));
				tb.setPicStream(rs.getBinaryStream(6));
				tb.setTestDiscount(rs.getDouble(10));
				tb.setTestDiscountedPrice(rs.getDouble(11));
				tlist.add(tb);

			}

		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while displaying popular test");
		} catch (PharmacyDBException ex) {
			throw ex;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
		return tlist;
	}

	public static void managePopularity(Integer testId, String action) throws PharmacyDBException {
		String sql = "update testtbl set popular=?,updatedOn=now() where testId=?";
		PreparedStatement ps = null;
		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			ps = con.prepareStatement(sql);
			ps.setBoolean(1, Boolean.parseBoolean(action));
			ps.setInt(2, testId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while managing popular test");
		} catch (PharmacyDBException ex) {
			throw ex;
		} finally {
			PharmacyDBConnection.releaseResource(ps);
		}

	}
}

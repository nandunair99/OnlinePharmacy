package com.narola.pharmacy.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.narola.pharmacy.PharmacyDBException;
import com.narola.pharmacy.utility.PharmacyDBConnection;

public class CategoryDAO {

	/**
	 * insert new category
	 */
	public static int InsertCategory(String catName) throws PharmacyDBException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Connection con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "insert into categorytbl(catName,createdOn,UpdatedOn)values(?,now(),now());";
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, catName.toLowerCase());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw new PharmacyDBException("Error while inserting category", e);
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}

	}

	/**
	 * 
	 * check if category is used before deleting category
	 */
	public static boolean isCategoryUsed(Integer catId) throws PharmacyDBException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Connection con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "select * from medicinetbl where catid=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, catId);
			rs = ps.executeQuery();
			return rs.next();

		} catch (SQLException e) {
			throw new PharmacyDBException("Error while inserting category", e);
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}

	}

	/**
	 * returns entire category object by Id for update
	 */
	public static CategoryBean getCategoryById(Integer catId) throws PharmacyDBException {

		CategoryBean cb = new CategoryBean();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {

			String sql = "select * from categorytbl where catId=?;";
			Connection con = PharmacyDBConnection.getInstance().getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, catId.intValue());
			rs = ps.executeQuery();
			while (rs.next()) {

				cb.setCatId(catId);
				cb.setCatName(rs.getString("catName"));
				cb.setCreatedOn(rs.getTimestamp("createdOn").toLocalDateTime());
				cb.setUpdatedOn(rs.getTimestamp("updatedOn").toLocalDateTime());

			}

		} catch (SQLException e) {
			throw new PharmacyDBException("Category not found by ID", null);
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
		return cb;

	}

	/**
	 * deletes the specified category
	 */
	public static void deleteCategory(Integer catid) throws PharmacyDBException { // deletes the specified category
		PreparedStatement ps = null;
		try {

			Connection con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "delete from categorytbl where catId=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, catid.intValue());
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while deleting category");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps);
		}

	}

	/**
	 * updates the specified category's name
	 */
	public static void updateCategory(int catid, String catname) throws PharmacyDBException {
		PreparedStatement ps = null;
		try {
			Connection con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "update categorytbl set catName=?,updatedOn=now() where catId=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, catname.toLowerCase());
			ps.setInt(2, catid);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while updating category");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps);
		}

	}

	/**
	 * checks if category with specified name exists while inserting and updating
	 * category to avoid duplicity
	 * 
	 * @throws PharmacyDBException
	 */
	public static boolean categoryIsExist(String catName) throws PharmacyDBException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {

			Connection con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "select * from categorytbl where catName =?";
			ps = con.prepareStatement(sql);
			ps.setString(1, catName.toLowerCase());
			rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			throw new PharmacyDBException("Error while checking if category exists", e);
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}

	}

	/**
	 * return list of all categories
	 */
	public static List<CategoryBean> showAllCategory() throws PharmacyDBException {
		String sql = "select * from categorytbl";
		List<CategoryBean> list = new ArrayList<>();
		ResultSet rs = null;
		Statement st = null;
		try {

			Connection con = PharmacyDBConnection.getInstance().getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {

				CategoryBean cb = new CategoryBean();
				cb.setCatId(rs.getInt("catId"));
				cb.setCatName(rs.getString("catName"));
				cb.setCreatedOn(rs.getTimestamp("createdOn").toLocalDateTime());
				cb.setUpdatedOn(rs.getTimestamp("updatedOn").toLocalDateTime());
				cb.setPopular(rs.getBoolean("popular"));
				list.add(cb);
			}

		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while displaying all the categories", e);
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(st, rs);
		}

		return list;

	}

	/**
	 * show All popular categories
	 */
	public static List<CategoryBean> showPopularCategory() throws PharmacyDBException {
		String sql = "select * from categorytbl where popular=?";
		List<CategoryBean> list = new ArrayList<CategoryBean>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			Connection con = PharmacyDBConnection.getInstance().getConnection();

			ps = con.prepareStatement(sql);
			ps.setBoolean(1, true);
			rs = ps.executeQuery();
			while (rs.next()) {

				CategoryBean cb = new CategoryBean();
				cb.setCatId(rs.getInt("catId"));
				cb.setCatName(rs.getString("catName"));
				cb.setCreatedOn(rs.getTimestamp("createdOn").toLocalDateTime());
				cb.setUpdatedOn(rs.getTimestamp("updatedOn").toLocalDateTime());
				list.add(cb);
			}

		} catch (SQLException e) {
			throw new PharmacyDBException("Error  occured while showing popular category", e);
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}

		return list;

	}

	/**
	 * @throws PharmacyDBException
	 * 
	 */
	public static void updatePopularity(Integer catId, String action) throws PharmacyDBException {
		PreparedStatement ps = null;
		String sql = "update categorytbl set popular=?,updatedOn=now() where catId=?";
		try {
			Connection con = PharmacyDBConnection.getInstance().getConnection();
			ps = con.prepareStatement(sql);
			ps.setBoolean(1, Boolean.parseBoolean(action));
			ps.setInt(2, catId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while updating popularity", e);
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps);
		}

	}
}

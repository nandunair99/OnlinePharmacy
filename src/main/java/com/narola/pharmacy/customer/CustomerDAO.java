package com.narola.pharmacy.customer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.narola.pharmacy.PharmacyDBException;
import com.narola.pharmacy.medicine.dao.IMedicineDAO;
import com.narola.pharmacy.medicine.model.MedicineBean;
import com.narola.pharmacy.test.dao.ITestDAO;
import com.narola.pharmacy.test.dao.TestDAOMysql;
import com.narola.pharmacy.test.model.TestBean;
import com.narola.pharmacy.utility.Constant;
import com.narola.pharmacy.utility.DAOFactory;
import com.narola.pharmacy.utility.PharmacyDBConnection;

public class CustomerDAO {

	public static int insertCustomer(CustomerBean cb) throws PharmacyDBException {

		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		Connection con = null;
		int userId = -1;
		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			con.setAutoCommit(false);
			String sql1 = "insert into usertbl(password,isActive,mailId,userType,createdOn,updatedOn)values(?,?,?,?,now(),now())";
			ps = con.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, cb.getPassword());
			ps.setBoolean(2, true);
			ps.setString(3, cb.getEmailId());
			ps.setBoolean(4, true);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			rs.next();
			userId = rs.getInt(1);

			sql1 = "insert into patienttbl(userId,firstname,lastname,contact,dob,address,profileImg,createdOn,updatedOn)values(?,?,?,?,?,?,?,now(),now())";
			ps2 = con.prepareStatement(sql1);
			ps2.setInt(1, userId);
			ps2.setString(2, cb.getFirstName().toLowerCase());
			ps2.setString(3, cb.getLastName().toLowerCase());
			ps2.setString(4, cb.getContactNo());
			String datestr = cb.getDob().toString();
			Date date = Date.valueOf(datestr);
			ps2.setDate(5, date);
			ps2.setString(6, cb.getAddress().toLowerCase());
			ps2.setBlob(7, cb.getPicStream());

			ps2.executeUpdate();

			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new PharmacyDBException("Error occured while rolling back " + e);
			}
			throw new PharmacyDBException("Error occured while registering user" + e);
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
			PharmacyDBConnection.releaseResource(ps2);
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				throw new PharmacyDBException("Error occured while setting autoCommit true");
			}
		}
		return userId;
	}

	public static void updateRazorpayOrderId(String razorpayOrderId, Integer orderId) throws PharmacyDBException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = PharmacyDBConnection.getInstance().getConnection();

			String sql = "update ordertbl set razorpay_orderId=?,updatedOn=now() where orderId=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, razorpayOrderId);
			ps.setInt(2, orderId);
			ps.executeUpdate();

		} catch (SQLException e) {

			throw new PharmacyDBException("Error occured while updating razorpay orderId ");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
	}

	public static void updateTransactionStatus(String status,String ErrorField,String reason, Integer orderId) throws PharmacyDBException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			if(ErrorField!=null && reason!=null)
			{
				String sql = "update transactiontbl set reason=?,errorField=?,updatedOn=now() where orderId=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, reason);
				ps.setString(2, ErrorField);
				ps.setInt(3, orderId);
			}
			else if(status!=null)
			{
				String sql = "update transactiontbl set status=?,updatedOn=now() where orderId=?";
				ps = con.prepareStatement(sql);
				
				ps.setString(1, status);
				ps.setInt(2, orderId);
			}
			
			ps.executeUpdate();

		} catch (SQLException e) {

			throw new PharmacyDBException("Error occured while updating transaction status ");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
	}

	public static void updateOrderStatus(String status, Integer orderId) throws PharmacyDBException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = PharmacyDBConnection.getInstance().getConnection();

			String sql = "update ordertbl set status=?,updatedOn=now() where orderId=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, status);
			ps.setInt(2, orderId);
			ps.executeUpdate();

		} catch (SQLException e) {

			throw new PharmacyDBException("Error occured while updating transaction status ");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
	}
	
	

	public static int insertOrderItem(Integer orderId, CartBean cartBean) throws PharmacyDBException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer orderItemId = null;
		Connection con = null;
		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			if (cartBean.getMedicineBean() == null && cartBean.getTestBean() == null) // cart is empty
			{
				return -1;
			}
			if (cartBean.getMedicineBean().getMedId() != 0) {
				String sql = "insert into orderitemtbl(orderId,userId,medId,quantity,createdOn,updatedOn)values(?,?,?,?,now(),now())";
				ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(3, cartBean.getMedicineBean().getMedId());
			}

			else if (cartBean.getTestBean().getTestId() != 0) {
				String sql = "insert into orderitemtbl(orderId,userId,testId,quantity,createdOn,updatedOn)values(?,?,?,?,now(),now())";
				ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(3, cartBean.getTestBean().getTestId());
			}
			ps.setInt(1, orderId);
			ps.setInt(2, cartBean.getUserId());
			ps.setInt(4, cartBean.getQuantity());

			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			rs.next();
			orderItemId = rs.getInt(1);

		} catch (SQLException e) {

			throw new PharmacyDBException("Error occured while inserting order item ");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
		return orderItemId;
	}

	public static List<OrderBean> getAllOrders() throws PharmacyDBException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		List<OrderBean> orderBeans = new ArrayList<>();

		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "select o.* from ordertbl o inner join transactiontbl t on o.orderId=t.orderId where t.status=\"Success\" order by o.date desc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				OrderBean orderBean = new OrderBean();
				orderBean.setOrderId(rs.getInt("orderId"));
				orderBean.setDate(rs.getDate("date").toLocalDate());
				orderBean.setTotalAmount(rs.getDouble("totalAmount"));
				orderBean.setStatus(rs.getString("status"));
				orderBean.setUserId(rs.getInt("userId"));
				orderBeans.add(orderBean);
			}
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while fetching all order ");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
		return orderBeans;
	}

	public static int insertTransaction(Integer orderId, Integer userId, Double totalAmount)
			throws PharmacyDBException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer transactionId = null;
		Connection con = null;
		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "insert into transactiontbl(orderId,userId,totalAmount,status,createdOn,updatedOn)values(?,?,?,?,now(),now())";
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, orderId);
			ps.setInt(2, userId);
			ps.setDouble(3, totalAmount);
			ps.setString(4, Constant.TRNS_STATUS_INITIATED);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			rs.next();
			transactionId = rs.getInt(1);

		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while inserting into transaction ");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
		return transactionId;
	}

	public static int insertOrder(OrderBean orderBean) throws PharmacyDBException {

		PreparedStatement ps = null;
		Integer orderId = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			IMedicineDAO medicineDAO=DAOFactory.getInstance().getMedicineDAO();
			ITestDAO testDAO=DAOFactory.getInstance().getTestDAO();
			con = PharmacyDBConnection.getInstance().getConnection();
			con.setAutoCommit(false);
			String sql = "insert into ordertbl(userId,date,totalAmount,Status,createdOn,updatedOn)values(?,now(),?,?,now(),now())";
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, orderBean.getCustomerBean().getUserId());
			ps.setDouble(2, orderBean.getTotalAmount());
			ps.setString(3, Constant.ORDER_STATUS_NEW);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			rs.next();
			orderId = rs.getInt(1);

			if (!orderBean.getCartIds()[0].isEmpty()) {
				for (String cartId : orderBean.getCartIds()) {
					CartBean cartBean = getCartItemById(Integer.valueOf(cartId));
					insertOrderItem(orderId, cartBean);
				}
				for (String cartId : orderBean.getCartIds()) {
					deleteCartItem(Integer.valueOf(cartId));
				}

			} else {
				CartBean cartBean = new CartBean();
				if (orderBean.getMedId() != null) {
					cartBean.setMedicineBean(medicineDAO.getMedicineById(orderBean.getMedId()));
					TestBean testBean = new TestBean();
					testBean.setTestId(0);
					cartBean.setTestBean(testBean);
				} else if (orderBean.getTestId() != null) {
					cartBean.setTestBean(testDAO.getTestById(orderBean.getTestId()));
					MedicineBean medicineBean = new MedicineBean();
					medicineBean.setMedId(0);
					cartBean.setMedicineBean(medicineBean);
				}
				cartBean.setUserId(orderBean.getCustomerBean().getUserId());
				cartBean.setQuantity(Constant.ORDER_DEFAULT_QUANTITY);
				insertOrderItem(orderId, cartBean);
			}

			insertTransaction(orderId, orderBean.getCustomerBean().getUserId(), orderBean.getTotalAmount());
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new PharmacyDBException("Error occured while rollback insert order item ");
			}
			throw new PharmacyDBException("Error occured while inserting order");
		} catch (PharmacyDBException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new PharmacyDBException("Error occured while rollback insert order item ");
			}
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			PharmacyDBConnection.releaseResource(ps, rs);
		}
		return orderId;
	}

	public static OrderBean getOrderDetails(Integer orderId) throws PharmacyDBException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		OrderBean orderBean = new OrderBean();
		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "select totalAmount,razorpay_orderId from ordertbl where orderId=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, orderId);
			rs = ps.executeQuery();
			while (rs.next()) {
				orderBean.setTotalAmount(rs.getDouble("totalAmount"));
				orderBean.setRazorpayOrderId(rs.getString("razorpay_orderId"));
			}
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while getting order details");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
		return orderBean;
	}

	public static CustomerBean getCustomerByContact(String contactNo) throws PharmacyDBException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		CustomerBean cb = new CustomerBean();

		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "select * from patienttbl as p inner join usertbl as u  on p.userId=u.userId where p.contact=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, contactNo);

			rs = ps.executeQuery();
			while (rs.next()) {
				cb.setUserId(rs.getInt("userId"));
				cb.setFirstName(rs.getString("firstname"));
				cb.setLastName(rs.getString("lastname"));
				cb.setContactNo(rs.getString("contact"));
				cb.setDob(rs.getDate("dob").toLocalDate());
				cb.setAddress(rs.getString("address"));
				cb.setPicStream(rs.getBinaryStream("profileImg"));
				cb.setEmailId(rs.getString("mailId"));

			}
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while getting customer details by contact no.");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
		return cb;
	}

	public static boolean customerLogin(String contactNo, String password) throws PharmacyDBException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		boolean status = false;
		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "select * from patienttbl as p inner join usertbl as u  on p.userId=u.userId where p.contact=? and u.password=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, contactNo);
			ps.setString(2, password);
			rs = ps.executeQuery();
			status = rs.next();
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while checking customer login ");
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
		return status;
	}

	public static void deleteCartItem(Integer cartId) throws PharmacyDBException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			String sql = null;
			con = PharmacyDBConnection.getInstance().getConnection();
			sql = "delete from carttbl where cartId=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, cartId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while deleting cart item.");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}

	}

	public static void insertToCart(Integer medId, Integer testId, Integer userId) throws PharmacyDBException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection con = null;
		try {
			String sql = null;
			String sql2 = null;
			Integer cartId = null;
			con = PharmacyDBConnection.getInstance().getConnection();
			sql2 = "select cartId from carttbl where (medId=? or testId=?) and userId=?";
			ps2 = con.prepareStatement(sql2);
			if (medId != null) {
				ps2.setInt(1, medId);
				ps2.setInt(2, medId);
			} else if (testId != null) {
				ps2.setInt(1, testId);
				ps2.setInt(2, testId);
			}
			ps2.setInt(3, userId);
			rs2 = ps2.executeQuery();
			if (rs2.next()) {
				cartId = rs2.getInt(1);
				updateQuantity(cartId, userId, "increment");
			} else {
				if (medId != null) {
					sql = "insert into carttbl(userId,medId,createdOn,updatedOn) values(?,?,now(),now())";
					ps = con.prepareStatement(sql);
					ps.setInt(1, userId);
					ps.setInt(2, medId);

				} else if (testId != null) {
					sql = "insert into carttbl(userId,testId,createdOn,updatedOn) values(?,?,now(),now())";
					ps = con.prepareStatement(sql);
					ps.setInt(1, userId);
					ps.setInt(2, testId);
				} else {
					throw new PharmacyDBException("both medId and testId cannot be null;");
				}

				ps.executeUpdate();
			}
			System.out.println("inserting ->" + medId + " " + testId + " " + userId);

		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while inserting into cart.");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
			PharmacyDBConnection.releaseResource(ps2, rs2);
		}

	}

	public static void updateCartQuantity(int cartId, int quantity) throws PharmacyDBException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		try {

			con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "update carttbl set quantity=?,updatedOn=now() where cartId=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, quantity);
			ps.setInt(2, cartId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while updating into cart quantity.");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);

		}

	}

	public static CartBean getCartItemById(Integer cartId) throws PharmacyDBException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		CartBean cb = new CartBean();
		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "select * from carttbl where cartId=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, cartId);
			rs = ps.executeQuery();
			while (rs.next()) {
				cb.setCartId(rs.getInt(1));
				cb.setUserId(rs.getInt(2));
				MedicineBean medicineBean = new MedicineBean();
				medicineBean.setMedId(rs.getInt(3));
				cb.setMedicineBean(medicineBean);
				cb.setQuantity(rs.getInt(4));
				TestBean tb = new TestBean();
				tb.setTestId(rs.getInt(5));
				cb.setTestBean(tb);

			}
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while getting cart item by ID." + e);
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
		return cb;

	}

	public static List<OrderBean> showPastOrders(Integer userId) throws PharmacyDBException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		List<OrderBean> pastOrders = new ArrayList<OrderBean>();
		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "SELECT o.orderId,o.userId,o.date,t.totalAmount,t.transactionId,o.status as orderStatus ,t.status FROM ordertbl o inner join transactiontbl"
					+ " t on o.orderId=t.orderId where o.userId=? and t.status=\"Success\" order by t.updatedOn desc";
			ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				OrderBean orderBean = new OrderBean();
				orderBean.setOrderId(rs.getInt("orderId"));
				orderBean.setStatus(rs.getString("orderStatus"));
				orderBean.setDate(rs.getDate("date").toLocalDate());
				CartWrapper cartWrapper = CustomerDAO.showOrderSummary(rs.getInt("orderId"));
				orderBean.setCartWrapper(cartWrapper);
				pastOrders.add(orderBean);
			}

		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while getting Past Orders." + e);
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}
		return pastOrders;
	}

	public static CartWrapper showOrderSummary(Integer orderId) throws PharmacyDBException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection con = null;
		List<CartBean> medicinelist = new ArrayList<>();
		List<CartBean> testlist = new ArrayList<>();
		CartWrapper cartWrapper = new CartWrapper();
		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "select o.orderId,o.userId,m.medId,o.quantity,m.medName,m.medPrice,m.medManufacturer,m.medDescription,round(m.medPrice-(m.medPrice * m.discount/100)) as 'medDiscoutedPrice'  from orderItemtbl o inner join medicinetbl m on o.medId=m.medId where orderId=?";

			String sql2 = "select o.orderId,o.userId,t.testId,o.quantity,t.testName,t.testPrice,t.testDesc,t.testPreparation,t.picture,round(t.testPrice-(t.testPrice * t.discount/100)) as 'testDiscoutedPrice'  from orderItemtbl o inner join testtbl t on o.testId=t.testId where orderId=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, orderId);
			rs = ps.executeQuery();
			while (rs.next()) {
				CartBean cb = new CartBean();
				cb.setUserId(rs.getInt("userId"));
				cb.setQuantity(rs.getInt("quantity"));
				MedicineBean mb = new MedicineBean();
				mb.setMedId(rs.getInt("medId"));
				mb.setMedName(rs.getString("medName"));
				mb.setMedPrice(rs.getDouble("medPrice"));
				mb.setMedManufacturer(rs.getString("medManufacturer"));
				mb.setMedDescription(rs.getString("medDescription"));
				mb.setMedDiscountedPrice(rs.getDouble("medDiscoutedPrice"));

				cb.setMedicineBean(mb);
				medicinelist.add(cb);
			}
			ps2 = con.prepareStatement(sql2);
			ps2.setInt(1, orderId);
			rs2 = ps2.executeQuery();
			while (rs2.next()) {
				CartBean cb2 = new CartBean();
				cb2.setUserId(rs2.getInt("userId"));
				cb2.setQuantity(rs2.getInt("quantity"));
				TestBean tb = new TestBean();
				tb.setTestId(rs2.getInt("testId"));
				tb.setTestName(rs2.getString("testName"));
				tb.setTestPrice(rs2.getDouble("testPrice"));
				tb.setTestDescription(rs2.getString("testDesc"));
				tb.setTestPreparation(rs2.getString("testPreparation"));
				tb.setPicStream(rs2.getBinaryStream("picture"));
				tb.setTestDiscountedPrice(rs2.getDouble("testDiscoutedPrice"));
				cb2.setTestBean(tb);
				testlist.add(cb2);
			}
			cartWrapper.setMedicineBeans(medicinelist);
			cartWrapper.setTestBeans(testlist);
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while getting order Summary." + e);
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
			PharmacyDBConnection.releaseResource(ps2, rs2);
		}

		return cartWrapper;
	}

	public static CartWrapper ShowCartItems(Integer userId) throws PharmacyDBException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection con = null;
		List<CartBean> medicinelist = new ArrayList<>();
		List<CartBean> testlist = new ArrayList<>();
		CartWrapper cartWrapper = new CartWrapper();
		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			String sql = "select c.cartId,c.userId,c.quantity,m.medId,m.catId,m.medName,m.medPrice,m.medManufacturer,m.medDescription,m.medMfgDate,"
					+ "m.medExpDate,m.quantity,m.popular,m.discount,round(m.medPrice-(m.medPrice * m.discount/100)) as 'medDiscoutedPrice'"
					+ " from carttbl c inner join medicinetbl m on c.medId=m.medId where userId=? order by c.updatedOn desc";

			String sql2 = "select c.cartId,c.userId,c.quantity,t.testId,t.testName,t.testPrice,t.testDesc,t.testPreparation,"
					+ "t.picture,t.popular,t.discount,round(t.testPrice-(t.testPrice * t.discount/100)) as 'testDiscoutedPrice'"
					+ " from carttbl c inner join testtbl t on c.testId=t.testId where userId=? order by c.updatedOn desc";
			ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				CartBean cb = new CartBean();
				cb.setCartId(rs.getInt("cartId"));
				cb.setUserId(userId);
				cb.setQuantity(rs.getInt("quantity"));
				MedicineBean mb = new MedicineBean();
				mb.setMedId(rs.getInt("medId"));
				mb.setCatId(rs.getInt("catId"));
				mb.setMedName(rs.getString("medName"));
				mb.setMedPrice(rs.getDouble("medPrice"));
				mb.setMedManufacturer(rs.getString("medManufacturer"));
				mb.setMedDescription(rs.getString("medDescription"));
				mb.setMedMfgDate(
						(rs.getString("medMfgDate") != null) ? (rs.getDate("medMfgDate").toLocalDate()) : (null));
				mb.setMedExpDate(
						(rs.getString("medExpDate") != null) ? (rs.getDate("medExpDate").toLocalDate()) : (null));
				mb.setQuantity(rs.getInt("quantity"));
				mb.setPopular(rs.getBoolean("popular"));
				mb.setMedDiscount(rs.getDouble("discount"));
				mb.setMedDiscountedPrice(rs.getDouble("medDiscoutedPrice"));

				cb.setMedicineBean(mb);
				medicinelist.add(cb);
			}
			ps2 = con.prepareStatement(sql2);
			ps2.setInt(1, userId);
			rs2 = ps2.executeQuery();
			while (rs2.next()) {
				CartBean cb2 = new CartBean();
				cb2.setCartId(rs2.getInt("cartId"));
				cb2.setUserId(userId);
				cb2.setQuantity(rs2.getInt("quantity"));
				TestBean tb = new TestBean();
				tb.setTestId(rs2.getInt("testId"));
				tb.setTestName(rs2.getString("testName"));
				tb.setTestPrice(rs2.getDouble("testPrice"));
				tb.setTestDescription(rs2.getString("testDesc"));
				tb.setTestPreparation(rs2.getString("testPreparation"));
				tb.setPicStream(rs2.getBinaryStream("picture"));
				tb.setPopular(rs2.getBoolean("popular"));
				tb.setTestDiscount(rs2.getDouble("discount"));
				tb.setTestDiscountedPrice(rs2.getDouble("testDiscoutedPrice"));
				cb2.setTestBean(tb);
				testlist.add(cb2);
			}
			cartWrapper.setMedicineBeans(medicinelist);
			cartWrapper.setTestBeans(testlist);
		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while getting cart details." + e);
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
			PharmacyDBConnection.releaseResource(ps2, rs2);
		}

		return cartWrapper;
	}

	public static void updateQuantity(Integer cartId, Integer userId, String action) throws PharmacyDBException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			con = PharmacyDBConnection.getInstance().getConnection();
			String sql = null;
			if (action.equals("increment")) {
				sql = "update carttbl set quantity=quantity+1,updatedOn=now() where cartid=? and userId=?";
			} else if (action.equals("decrement")) {
				sql = "update carttbl set quantity=quantity-1,updatedOn=now() where cartid=? and userId=?";
			}
			ps = con.prepareStatement(sql);
			ps.setInt(1, cartId);
			ps.setInt(2, userId);
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new PharmacyDBException("Error occured while managing quantity");
		} catch (PharmacyDBException e) {
			throw e;
		} finally {
			PharmacyDBConnection.releaseResource(ps, rs);
		}

	}
}

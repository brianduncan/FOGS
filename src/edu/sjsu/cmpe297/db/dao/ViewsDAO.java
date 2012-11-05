package edu.sjsu.cmpe297.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.sjsu.cmpe297.db.object.Views;

public class ViewsDAO implements DatabaseAccessObject<Views>{
	
	// singleton
	private static final ViewsDAO INSTANCE = new ViewsDAO();
	
	private static final String dbUrl = "jdbc:mysql://luxor.svl.ibm.com:3306/cmpe297";
	private Connection con;
	
	private static final String READ_VIEWS_CMD = "select * from views";
	private static final String READ_VIEWS_FOR_USER_CMD = READ_VIEWS_CMD + " where user_id = ?";
	private static final String READ_VIEWS_FOR_PRODUCT_CMD = READ_VIEWS_CMD + " where product_id = ?";
	private static final String READ_VIEWS_FOR_USER_AND_PRODUCT_CMD = READ_VIEWS_CMD + " where user_id = ? and product_id = ?";
	private static final String INSERT_VIEWS_CMD = "insert into views (user_id, product_id, view_count) values (?, ?, ?)";
	private static final String UPDATE_VIEWCOUNT_CMD = "update views set view_count=? where user_id=? and product_id=?";
	private static final String DELETE_VIEWS_CMD = "delete from views where user_id=? and product_id=?";

	private ViewsDAO() {
		super();
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(dbUrl, "cmpe297", "cmpe297");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized ViewsDAO getInstance() {
		return INSTANCE;
	}
	
	@Override
	public List<Views> list() throws SQLException {
		List<Views> views = new ArrayList<Views>();
		
		PreparedStatement stmt = con.prepareStatement(READ_VIEWS_CMD);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Integer userId = rs.getInt(1);
			Integer productId = rs.getInt(2);
			Integer viewCount = rs.getInt(3);
			Views v = new Views(userId, productId, viewCount);
			views.add(v);
		}
		
		rs.close();
		stmt.close();
		
		return views;
	}

	@Override
	public Views get(Views data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(READ_VIEWS_FOR_USER_AND_PRODUCT_CMD);
		stmt.setInt(1, data.getUserId());
		stmt.setInt(2, data.getProductId());
		ResultSet rs = stmt.executeQuery();
		
		rs.next();
		
		Integer userId = rs.getInt(1);
		Integer productId = rs.getInt(2);
		Integer viewCount = rs.getInt(3);
		Views v = new Views(userId, productId, viewCount);
		
		rs.close();
		stmt.close();
		
		return v;
	}
	
	public List<Views> getViewsForUser(Integer userId) throws SQLException {
		List<Views> views = new ArrayList<Views>();
		
		PreparedStatement stmt = con.prepareStatement(READ_VIEWS_FOR_USER_CMD);
		stmt.setInt(1, userId);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Integer productId = rs.getInt(2);
			Integer viewCount = rs.getInt(3);
			Views v = new Views(userId, productId, viewCount);
			views.add(v);
		}
		
		rs.close();
		stmt.close();
		
		return views;
	}
	
	public List<Views> getViewsForProduct(Integer productId) throws SQLException {
		List<Views> views = new ArrayList<Views>();
		
		PreparedStatement stmt = con.prepareStatement(READ_VIEWS_FOR_PRODUCT_CMD);
		stmt.setInt(1, productId);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Integer userId = rs.getInt(1);
			Integer viewCount = rs.getInt(3);
			Views v = new Views(userId, productId, viewCount);
			views.add(v);
		}
		
		rs.close();
		stmt.close();
		
		return views;
	}

	@Override
	public void insert(Views data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(INSERT_VIEWS_CMD);
		stmt.setInt(1, data.getUserId());
		stmt.setInt(2, data.getProductId());
		stmt.setInt(3, data.getViewCount());
		stmt.executeUpdate();
		stmt.close();
	}

	@Override
	public void update(Views oldData, Views newData) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(UPDATE_VIEWCOUNT_CMD);
		stmt.setInt(1, newData.getViewCount());
		stmt.setInt(2, oldData.getUserId());
		stmt.setInt(3, oldData.getProductId());
		stmt.executeUpdate();
		stmt.close();
	}

	@Override
	public void delete(Views data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(DELETE_VIEWS_CMD);
		stmt.setInt(1, data.getUserId());
		stmt.setInt(2, data.getProductId());
		stmt.executeUpdate();
		stmt.close();
	}

}

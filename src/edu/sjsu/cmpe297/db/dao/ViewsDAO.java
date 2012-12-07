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

public class ViewsDAO extends FogsDatabase implements DatabaseAccessObject<Views>{
	
	// singleton
	private static final ViewsDAO INSTANCE = new ViewsDAO();
	
	private static final String READ_VIEWS_CMD = "select * from views";
	private static final String READ_VIEWS_FOR_USER_CMD = READ_VIEWS_CMD + " where user_id = ?";
	private static final String READ_VIEWS_FOR_PRODUCT_CMD = READ_VIEWS_CMD + " where product_id = ?";
	private static final String READ_VIEWS_FOR_USER_AND_PRODUCT_CMD = READ_VIEWS_CMD + " where user_id = ? and product_id = ?";
	private static final String INSERT_VIEWS_CMD = "insert into views (user_id, product_id, view_count) values (?, ?, ?)";
	private static final String UPDATE_VIEWCOUNT_CMD = "update views set view_count=? where user_id=? and product_id=?";
	private static final String DELETE_VIEWS_CMD = "delete from views where user_id=? and product_id=?";
	private static final String DELETE_VIEWS_ALL_CMD = "delete from views";

	/**
	 * constructor
	 */
	private ViewsDAO() {
		super();
		
		con = getDatabaseConnection();
	}
	
	/**
	 * get singleton instance of ViewsDAO
	 * 
	 * @return ViewsDAO
	 */
	public static synchronized ViewsDAO getInstance() {
		return INSTANCE;
	}
	
	/**
	 * lists all views
	 * 
	 * @return List<Views>
	 */
	@Override
	public List<Views> list() throws SQLException {
		List<Views> views = new ArrayList<Views>();
		
		PreparedStatement stmt = con.prepareStatement(READ_VIEWS_CMD);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Long userId = rs.getLong(1);
			Long productId = rs.getLong(2);
			Long viewCount = rs.getLong(3);
			Views v = new Views(userId, productId, viewCount);
			views.add(v);
		}
		
		rs.close();
		stmt.close();
		
		return views;
	}

	/**
	 * gets specific view
	 * 
	 * @param data
	 * @return Views
	 */
	@Override
	public Views get(Views data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(READ_VIEWS_FOR_USER_AND_PRODUCT_CMD);
		stmt.setLong(1, data.getUserId());
		stmt.setLong(2, data.getProductId());
		ResultSet rs = stmt.executeQuery();
		
		Views v = null;
		
		if (rs.next()) {
			Long userId = rs.getLong(1);
			Long productId = rs.getLong(2);
			Long viewCount = rs.getLong(3);
			v = new Views(userId, productId, viewCount);
		}
		
		rs.close();
		stmt.close();
		
		return v;
	}
	
	/**
	 * gets all views for a specific user
	 * 
	 * @param userId
	 * @return List<Views>
	 * @throws SQLException
	 */
	public List<Views> getViewsForUser(Long userId) throws SQLException {
		List<Views> views = new ArrayList<Views>();
		
		PreparedStatement stmt = con.prepareStatement(READ_VIEWS_FOR_USER_CMD);
		stmt.setLong(1, userId);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Long productId = rs.getLong(2);
			Long viewCount = rs.getLong(3);
			Views v = new Views(userId, productId, viewCount);
			views.add(v);
		}
		
		rs.close();
		stmt.close();
		
		return views;
	}
	
	/**
	 * gets all views for a specific product
	 * 
	 * @param productId
	 * @return List<Views>
	 * @throws SQLException
	 */
	public List<Views> getViewsForProduct(Long productId) throws SQLException {
		List<Views> views = new ArrayList<Views>();
		
		PreparedStatement stmt = con.prepareStatement(READ_VIEWS_FOR_PRODUCT_CMD);
		stmt.setLong(1, productId);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Long userId = rs.getLong(1);
			Long viewCount = rs.getLong(3);
			Views v = new Views(userId, productId, viewCount);
			views.add(v);
		}
		
		rs.close();
		stmt.close();
		
		return views;
	}

	/**
	 * inserts new view
	 * 
	 * @param data
	 */
	@Override
	public void insert(Views data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(INSERT_VIEWS_CMD);
		stmt.setLong(1, data.getUserId());
		stmt.setLong(2, data.getProductId());
		stmt.setLong(3, data.getViewCount());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * updates existing views data
	 * 
	 * @param oldData
	 * @param newData
	 */
	@Override
	public void update(Views oldData, Views newData) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(UPDATE_VIEWCOUNT_CMD);
		stmt.setLong(1, newData.getViewCount());
		stmt.setLong(2, oldData.getUserId());
		stmt.setLong(3, oldData.getProductId());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * deletes view
	 * 
	 * @param data
	 */
	@Override
	public void delete(Views data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(DELETE_VIEWS_CMD);
		stmt.setLong(1, data.getUserId());
		stmt.setLong(2, data.getProductId());
		stmt.executeUpdate();
		stmt.close();
	}
	
	/**
	 * deletes all views
	 * 
	 */
	public void deleteAllViews() throws SQLException {
		PreparedStatement stmt = con.prepareStatement(DELETE_VIEWS_ALL_CMD);
		stmt.executeUpdate();
		stmt.close();
	}

}

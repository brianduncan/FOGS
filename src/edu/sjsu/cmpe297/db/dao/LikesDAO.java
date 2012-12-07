package edu.sjsu.cmpe297.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.sjsu.cmpe297.db.object.Company;
import edu.sjsu.cmpe297.db.object.Likes;

public class LikesDAO implements DatabaseAccessObject<Likes>{
	
	// singleton
	private static final LikesDAO INSTANCE = new LikesDAO();
	
	private static final String dbUrl = "jdbc:mysql://luxor.svl.ibm.com:3306/cmpe297";
	private Connection con;
	
	private static final String READ_LIKES_CMD = "select * from likes";
	private static final String READ_LIKES_FOR_USER_CMD = READ_LIKES_CMD + " where user_id = ?";
	private static final String READ_LIKES_FOR_PRODUCT_CMD = READ_LIKES_CMD + " where product_id = ?";
	private static final String READ_LIKES_FOR_USER_AND_PRODUCT_CMD = READ_LIKES_CMD + " where user_id = ? and product_id = ?";
	private static final String INSERT_LIKES_CMD = "insert into likes (user_id, product_id) values (?, ?)";
	private static final String DELETE_LIKES_CMD = "delete from likes where user_id=? and product_id=?";

	/**
	 * constructor
	 */
	private LikesDAO() {
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
	
	/**
	 * get singleton instance of LikesDAO
	 * 
	 * @return LikesDAO
	 */
	public static synchronized LikesDAO getInstance() {
		return INSTANCE;
	}
	
	/**
	 * lists all likes
	 * 
	 * @return List<Likes>
	 */
	@Override
	public List<Likes> list() throws SQLException {
		List<Likes> likes = new ArrayList<Likes>();
		
		PreparedStatement stmt = con.prepareStatement(READ_LIKES_CMD);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Long userId = rs.getLong(1);
			Long productId = rs.getLong(2);
			Likes l = new Likes(userId, productId);
			likes.add(l);
		}
		
		rs.close();
		stmt.close();
		
		return likes;
	}

	/**
	 * gets specific like
	 * 
	 * @param data
	 * @return Likes
	 */
	@Override
	public Likes get(Likes data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(READ_LIKES_FOR_USER_AND_PRODUCT_CMD);
		stmt.setLong(1, data.getUserId());
		stmt.setLong(2, data.getProductId());
		ResultSet rs = stmt.executeQuery();
		
		Likes l = null;
		if (rs.next()) {
			Long userId = rs.getLong(1);
			Long productId = rs.getLong(2);
			l = new Likes(userId, productId);
		}
		
		rs.close();
		stmt.close();
		
		return l;
	}
	
	/**
	 * gets all likes for a specific user
	 * 
	 * @param userId
	 * @return List<Likes>
	 * @throws SQLException
	 */
	public List<Likes> getLikesForUser(Long userId) throws SQLException {
		List<Likes> likes = new ArrayList<Likes>();
		
		PreparedStatement stmt = con.prepareStatement(READ_LIKES_FOR_USER_CMD);
		stmt.setLong(1, userId);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Long productId = rs.getLong(2);
			Likes l = new Likes(userId, productId);
			likes.add(l);
		}
		
		rs.close();
		stmt.close();
		
		return likes;
	}
	
	/**
	 * gets all likes for a specific product
	 * 
	 * @param productId
	 * @return List<Likes>
	 * @throws SQLException
	 */
	public List<Likes> getLikesForProduct(Long productId) throws SQLException {
		List<Likes> likes = new ArrayList<Likes>();
		
		PreparedStatement stmt = con.prepareStatement(READ_LIKES_FOR_PRODUCT_CMD);
		stmt.setLong(1, productId);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Long userId = rs.getLong(1);
			Likes l = new Likes(userId, productId);
			likes.add(l);
		}
		
		rs.close();
		stmt.close();
		
		return likes;
	}

	/**
	 * inserts new like
	 * 
	 * @param data
	 */
	@Override
	public void insert(Likes data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(INSERT_LIKES_CMD);
		stmt.setLong(1, data.getUserId());
		stmt.setLong(2, data.getProductId());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * deletes like
	 * 
	 * @param data
	 */
	@Override
	public void delete(Likes data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(DELETE_LIKES_CMD);
		stmt.setLong(1, data.getUserId());
		stmt.setLong(2, data.getProductId());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * updates existing likes data
	 * 
	 * @param oldData
	 * @param newData
	 */
	@Override
	public void update(Likes oldData, Likes newData) throws SQLException {
		// TODO Auto-generated method stub
	}

}

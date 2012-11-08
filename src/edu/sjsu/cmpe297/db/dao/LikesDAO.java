package edu.sjsu.cmpe297.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	
	public static synchronized LikesDAO getInstance() {
		return INSTANCE;
	}
	
	@Override
	public List<Likes> list() throws SQLException {
		List<Likes> likes = new ArrayList<Likes>();
		
		PreparedStatement stmt = con.prepareStatement(READ_LIKES_CMD);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Integer userId = rs.getInt(1);
			Integer productId = rs.getInt(2);
			Likes l = new Likes(userId, productId);
			likes.add(l);
		}
		
		rs.close();
		stmt.close();
		
		return likes;
	}

	@Override
	public Likes get(Likes data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(READ_LIKES_FOR_USER_AND_PRODUCT_CMD);
		stmt.setInt(1, data.getUserId());
		stmt.setInt(2, data.getProductId());
		ResultSet rs = stmt.executeQuery();
		
		rs.next();
		
		Integer userId = rs.getInt(1);
		Integer productId = rs.getInt(2);
		Likes l = new Likes(userId, productId);
		
		rs.close();
		stmt.close();
		
		return l;
	}
	
	public List<Likes> getLikesForUser(Integer userId) throws SQLException {
		List<Likes> likes = new ArrayList<Likes>();
		
		PreparedStatement stmt = con.prepareStatement(READ_LIKES_FOR_USER_CMD);
		stmt.setInt(1, userId);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Integer productId = rs.getInt(2);
			Likes l = new Likes(userId, productId);
			likes.add(l);
		}
		
		rs.close();
		stmt.close();
		
		return likes;
	}
	
	public List<Likes> getLikesForProduct(Integer productId) throws SQLException {
		List<Likes> likes = new ArrayList<Likes>();
		
		PreparedStatement stmt = con.prepareStatement(READ_LIKES_FOR_PRODUCT_CMD);
		stmt.setInt(1, productId);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Integer userId = rs.getInt(1);
			Likes l = new Likes(userId, productId);
			likes.add(l);
		}
		
		rs.close();
		stmt.close();
		
		return likes;
	}

	@Override
	public void insert(Likes data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(INSERT_LIKES_CMD);
		stmt.setInt(1, data.getUserId());
		stmt.setInt(2, data.getProductId());
		stmt.executeUpdate();
		stmt.close();
	}

	@Override
	public void delete(Likes data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(DELETE_LIKES_CMD);
		stmt.setInt(1, data.getUserId());
		stmt.setInt(2, data.getProductId());
		stmt.executeUpdate();
		stmt.close();
	}

	@Override
	public void update(Likes oldData, Likes newData) throws SQLException {
		// TODO Auto-generated method stub
	}

}

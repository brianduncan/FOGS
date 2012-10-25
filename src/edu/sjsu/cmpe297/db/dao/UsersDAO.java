package edu.sjsu.cmpe297.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.sjsu.cmpe297.db.object.Users;

public class UsersDAO implements DatabaseAccessObject<Users>{
	
	// singleton
	private static final UsersDAO INSTANCE = new UsersDAO();
	
	private static final String dbUrl = "jdbc:mysql://luxor.svl.ibm.com:3306/cmpe297";
	private Connection con;
	
	private static final String READ_USERS_CMD = "select * from users";
	private static final String READ_USERS_FOR_ID_CMD = READ_USERS_CMD + " where facebook_id = ?";
	private static final String INSERT_USERS_CMD = "insert into users (facebook_id, login) values (?, ?)";
	private static final String UPDATE_USERS_CMD = "update users set facebook_id=?, login=? where facebook_id=?";
	private static final String DELETE_USERS_CMD = "delete from users where facebook_id=?";

	private UsersDAO() {
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
	
	public static synchronized UsersDAO getInstance() {
		return INSTANCE;
	}
	
	@Override
	public List<Users> list() throws SQLException {
		List<Users> users = new ArrayList<Users>();
		
		PreparedStatement stmt = con.prepareStatement(READ_USERS_CMD);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Integer facebookId = rs.getInt(1);
			String login = rs.getString(2);
			Users u = new Users(facebookId, login);
			users.add(u);
		}
		
		rs.close();
		stmt.close();
		
		return users;
	}

	@Override
	public Users get(Users data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(READ_USERS_FOR_ID_CMD);
		stmt.setInt(1, data.getFacebookId());
		ResultSet rs = stmt.executeQuery();
		
		rs.next();
		
		Integer facebookId = rs.getInt(1);
		String login = rs.getString(2);
		Users u = new Users(facebookId, login);
		
		rs.close();
		stmt.close();
		
		return u;
	}

	@Override
	public void insert(Users data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(INSERT_USERS_CMD);
		stmt.setInt(1, data.getFacebookId());
		stmt.setString(2, data.getLogin());
		stmt.executeUpdate();
		stmt.close();
	}

	@Override
	public void update(Users oldData, Users newData) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(UPDATE_USERS_CMD);
		stmt.setInt(1, newData.getFacebookId());
		stmt.setString(2, newData.getLogin());
		stmt.setInt(3, oldData.getFacebookId());
		stmt.executeUpdate();
		stmt.close();
	}

	@Override
	public void delete(Users data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(DELETE_USERS_CMD);
		stmt.setInt(1, data.getFacebookId());
		stmt.executeUpdate();
		stmt.close();
	}

}

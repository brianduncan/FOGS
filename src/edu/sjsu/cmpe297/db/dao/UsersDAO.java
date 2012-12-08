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

public class UsersDAO extends FogsDatabase implements DatabaseAccessObject<Users>{
	
	// singleton
	private static final UsersDAO INSTANCE = new UsersDAO();
	
	private static final String READ_USERS_CMD = "select * from users";
	private static final String READ_USERS_FOR_ID_CMD = READ_USERS_CMD + " where facebook_id = ?";
	private static final String INSERT_USERS_CMD = "insert into users (facebook_id, name) values (?, ?)";
	private static final String UPDATE_USERS_CMD = "update users set facebook_id=?, name=? where facebook_id=?";
	private static final String DELETE_USERS_CMD = "delete from users where facebook_id=?";

	/**
	 * constructor
	 */
	private UsersDAO() {
		super();
		
		con = getDatabaseConnection();
	}
	
	/**
	 * get singleton instance of UsersDAO
	 * 
	 * @return UsersDAO
	 */
	public static synchronized UsersDAO getInstance() {
		return INSTANCE;
	}
	
	/**
	 * lists all users
	 * 
	 * @return List<Users>
	 */
	@Override
	public List<Users> list() throws SQLException {
		List<Users> users = new ArrayList<Users>();
		
		PreparedStatement stmt = con.prepareStatement(READ_USERS_CMD);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Long facebookId = rs.getLong(1);
			String name = rs.getString(2);
			Users u = new Users(facebookId, name);
			users.add(u);
		}
		
		rs.close();
		stmt.close();
		
		return users;
	}

	/**
	 * gets specific user
	 * 
	 * @param data
	 * @return Users
	 */
	@Override
	public Users get(Users data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(READ_USERS_FOR_ID_CMD);
		stmt.setLong(1, data.getFacebookId());
		ResultSet rs = stmt.executeQuery();
		
		Users u = null;
		
		if (rs.next()) {		
			Long facebookId = rs.getLong(1);
			String name = rs.getString(2);
			u = new Users(facebookId, name);
		}
		
		rs.close();
		stmt.close();
		
		return u;
	}

	/**
	 * inserts new user
	 * 
	 * @param data
	 */
	@Override
	public void insert(Users data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(INSERT_USERS_CMD);
		stmt.setLong(1, data.getFacebookId());
		stmt.setString(2, data.getLogin());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * updates existing users data
	 * 
	 * @param oldData
	 * @param newData
	 */
	@Override
	public void update(Users oldData, Users newData) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(UPDATE_USERS_CMD);
		stmt.setLong(1, newData.getFacebookId());
		stmt.setString(2, newData.getLogin());
		stmt.setLong(3, oldData.getFacebookId());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * deletes user
	 * 
	 * @param data
	 */
	@Override
	public void delete(Users data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(DELETE_USERS_CMD);
		stmt.setLong(1, data.getFacebookId());
		stmt.executeUpdate();
		stmt.close();
	}

}

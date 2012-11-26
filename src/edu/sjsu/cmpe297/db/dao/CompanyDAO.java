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

public class CompanyDAO implements DatabaseAccessObject<Company>{
	
	// singleton
	private static final CompanyDAO INSTANCE = new CompanyDAO();
	
	private static final String dbUrl = "jdbc:mysql://luxor.svl.ibm.com:3306/cmpe297";
	private Connection con;
	
	private static final String READ_COMPANY_CMD = "select * from company";
	private static final String READ_COMPANY_FOR_ID_CMD = READ_COMPANY_CMD + " where facebook_id = ?";
	private static final String INSERT_COMPANY_CMD = "insert into company (facebook_id, name) values (?, ?)";
	private static final String UPDATE_COMPANY_CMD = "update company set facebook_id=?, name=? where facebook_id=?";
	private static final String DELETE_COMPANY_CMD = "delete from company where facebook_id=?";

	private CompanyDAO() {
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
	
	public static synchronized CompanyDAO getInstance() {
		return INSTANCE;
	}
	
	@Override
	public List<Company> list() throws SQLException {
		List<Company> companies = new ArrayList<Company>();
		
		PreparedStatement stmt = con.prepareStatement(READ_COMPANY_CMD);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Long facebookId = rs.getLong(1);
			String name = rs.getString(2);
			Company c = new Company(facebookId, name);
			companies.add(c);
		}
		
		rs.close();
		stmt.close();
		
		return companies;
	}

	@Override
	public Company get(Company data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(READ_COMPANY_FOR_ID_CMD);
		stmt.setLong(1, data.getFacebookId());
		ResultSet rs = stmt.executeQuery();
		
		rs.next();
		
		Long facebookId = rs.getLong(1);
		String name = rs.getString(2);
		Company c = new Company(facebookId, name);
		
		rs.close();
		stmt.close();
		
		return c;
	}

	@Override
	public void insert(Company data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(INSERT_COMPANY_CMD);
		stmt.setLong(1, data.getFacebookId());
		stmt.setString(2, data.getName());
		stmt.executeUpdate();
		stmt.close(); 
	}

	@Override
	public void update(Company oldData, Company newData) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(UPDATE_COMPANY_CMD);
		stmt.setLong(1, newData.getFacebookId());
		stmt.setString(2, newData.getName());
		stmt.setLong(3, oldData.getFacebookId());
		stmt.executeUpdate();
		stmt.close();
	}

	@Override
	public void delete(Company data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(DELETE_COMPANY_CMD);
		stmt.setLong(1, data.getFacebookId());
		stmt.executeUpdate();
		stmt.close();
	}

}

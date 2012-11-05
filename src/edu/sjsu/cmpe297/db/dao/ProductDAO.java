package edu.sjsu.cmpe297.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.sjsu.cmpe297.db.object.Product;

public class ProductDAO implements DatabaseAccessObject<Product>{
	
	// singleton
	private static final ProductDAO INSTANCE = new ProductDAO();
	
	private static final String dbUrl = "jdbc:mysql://luxor.svl.ibm.com:3306/cmpe297";
	private Connection con;
	
	private static final String READ_PRODUCT_CMD = "select * from product";
	private static final String READ_PRODUCT_FOR_ID_CMD = READ_PRODUCT_CMD + " where facebook_id = ?";
	private static final String READ_PRODUCT_FOR_COMPANY_CMD = READ_PRODUCT_CMD + " where company_id = ?";
	private static final String INSERT_PRODUCT_CMD = "insert into product (facebook_id, name, company_id) values (?, ?, ?)";
	private static final String UPDATE_PRODUCT_CMD = "update product set facebook_id=?, name=?, company_id=? where facebook_id=?";
	private static final String DELETE_PRODUCT_CMD = "delete from product where facebook_id=?";

	private ProductDAO() {
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
	
	public static synchronized ProductDAO getInstance() {
		return INSTANCE;
	}
	
	@Override
	public List<Product> list() throws SQLException {
		List<Product> products = new ArrayList<Product>();
		
		PreparedStatement stmt = con.prepareStatement(READ_PRODUCT_CMD);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Integer facebookId = rs.getInt(1);
			String name = rs.getString(2);
			Integer companyId = rs.getInt(3);
			Product p = new Product(facebookId, name, companyId);
			products.add(p);
		}
		
		rs.close();
		stmt.close();
		
		return products;
	}

	@Override
	public Product get(Product data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(READ_PRODUCT_FOR_ID_CMD);
		stmt.setInt(1, data.getFacebookId());
		ResultSet rs = stmt.executeQuery();
		
		rs.next();
		
		Integer facebookId = rs.getInt(1);
		String name = rs.getString(2);
		Integer companyId = rs.getInt(3);
		Product p = new Product(facebookId, name, companyId);
		
		rs.close();
		stmt.close();
		
		return p;
	}
	
	public List<Product> getProductsForCompany(Integer companyId) throws SQLException {
		List<Product> products = new ArrayList<Product>();
		
		PreparedStatement stmt = con.prepareStatement(READ_PRODUCT_FOR_COMPANY_CMD);
		stmt.setInt(1, companyId);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Integer facebookId = rs.getInt(1);
			String name = rs.getString(2);
			Product p = new Product(facebookId, name, companyId);
			products.add(p);
		}
		
		rs.close();
		stmt.close();
		
		return products;
	}

	@Override
	public void insert(Product data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(INSERT_PRODUCT_CMD);
		stmt.setInt(1, data.getFacebookId());
		stmt.setString(2, data.getName());
		stmt.setInt(3, data.getCompanyId());
		stmt.executeUpdate();
		stmt.close();
	}

	@Override
	public void update(Product oldData, Product newData) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(UPDATE_PRODUCT_CMD);
		stmt.setInt(1, newData.getFacebookId());
		stmt.setString(2, newData.getName());
		stmt.setInt(3, newData.getCompanyId());
		stmt.setInt(4, oldData.getFacebookId());
		stmt.executeUpdate();
		stmt.close();
	}

	@Override
	public void delete(Product data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(DELETE_PRODUCT_CMD);
		stmt.setInt(1, data.getFacebookId());
		stmt.executeUpdate();
		stmt.close();
	}

}

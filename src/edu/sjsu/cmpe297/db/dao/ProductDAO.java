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

public class ProductDAO extends FogsDatabase implements DatabaseAccessObject<Product>{
	
	// singleton
	private static final ProductDAO INSTANCE = new ProductDAO();
	
	private static final String READ_PRODUCT_CMD = "select * from product";
	private static final String READ_PRODUCT_FOR_ID_CMD = READ_PRODUCT_CMD + " where facebook_id = ?";
	private static final String READ_PRODUCT_FOR_COMPANY_CMD = READ_PRODUCT_CMD + " where company_id = ?";
	private static final String INSERT_PRODUCT_CMD = "insert into product (facebook_id, name, company_id) values (?, ?, ?)";
	private static final String UPDATE_PRODUCT_CMD = "update product set facebook_id=?, name=?, company_id=? where facebook_id=?";
	private static final String DELETE_PRODUCT_CMD = "delete from product where facebook_id=?";

	/**
	 * constructor
	 */
	private ProductDAO() {
		super();
		
        con = getDatabaseConnection();
	}
	
	/**
	 * get singleton instance of ProductDAO
	 * 
	 * @return ProductDAO
	 */
	public static synchronized ProductDAO getInstance() {
		return INSTANCE;
	}
	
	/**
	 * lists all products
	 * 
	 * @return List<Product>
	 */
	@Override
	public List<Product> list() throws SQLException {
		List<Product> products = new ArrayList<Product>();
		
		PreparedStatement stmt = con.prepareStatement(READ_PRODUCT_CMD);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Long facebookId = rs.getLong(1);
			String name = rs.getString(2);
			Long companyId = rs.getLong(3);
			Product p = new Product(facebookId, name, companyId);
			products.add(p);
		}
		
		rs.close();
		stmt.close();
		
		return products;
	}

	/**
	 * gets specific product
	 * 
	 * @param data
	 * @return Product
	 */
	@Override
	public Product get(Product data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(READ_PRODUCT_FOR_ID_CMD);
		stmt.setLong(1, data.getFacebookId());
		ResultSet rs = stmt.executeQuery();
		
		Product p = null;
		
		if (rs.next()) {
			Long facebookId = rs.getLong(1);
			String name = rs.getString(2);
			Long companyId = rs.getLong(3);
			p = new Product(facebookId, name, companyId);
		}
		
		rs.close();
		stmt.close();
		
		return p;
	}
	
	/**
	 * gets all products for a specific company
	 * 
	 * @param companyId
	 * @return List<Product>
	 * @throws SQLException
	 */
	public List<Product> getProductsForCompany(Long companyId) throws SQLException {
		List<Product> products = new ArrayList<Product>();
		
		PreparedStatement stmt = con.prepareStatement(READ_PRODUCT_FOR_COMPANY_CMD);
		stmt.setLong(1, companyId);
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			Long facebookId = rs.getLong(1);
			String name = rs.getString(2);
			Product p = new Product(facebookId, name, companyId);
			products.add(p);
		}
		
		rs.close();
		stmt.close();
		
		return products;
	}

	/**
	 * inserts new product
	 * 
	 * @param data
	 */
	@Override
	public void insert(Product data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(INSERT_PRODUCT_CMD);
		stmt.setLong(1, data.getFacebookId());
		stmt.setString(2, data.getName());
		stmt.setLong(3, data.getCompanyId());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * updates existing product data
	 * 
	 * @param oldData
	 * @param newData
	 */
	@Override
	public void update(Product oldData, Product newData) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(UPDATE_PRODUCT_CMD);
		stmt.setLong(1, newData.getFacebookId());
		stmt.setString(2, newData.getName());
		stmt.setLong(3, newData.getCompanyId());
		stmt.setLong(4, oldData.getFacebookId());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * deletes product
	 * 
	 * @param data
	 */
	@Override
	public void delete(Product data) throws SQLException {
		PreparedStatement stmt = con.prepareStatement(DELETE_PRODUCT_CMD);
		stmt.setLong(1, data.getFacebookId());
		stmt.executeUpdate();
		stmt.close();
	}

}

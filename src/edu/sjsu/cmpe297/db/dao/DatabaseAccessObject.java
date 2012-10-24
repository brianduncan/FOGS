package edu.sjsu.cmpe297.db.dao;

/**
 * 
 * @author michael yu
 * 
 * @param <T>
 * 				The database object class that will be used by the class that implements this dao interface
 *
 */

import java.sql.SQLException;
import java.util.List;

import edu.sjsu.cmpe297.db.object.DatabaseObject;

public interface DatabaseAccessObject <T extends DatabaseObject>{
	
	/**
	 * 
	 * @return List of database objects
	 * @throws SQLException
	 */
	List<T> list() throws SQLException;
	
	/**
	 * 
	 * @param id
	 * 				The id (primary key) of the database object to get
	 * @return The database object
	 * @throws SQLException
	 */
	T get(Integer id) throws SQLException;
	
	/**
	 * 
	 * @param data
	 * 				The database object to insert
	 * @throws SQLException
	 */
	void insert(T data) throws SQLException;
	
	/**
	 * 
	 * @param id
	 * 				The id (primary key) of the database object to update
	 * @param data
	 * 				The new database object to update to
	 * @throws SQLException
	 */
	void update(Integer id, T data) throws SQLException;
	
	/**
	 * 
	 * @param id
	 * 				The id (primary key) of the database object to delete
	 * @throws SQLException
	 */
	void delete(Integer id) throws SQLException;
}

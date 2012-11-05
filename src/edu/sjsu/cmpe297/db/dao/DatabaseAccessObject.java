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
	 * @param data
	 * 				The database object to get (query by its primary key)
	 * @return The database object
	 * @throws SQLException
	 */
	T get(T data) throws SQLException;
	
	/**
	 * 
	 * @param data
	 * 				The database object to insert
	 * @throws SQLException
	 */
	void insert(T data) throws SQLException;
	
	/**
	 * 
	 * @param oldData
	 * 				The old database object to update (query by its primary key)
	 * @param newData
	 * 				The new database object to update to
	 * @throws SQLException
	 */
	void update(T oldData, T newData) throws SQLException;
	
	/**
	 * 
	 * @param data
	 * 				The database object to delete (query by its primary key)
	 * @throws SQLException
	 */
	void delete(T data) throws SQLException;
}

/**
 * 
 */
package edu.sjsu.cmpe297;

import java.sql.SQLException;

import edu.sjsu.cmpe297.db.dao.ProductDAO;
import edu.sjsu.cmpe297.db.dao.UsersDAO;
import edu.sjsu.cmpe297.db.object.Product;
import edu.sjsu.cmpe297.db.object.Users;

/**
 * @author rpriyad
 *
 */
public class FogsCSHelper {
	
	//Check if this product is present in product table and add the product
	  public static String checkAndAddProduct(Long prodId, String prodName, Long compId ){
		  String ret = "SUCCESS";
		  ProductDAO pdao = ProductDAO.getInstance();
		  
		  Product prod = new  Product(prodId, prodName, compId);
		  @SuppressWarnings("unused")
		  Product prod1 = new  Product(prodId, prodName, compId);
		  
			try {
				prod1 = pdao.get(prod);
			} catch (SQLException e) {
				//Prod does not exist in prod table, so add the prod
				e.printStackTrace();
				
				try {
					pdao.insert(prod);
				} catch (SQLException e1) {
					ret = "ERROR";
					e1.printStackTrace();
				}
			}
		  
		  return ret;
	  }
	  
	  
	//Check if this user is present in users table and add the user
	  public static String checkAndAddUser(Long fbId, String name){
		  
		  String ret = "SUCCESS"; 
		  
			  
			  UsersDAO udao = UsersDAO.getInstance();
			  Users user = new Users(fbId, name);
			  @SuppressWarnings("unused")
			Users user1 = new Users(fbId, name);
			  try {
				user1 = udao.get(user);
			} catch (SQLException e) {			
				e.printStackTrace();
				//User does not exist in user table, so add the user
				try {
					udao.insert(user);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					ret = "ERROR";
				}
			}
		  
		  return ret;	  
	  }
	  
	//Check if the string passed is an Long number
	  public static boolean isNumeric(String str)  
	  {  
	    try  
	    {  
	        
	    	Long.parseLong(str);  
	    }  
	    catch(NumberFormatException nfe)  
	    {  
	      return false;  
	    }  
	    return true;  
	  }
	

}

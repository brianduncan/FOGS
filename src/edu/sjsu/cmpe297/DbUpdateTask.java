package edu.sjsu.cmpe297;

import java.sql.SQLException;

import edu.sjsu.cmpe297.db.dao.*;
import edu.sjsu.cmpe297.db.object.*;

public class DbUpdateTask {
	
	private static CompanyDAO companyDb = CompanyDAO.getInstance();
	private static ProductDAO productDb = ProductDAO.getInstance();
	private static UsersDAO usersDb = UsersDAO.getInstance();
	private static ViewsDAO viewsDb = ViewsDAO.getInstance();
	
	public static void updateCompany(Long facebookId, String name) {
		Company c = new Company(facebookId, name);
		
		try {
			Company currentCompany = companyDb.get(c);
			
			if (currentCompany == null) {
				//does not exist in company table, insert
				companyDb.insert(c);
				System.out.println("DbUpdateTask.java: updateCompany(): successfully inserted company with facebookId=" + facebookId + ", name=" + name);
			}
			else {
				//exists in company table, update
				companyDb.update(currentCompany, c);
				System.out.println("DbUpdateTask.java: updateCompany(): successfully updated company to now have facebookId=" + facebookId + ", name=" + name);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteCompany(Long facebookId, String name) {
		Company c = new Company(facebookId, name);
		
		try {
			companyDb.delete(c);
			System.out.println("DbUpdateTask.java: deleteCompany(): successfully deleted company with facebookId=" + facebookId + ", name=" + name);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateProduct(Long facebookId, String name, Long companyId) {
		Product p = new Product(facebookId, name, companyId);
		
		try {
			Product currentProduct = productDb.get(p);
			
			if (currentProduct == null) {
				//does not exist in product table, insert
				productDb.insert(p);
				System.out.println("DbUpdateTask.java: updateProduct(): successfully inserted product with facebookId=" + facebookId + ", name=" + name + ", companyId=" + companyId);
			}
			else {
				//exists in product table, update
				productDb.update(currentProduct, p);
				System.out.println("DbUpdateTask.java: updateProduct(): successfully updated product to now have facebookId=" + facebookId + ", name=" + name + ", companyId=" + companyId);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteProduct(Long facebookId, String name, Long companyId) {
		Product p = new Product(facebookId, name, companyId);
		
		try {
			productDb.delete(p);
			System.out.println("DbUpdateTask.java: deleteProduct(): successfully deleted product with facebookId=" + facebookId + ", name=" + name + ", companyId=" + companyId);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateUser(Long facebookId, String name) {
		Users u = new Users(facebookId, name);
		
		try {
			Users currentUser = usersDb.get(u);
			
			if (currentUser == null) {
				//does not exist in users table, insert
				usersDb.insert(u);
				System.out.println("DbUpdateTask.java: updateUser(): successfully inserted user with facebookId=" + facebookId + ", name=" + name);
			}
			else {
				//exists in users table, update
				usersDb.update(currentUser, u);
				System.out.println("DbUpdateTask.java: updateUser(): successfully updated user to now have facebookId=" + facebookId + ", name=" + name);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteUser(Long facebookId, String name) {
		Users u = new Users(facebookId, name);
		
		try {
			usersDb.delete(u);
			System.out.println("DbUpdateTask.java: deleteUser(): successfully deleted user with facebookId=" + facebookId + ", name=" + name);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateView(Long userId, Long productId) {
		Views v = new Views(userId, productId, new Long(1));
		
		try {
			Views currentView = viewsDb.get(v);
			
			if (currentView == null) {
				//does not exist in views table, insert
				viewsDb.insert(v);
				System.out.println("DbUpdateTask.java: updateView(): successfully inserted view with userId=" + userId + ", productId=" + productId + ", viewCount=1");
			}
			else {
				//exists in views table, update
				long count = currentView.getViewCount().longValue() + 1;
				v.setViewCount(new Long(count));
				viewsDb.update(currentView, v);
				System.out.println("DbUpdateTask.java: updateView(): successfully updated view to now have userId=" + userId + ", productId=" + productId + ", viewCount=" + count);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteView(Long userId, Long productId) {
		Views v = new Views(userId, productId, new Long(1));
		
		try {
			viewsDb.delete(v);
			System.out.println("DbUpdateTask.java: deleteView(): successfully deleted view with userId=" + userId + ", productId=" + productId);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteAllViews() {
		try {
			viewsDb.deleteAllViews();
			System.out.println("DbUpdateTask.java: deleteView(): successfully deleted all views");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Long mcdonaldsId = new Long("50245567013");
		Long bigMacId = new Long("36062351276");
		Long mcNuggetsId = new Long("151622314163");
		Long friesId = new Long("336725238500");
		
		Long starbucksId = new Long("22092443056");
		Long caramelMacId = new Long("36667706910");
		Long caramelFrapId = new Long("214209185534");
		
		Long michaelId = new Long("3300077");
		Long richardId = new Long("607852190");
		Long rahulId = new Long("1557647490");
		
		//---------------------------------------------------------------------------------
		//- Registering a new company to use our FogsCustomerService
		//---------------------------------------------------------------------------------
		
		updateCompany(mcdonaldsId, "McDonald's");
		
		
		//---------------------------------------------------------------------------------
		//- Adding its products
		//---------------------------------------------------------------------------------
		
		updateProduct(bigMacId, "Big Mac", mcdonaldsId);
		updateProduct(mcNuggetsId, "Chicken McNuggets", mcdonaldsId);
		updateProduct(friesId, "French Fries", mcdonaldsId);
		
		
		//---------------------------------------------------------------------------------
		//- Registering Facebook users to our FogsCustomerService
		//---------------------------------------------------------------------------------
		
		updateUser(michaelId, "Michael Yu");
		
		
		//---------------------------------------------------------------------------------
		//- MANUAL population of views (i.e. which Facebook user viewed which product)
		//- TODO: in the future, need to support some sort of mechanism for tracking views
		//---------------------------------------------------------------------------------
		
		//views of mcdonald's products
		for (int count = 0; count < 10; count++) {
			updateView(michaelId, bigMacId);
			updateView(michaelId, mcNuggetsId);
			updateView(michaelId, friesId);
			updateView(richardId, bigMacId);
			updateView(richardId, mcNuggetsId);
			updateView(richardId, friesId);
			updateView(rahulId, bigMacId);
			updateView(rahulId, mcNuggetsId);
			updateView(rahulId, friesId);
		}
		
		//views of starbucks' products
		for (int count = 0; count < 5; count++) {
			updateView(michaelId, caramelMacId);
			updateView(michaelId, caramelFrapId);
			updateView(richardId, caramelMacId);
			updateView(richardId, caramelFrapId);
			updateView(rahulId, caramelMacId);
			updateView(rahulId, caramelFrapId);
		}
		
		
		//---------------------------------------------------------------------------------
		//- Cleanup views
		//---------------------------------------------------------------------------------
		/*
		deleteAllViews();
		*/
		
		//---------------------------------------------------------------------------------
		//- Cleanup users
		//--------------------------------------------------------------------------------
		/*
		deleteUser(michaelId, "Michael Yu");
		*/
		
		//---------------------------------------------------------------------------------
		//- Cleanup products
		//---------------------------------------------------------------------------------
		/*
		deleteProduct(bigMacId, "Big Mac", mcdonaldsId);
		deleteProduct(mcNuggetsId, "Chicken McNuggets", mcdonaldsId);
		deleteProduct(friesId, "French Fries", mcdonaldsId);
		*/
		
		//---------------------------------------------------------------------------------
		//- Cleanup companies
		//---------------------------------------------------------------------------------
		/*
		deleteCompany(mcdonaldsId, "McDonald's");
		*/
		
		
	}
}

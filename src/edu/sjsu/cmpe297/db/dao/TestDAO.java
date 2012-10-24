package edu.sjsu.cmpe297.db.dao;

import java.sql.SQLException;
import java.util.List;

import edu.sjsu.cmpe297.db.object.Company;

public class TestDAO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CompanyDAO companyDb = CompanyDAO.getInstance();
		
		try {
			//test insert
			//Company c = new Company(2, "tester");
			//companyDb.insert(c);
			
			//test get
			//Company c = companyDb.get(2);
			//System.out.println(c.getFacebookId() + " " + c.getName());
			
			//test update
			//Company cToUpdate = new Company(99,"mike_company");
			//companyDb.update(3, cToUpdate);
			
			//test delete
			companyDb.delete(99);
			
			//test list
			List<Company> companies = companyDb.list();
			for (Company c : companies) {
				System.out.println(c.getFacebookId() + " " + c.getName());
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

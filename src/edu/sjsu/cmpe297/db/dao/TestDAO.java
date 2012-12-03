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
			Company c = new Company(new Long(2), "tester company");
			companyDb.insert(c);
			
			/*
			//test get
			Company cToGet = companyDb.get(c);
			System.out.println(cToGet.getFacebookId() + " " + cToGet.getName());
			
			//test update
			Company cToUpdate = new Company(new Long(99),"mike_company");
			companyDb.update(cToGet, cToUpdate);
			
			//test delete
			companyDb.delete(cToUpdate);
			
			//test list
			List<Company> companies = companyDb.list();
			for (Company company : companies) {
				System.out.println(company.getFacebookId() + " " + company.getName());
			}
			*/
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

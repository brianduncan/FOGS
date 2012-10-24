package edu.sjsu.cmpe297.db.object;

public class Product implements DatabaseObject {

	// member variables
	private Integer facebookId; //TODO: choose Long because there are a lot of facebook ids out there
	private String name;
	private Integer companyId;
	
	public Product(Integer facebookId, String name, Integer companyId) {
		this.facebookId = facebookId;
		this.name = name;
		this.companyId = companyId;
	}
	
	// accessor methods
	public Integer getFacebookId() {
		return facebookId;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}
	
	public void setFacebookId(Integer facebookId) {
		this.facebookId = facebookId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
}

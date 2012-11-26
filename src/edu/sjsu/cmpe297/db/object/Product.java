package edu.sjsu.cmpe297.db.object;

public class Product implements DatabaseObject {

	// member variables
	private Long facebookId; //TODO: choose Long because there are a lot of facebook ids out there
	private String name;
	private Long companyId;
	
	public Product(Long facebookId, String name, Long companyId) {
		this.facebookId = facebookId;
		this.name = name;
		this.companyId = companyId;
	}
	
	// accessor methods
	public Long getFacebookId() {
		return facebookId;
	}
	
	public String getName() {
		return name;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setFacebookId(Long facebookId) {
		this.facebookId = facebookId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
}

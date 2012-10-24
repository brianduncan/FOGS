package edu.sjsu.cmpe297.db.object;

public class Company implements DatabaseObject {

	// member variables
	private Integer facebookId; //TODO: choose Long because there are a lot of facebook ids out there
	private String name;
	
	public Company(Integer facebookId, String name) {
		this.facebookId = facebookId;
		this.name = name;
	}
	
	// accessor methods
	public Integer getFacebookId() {
		return facebookId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setFacebookId(Integer facebookId) {
		this.facebookId = facebookId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}

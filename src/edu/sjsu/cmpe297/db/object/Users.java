package edu.sjsu.cmpe297.db.object;

public class Users implements DatabaseObject {

	// member variables
	private Integer facebookId; //TODO: choose Long because there are a lot of facebook ids out there
	private String name;
	
	public Users(Integer facebookId, String name) {
		this.facebookId = facebookId;
		this.name = name;
	}
	
	// accessor methods
	public Integer getFacebookId() {
		return facebookId;
	}
	
	public String getLogin() {
		return name;
	}
	
	public void setFacebookId(Integer facebookId) {
		this.facebookId = facebookId;
	}
	
	public void setLogin(String name) {
		this.name = name;
	}
	
}

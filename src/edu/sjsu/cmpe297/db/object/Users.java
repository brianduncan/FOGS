package edu.sjsu.cmpe297.db.object;

public class Users implements DatabaseObject {

	// member variables
	private Long facebookId; //TODO: choose Long because there are a lot of facebook ids out there
	private String name;
	
	public Users(Long facebookId, String name) {
		this.facebookId = facebookId;
		this.name = name;
	}
	
	// accessor methods
	public Long getFacebookId() {
		return facebookId;
	}
	
	public String getLogin() {
		return name;
	}
	
	public void setFacebookId(Long facebookId) {
		this.facebookId = facebookId;
	}
	
	public void setLogin(String name) {
		this.name = name;
	}
	
}

package edu.sjsu.cmpe297.db.object;

public class Users implements DatabaseObject {

	// member variables
	private Integer facebookId; //TODO: choose Long because there are a lot of facebook ids out there
	private String login;
	
	public Users(Integer facebookId, String login) {
		this.facebookId = facebookId;
		this.login = login;
	}
	
	// accessor methods
	public Integer getFacebookId() {
		return facebookId;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setFacebookId(Integer facebookId) {
		this.facebookId = facebookId;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
}

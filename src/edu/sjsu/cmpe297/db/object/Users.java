package edu.sjsu.cmpe297.db.object;

public class Users implements DatabaseObject {

	//-------------------------------------------
	// member variables
	//-------------------------------------------
	private Long facebookId; //choose Long because there are a lot of facebook ids out there
	private String name;
	
	/**
	 * constructor
	 * 
	 * @param facebookId
	 * @param name
	 */
	public Users(Long facebookId, String name) {
		this.facebookId = facebookId;
		this.name = name;
	}
	
	//-------------------------------------------
	// accessor methods
	//-------------------------------------------

	/**
	 * gets the facebookId for the user
	 * 
	 * @return Long
	 */
	public Long getFacebookId() {
		return facebookId;
	}
	
	/**
	 * gets the name of the user
	 * 
	 * @return String
	 */
	public String getLogin() {
		return name;
	}
	
	/**
	 * sets the facebookId for the user
	 * 
	 * @param facebookId
	 */
	public void setFacebookId(Long facebookId) {
		this.facebookId = facebookId;
	}
	
	/**
	 * sets the name for the user
	 * 
	 * @param name
	 */
	public void setLogin(String name) {
		this.name = name;
	}
	
}

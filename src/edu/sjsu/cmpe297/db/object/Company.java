package edu.sjsu.cmpe297.db.object;

public class Company implements DatabaseObject {

	//-------------------------------------------
	// member variables
	//-------------------------------------------
	private Long facebookId; //choose Long because there are a lot of facebook ids out there
	private String name;
	
	/**
	 * constructor
	 * 
	 * @param facebookId (of the company)
	 * @param name (of the company)
	 */
	public Company(Long facebookId, String name) {
		this.facebookId = facebookId;
		this.name = name;
	}
	
	//-------------------------------------------
	// accessor methods
	//-------------------------------------------
	
	/**
	 * gets the facebookId of the company
	 * 
	 * @return Long
	 */
	public Long getFacebookId() {
		return facebookId;
	}
	
	/**
	 * gets the name of the company
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * sets the facebookId of the company
	 * 
	 * @param facebookId
	 */
	public void setFacebookId(Long facebookId) {
		this.facebookId = facebookId;
	}
	
	/**
	 * sets the name of the company
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}

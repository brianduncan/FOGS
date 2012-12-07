package edu.sjsu.cmpe297.db.object;

public class Product implements DatabaseObject {

	//-------------------------------------------
	// member variables
	//-------------------------------------------
	private Long facebookId; //choose Long because there are a lot of facebook ids out there
	private String name;
	private Long companyId;
	
	/**
	 * constructor
	 * 
	 * @param facebookId
	 * @param name
	 * @param companyId
	 */
	public Product(Long facebookId, String name, Long companyId) {
		this.facebookId = facebookId;
		this.name = name;
		this.companyId = companyId;
	}
	
	//-------------------------------------------
	// accessor methods
	//-------------------------------------------
	
	/**
	 * gets the facebookId for this product
	 * 
	 * @return Long
	 */
	public Long getFacebookId() {
		return facebookId;
	}
	
	/**
	 * gets the name for this product
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * gets the companyId (i.e. facebookId of the company) for this product
	 * 
	 * @return Long
	 */
	public Long getCompanyId() {
		return companyId;
	}
	
	/**
	 * sets the facebookId for this product
	 * 
	 * @param facebookId
	 */
	public void setFacebookId(Long facebookId) {
		this.facebookId = facebookId;
	}
	
	/**
	 * sets the name for this product
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * sets the companyId for this product
	 * 
	 * @param companyId
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
}

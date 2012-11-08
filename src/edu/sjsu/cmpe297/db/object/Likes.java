package edu.sjsu.cmpe297.db.object;

public class Likes implements DatabaseObject{
	// member variables
	private Integer userId;
	private Integer productId;
	
	public Likes(Integer userId, Integer productId) {
		this.userId = userId;
		this.productId = productId;
	}
	
	// accessor methods
	public Integer getUserId() {
		return userId;
	}
	
	public Integer getProductId() {
		return productId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}

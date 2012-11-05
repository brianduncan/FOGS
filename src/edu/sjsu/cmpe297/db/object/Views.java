package edu.sjsu.cmpe297.db.object;

public class Views implements DatabaseObject {

	// member variables
	private Integer userId;
	private Integer productId;
	private Integer viewCount;
	
	public Views(Integer userId, Integer productId, Integer viewCount) {
		this.userId = userId;
		this.productId = productId;
		this.viewCount = viewCount;
	}
	
	// accessor methods
	public Integer getUserId() {
		return userId;
	}
	
	public Integer getProductId() {
		return productId;
	}
	
	public Integer getViewCount() {
		return viewCount;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	
}

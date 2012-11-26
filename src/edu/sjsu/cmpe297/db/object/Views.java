package edu.sjsu.cmpe297.db.object;

public class Views implements DatabaseObject {

	// member variables
	private Long userId;
	private Long productId;
	private Long viewCount;
	
	public Views(Long userId, Long productId, Long viewCount) {
		this.userId = userId;
		this.productId = productId;
		this.viewCount = viewCount;
	}
	
	// accessor methods
	public Long getUserId() {
		return userId;
	}
	
	public Long getProductId() {
		return productId;
	}
	
	public Long getViewCount() {
		return viewCount;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}
	
	public void incrementViewCount() {
		viewCount++;
	}
	
}

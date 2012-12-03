package edu.sjsu.cmpe297.db.object;

public class Likes implements DatabaseObject{
	// member variables
	private Long userId;
	private Long productId;
	
	public Likes(Long userId, Long productId) {
		this.userId = userId;
		this.productId = productId;
	}
	
	// accessor methods
	public Long getUserId() {
		return userId;
	}
	
	public Long getProductId() {
		return productId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		Likes like = (Likes)obj;

		if(like.getUserId().equals(userId) && like.getProductId().equals(productId))
		{
			return true;
		}

		return false;
	}
}

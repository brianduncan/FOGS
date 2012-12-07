package edu.sjsu.cmpe297.db.object;

public class Likes implements DatabaseObject{
	//-------------------------------------------
	// member variables
	//-------------------------------------------
	private Long userId;
	private Long productId;
	
	/**
	 * constructor
	 * 
	 * @param userId
	 * @param productId
	 */
	public Likes(Long userId, Long productId) {
		this.userId = userId;
		this.productId = productId;
	}
	
	//-------------------------------------------
	// accessor methods
	//-------------------------------------------
	
	/**
	 * gets the userId for this like
	 * 
	 * @return Long
	 */
	public Long getUserId() {
		return userId;
	}
	
	/**
	 * gets the productId for this like
	 * 
	 * @return Long
	 */
	public Long getProductId() {
		return productId;
	}
	
	/**
	 * sets the userId for this like
	 * 
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	/**
	 * sets the productId for this like
	 * 
	 * @param productId
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	/**
	 * equals comparison for likes
	 * 
	 * @param obj
	 * @return boolean
	 */
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

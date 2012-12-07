package edu.sjsu.cmpe297.db.object;

public class Views implements DatabaseObject {

	//-------------------------------------------
	// member variables
	//-------------------------------------------
	private Long userId;
	private Long productId;
	private Long viewCount;
	
	/**
	 * constructor
	 * 
	 * @param userId
	 * @param productId
	 * @param viewCount
	 */
	public Views(Long userId, Long productId, Long viewCount) {
		this.userId = userId;
		this.productId = productId;
		this.viewCount = viewCount;
	}
	
	//-------------------------------------------
	// accessor methods
	//-------------------------------------------
	
	/**
	 * gets the userId for this view
	 * 
	 * @return Long
	 */
	public Long getUserId() {
		return userId;
	}
	
	/**
	 * gets the productId for this view
	 * 
	 * @return Long
	 */
	public Long getProductId() {
		return productId;
	}
	
	/**
	 * gets the viewCount for this view
	 * 
	 * @return Long
	 */
	public Long getViewCount() {
		return viewCount;
	}
	
	/**
	 * sets the userId for this view
	 * 
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	/**
	 * sets the productId for this view
	 * 
	 * @param productId
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	/**
	 * sets the viewCount for this view
	 * 
	 * @param viewCount
	 */
	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}
	
	/**
	 * increments the viewCount by 1
	 */
	public void incrementViewCount() {
		viewCount++;
	}
	
	/**
	 * equals comparison for views
	 * 
	 * @param obj
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj)
	{
		Views view = (Views)obj;

		if(view.getUserId().equals(userId) && view.getProductId().equals(productId))
		{
			return true;
		}

		return false;
	}
}

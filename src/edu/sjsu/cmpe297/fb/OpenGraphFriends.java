package edu.sjsu.cmpe297.fb;

import org.codehaus.jettison.json.JSONObject;

public class OpenGraphFriends {

	private String json;
	private JSONObject obj;
	
	/**
	 * Creates a new instance of a Facebook user from the 
	 * passed in JSON string.
	 * 
	 * @param json
	 * @throws Exception
	 */
	public OpenGraphFriends(String json) throws Exception
	{
		this.json = json;
		obj = new JSONObject(json);
	}
	
	public String toJson()
	{
		return json;
	}
}

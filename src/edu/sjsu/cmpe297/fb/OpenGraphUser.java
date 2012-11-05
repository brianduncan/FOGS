package edu.sjsu.cmpe297.fb;
/**
 * Object to represent a Facebook User
 */
import org.codehaus.jettison.json.JSONObject;

import edu.sjsu.cmpe297.fb.FacebookClient;

public class OpenGraphUser {

	private String json;
	private JSONObject obj;
	private String accessToken;
	
	private FacebookClient client;
	private OpenGraphLikes userLikes;
	
	/**
	 * Creates a new instance of a Facebook user from the 
	 * passed in id string. 
	 * 
	 * @param id
	 * @throws Exception
	 */
	public OpenGraphUser(String id) throws Exception
	{
		client = new FacebookClient();
		accessToken = "";
		
		json = client.getUser(id);
		obj = new JSONObject(json);
	}
	
	public String toJson()
	{
		return json;
	}
	
	public String getId() throws Exception
	{
		return obj.getString("id");
	}
	
	public String getName() throws Exception
	{
		return obj.getString("name");
	}
	
	public String getFirstName() throws Exception
	{
		return obj.getString("first_name");
	}
	
	public String getLastName() throws Exception
	{
		return obj.getString("last_name");
	}
	
	public String getLink() throws Exception
	{
		return obj.getString("link");
	}
	
	public String getUserName() throws Exception
	{
		return obj.getString("username");
	}
	
	public String getGender() throws Exception
	{
		return obj.getString("gender");
	}
	
	public String getLocale() throws Exception
	{
		return obj.getString("locale");
	}
	
	public void setAccessToken(String token)
	{
		accessToken = token;
	}
	
	private void getLikes() throws Exception
	{	
		userLikes = new OpenGraphLikes(client.getLikes(getId(), accessToken));
	}
	
	public boolean likes(String id) throws Exception
	{
		if (null == userLikes)
			getLikes();
		
		return userLikes.likes(id);
	}
}
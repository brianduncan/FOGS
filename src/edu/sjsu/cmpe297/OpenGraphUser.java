package edu.sjsu.cmpe297;
/**
 * Object to represent a Facebook User
 */
import org.codehaus.jettison.json.JSONObject;

public class OpenGraphUser {

	private String json;
	private JSONObject obj;
	
	/**
	 * Creates a new instance of a Facebook user from the 
	 * passed in JSON string.
	 * 
	 * @param json
	 * @throws Exception
	 */
	public OpenGraphUser(String json) throws Exception
	{
		this.json = json;
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
}

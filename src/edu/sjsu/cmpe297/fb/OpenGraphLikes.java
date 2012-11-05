package edu.sjsu.cmpe297.fb;

/**
 * Object to represent the "Likes" data of a Facebook User
 */
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class OpenGraphLikes {

	private String json;
	private JSONObject obj;
	
	/**
	 * Creates a new instance of a Facebook user from the 
	 * passed in JSON string.
	 * 
	 * @param json
	 * @throws Exception
	 */
	public OpenGraphLikes(String json) throws Exception
	{
		this.json = json;
		obj = new JSONObject(json);
	}
	
	public String toJson()
	{
		return json;
	}
	
	/**
	 * Accepts a facebook item id and will search through the list of likes
	 * to see if there is a match.
	 * 
	 * @param id The facebook id of an product, company, or page
	 * @return
	 * @throws Exception
	 */
	public boolean likes(String id) throws Exception
	{
		JSONArray likesList = obj.getJSONArray("data");
		
		for (int i = 0; i < likesList.length() ; i++ ) {
			JSONObject likeObject = likesList.getJSONObject(i);
			
			if (id.equals(likeObject.getString("id"))) {
				return true;
			}
		}
		
		return false;
	}
}

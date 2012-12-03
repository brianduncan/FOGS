package edu.sjsu.cmpe297.fb;
/**
 * Engine to make calls using the Facebook Open Graph API
 */
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import edu.sjsu.cmpe297.fb.OpenGraphUser;

public class FacebookClient {

	private HttpClient client;
	private ResponseHandler<String> responseHandler;
	private String accessToken;
	
	private static final String API = "https://graph.facebook.com";
	private static final String API_FRIENDS = "/%s/friends?access_token=%s";
	private static final String API_LIKES = "/%s/likes?access_token=%s";
	
	
	public FacebookClient()
	{
		client = new DefaultHttpClient();
		responseHandler = new BasicResponseHandler();
	}
	
	public void setAccessToken(String token)
	{
		accessToken = token;
	}
	
	public String getUser(String id)
	{
		String response = "";
		
		try {
		    HttpGet request = new HttpGet(API + "/" + id);
		    response = client.execute(request, responseHandler);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return response;
	}
	
	//ALTERNATE OPTION FOR getFriends()
	protected List<OpenGraphUser> getFriends(String id, String token) 
	{
		String response = "";
		List<OpenGraphUser> friends = new ArrayList<OpenGraphUser>();
		
		try {
			HttpGet request = new HttpGet(API + String.format(API_FRIENDS, id, token));
			response = client.execute(request, responseHandler);
			
			System.out.println("response = " + response);
			
			JSONObject json = new JSONObject(response);
			JSONArray array = json.getJSONArray("data");
			
			System.out.println("Array length = " + array.length());
			
			java.util.Date date1 = new java.util.Date();
			System.out.println(new Timestamp(date1.getTime()));
			
			for (int i = 0; i < array.length(); i++) {
				JSONObject person = array.getJSONObject(i);
				OpenGraphUser friend = new OpenGraphUser(person.get("id").toString());
				friends.add(friend);
				
//				OpenGraphUser friend = new OpenGraphUser(array.getString(i));
//				friends.add(friend);
			}
			
			java.util.Date date2 = new java.util.Date();
			System.out.println(new Timestamp(date2.getTime()));
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return friends;
	}
	
	protected String getFriendsString(String id, String token) 
	{
		String response = "";
		
		try {
			HttpGet request = new HttpGet(API + String.format(API_FRIENDS, id, token));
			response = client.execute(request, responseHandler);
			
			System.out.println("response = " + response);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return response;
	}
	
	/*
	protected String getFriends(String id, String token)
	{
		String response = "";
		String url = "";
		
		try {
			url = String.format(API + API_FRIENDS, id, token);
			
		    HttpGet request = new HttpGet(url);
		    response = client.execute(request, responseHandler);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response = e.getMessage() + " (" + url + ")";
		}
		
		return response;
	}
	*/
	
	protected String getLikes(String id, String token)
	{
		String response = "";
		String url = "";
		
		try {
			url = String.format(API + API_LIKES, id, token);
			
		    HttpGet request = new HttpGet(url);
		    response = client.execute(request, responseHandler);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response = e.getMessage() + " (" + url + ")";
		}
		
		return response;
	}
	
}

package edu.sjsu.cmpe297.fb;
/**
 * Engine to make calls using the Facebook Open Graph API
 */
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

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

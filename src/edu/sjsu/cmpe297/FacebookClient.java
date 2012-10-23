package edu.sjsu.cmpe297;
/**
 * Engine to make calls using the Facebook Open Graph API
 */
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class FacebookClient {

	private HttpClient client;
	private ResponseHandler<String> responseHandler;
	
	private static final String API = "https://graph.facebook.com";
	private static final String API_FRIENDS = "/%s/friends?access_token=%s";
	private static final String API_LIKES = "/%s/likes?access_token=%s";
	
	
	public FacebookClient()
	{
		client = new DefaultHttpClient();
		responseHandler = new BasicResponseHandler();
	}
	
	public OpenGraphUser getUser(String username)
	{
		String response = "";
		OpenGraphUser user = null;
		
		try {
		    HttpGet request = new HttpGet(API + "/" + username);
		    response = client.execute(request, responseHandler);
		    
            user = new OpenGraphUser(response);
		    
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return user;
	}

}

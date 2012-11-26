package edu.sjsu.cmpe297;
/**
 * This is a sample of how we can create a REST service.  
 * The app is configured so that you would access
 * /FOGS/rest/{path} where {path} is specified for the class 
 * with the @Path annotation.
 */


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.sjsu.cmpe297.fb.OpenGraphUser;

@Path("/test")
public class Test {
	
	/**
	 * This function gets called by default.  Since our service
	 * will return JSON we set that with the @Produces annotation.
	 * We still need to make sure we properly form our JSON objects.
	 */
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserInfo() {
		String response = "";
			
		try {
			// This is just a sample. btaylor is a sample user from the FB API documentation
		    OpenGraphUser user = new OpenGraphUser("michael.yu.5688");
		    // This is just a test, rather than display all the users, I'm just getting his ID
		    response = user.toJson();
		
		} catch (Exception e) {
			// Handle it ...
			e.printStackTrace();
		}
		
		return response;
	}
	
	@GET
	@Path("/likes1")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserLikes() {
		String response = "";
		
		try {
			// This is just a sample. btaylor is a sample user from the FB API documentation
		    OpenGraphUser user = new OpenGraphUser("michael.yu.5688");
		    user.setAccessToken("AAAAAAITEghMBAJrp2rlpzMAvdCPJfDFtVaAbrBU7r3tTWGx0xlDevUgLxI0fXTGuCGtFcs9mHBdgHvq66VR7gnQD5sXbbEoGjP6PHwZDZD");
		    
		    // This is just a test, rather than display all the users, I'm just getting his ID
		    
		    // 190305301041692 = Pumpkin Spice Latte
		    response = String.valueOf(user.likes("102950680273"));
		
		} catch (Exception e) {
			// Handle it ...
			e.printStackTrace();
			response = "{ error : \"" + e.getMessage() + "\"}";
		}
		
		return response;
	}
	
	@GET
	@Path("/likes2")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserLikes2() {
		String response = "";
		
		try {
			// This is just a sample. btaylor is a sample user from the FB API documentation
		    OpenGraphUser user = new OpenGraphUser("briansjsu");
		    user.setAccessToken("AAAAAAITEghMBAJpd6UdQm3ry7Om8mVxZB3mDNXsPbx7vlZBb6EJJSzD29p8Siid9vRh8LlrLXzaAczc6mKysqY1pYEbZAfhrn0F7u29HE6Tn3rQ0d9M");
		    
		    // This is just a test, rather than display all the users, I'm just getting his ID
		    
		    // 190305301041692 = Pumpkin Spice Latte
		    response = String.valueOf(user.likes("19030"));
		
		} catch (Exception e) {
			// Handle it ...
			e.printStackTrace();
			response = "{ error : \"" + e.getMessage() + "\"}";
		}
		
		return response;
	}
}

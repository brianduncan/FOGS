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

@Path("/test")
public class Test {

	FacebookClient fb = new FacebookClient();
	
	/**
	 * This function gets called by default.  Since our service
	 * will return JSON we set that with the @Produces annotation.
	 * We still need to make sure we properly form our JSON objects.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserInfo() {
		String response = "";
			
		try {
			// This is just a sample. btaylor is a sample user from the FB API documentation
		    OpenGraphUser user = fb.getUser("btaylor");
		    // This is just a test, rather than display all the users, I'm just getting his ID
		    response = user.getId();
		
		} catch (Exception e) {
			// Handle it ...
			e.printStackTrace();
		}
		
		return "[ " + response + " ]";
	}
	
}

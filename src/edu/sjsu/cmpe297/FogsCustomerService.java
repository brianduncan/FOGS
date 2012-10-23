/**
 * 
 */
package edu.sjsu.cmpe297;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author rpriyad
 *
 */

// POJO, no interface no extends

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML.

//This is the service interface class which is exposed as a service provider
//for other system who wants to use the FOGS service.

//Sets the path to base URL + /fogs
@Path("/fogs")
public class FogsCustomerService {
	
  //This method will be used to get the facebook Id for the company	
  @GET
  @Path("/facebookid/{compName}") 
  @Produces(MediaType.APPLICATION_JSON)
  public String getFacebookId(@PathParam("compName") String compName){
	  return "";
  }
  
  //This method will be used to get the products sold by the company	
  @GET
  @Path("/products/{compId}") 
  @Produces(MediaType.APPLICATION_JSON)
  public String getProductsSoldByCompany(@PathParam("compId") String compId){
	  
	  return "";
	  //TODO: This method will return a list	  
  }
  
  
  //Get products by name
  @GET
  @Path("/prodname/{prodName}") 
  @Produces(MediaType.APPLICATION_JSON)  
  public String getProductsByName(@PathParam("prodName") String prodName){
	  return "";
	  //TODO: This method will return a list
  }
  
  //This REST service interface will help to get the 
  //product by name and for a particular company.
  @GET
  @Path("/prodbycomp/{prodName}/{compId}") 
  @Produces(MediaType.APPLICATION_JSON) 
  public String getProductsByNameAndCompany(@PathParam("prodName") String prodName, @PathParam("compId") String compId){
	  return "";
	  //TODO: This method will return a list
  }
  
  //Gets friends of users from facebook
  @GET
  @Path("/friendsofuser/{uname}") 
  @Produces(MediaType.APPLICATION_JSON)
  public List<String> getFriendsOfUser(@PathParam("uName") String uName){
	  List<String> list = null;
	  return list;
	  //TODO: This method will return a list
  }
  
  //Gets the likes from facebook
  @GET
  @Path("/facebooklikes/{uname}/{prodName}/{compName}") 
  @Produces(MediaType.APPLICATION_JSON)
  public List<String> getFacebookLikesForUserFreind(@PathParam("uName") String uName, @PathParam("prodName") String prodtName, @PathParam("compName") String compName){
	  List<String> list = null;
	  return list;
	  //TODO: This method will return a list  
  }
  
  //Checks if facebook user exists
  @GET
  @Path("/facebookuserexists/{facebookId}/{uName}") 
  @Produces(MediaType.APPLICATION_JSON)
  public String facebookIdUserExists(@PathParam("facebookId") String facebookId, @PathParam("uName") String uName){
	  
	  return "";
  }
  
  //Gets the friemds comments for facebook 
  @GET
  @Path("/facebookcomts/{uName}/{prodName}/{compName}") 
  @Produces(MediaType.APPLICATION_JSON)
  public List<String> getFacebookCommentsForUserFriends(@PathParam("uName") String uName, @PathParam("prodName") String prodName, @PathParam("compName") String compName){
	  List<String> list = null;
	  return list;
	  //TODO: This method will return a list	  
  }


}

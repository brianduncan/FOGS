/**
 * 
 */
package edu.sjsu.cmpe297;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import edu.sjsu.cmpe297.db.dao.CompanyDAO;
import edu.sjsu.cmpe297.db.dao.ViewsDAO;
import edu.sjsu.cmpe297.db.object.Company;
import edu.sjsu.cmpe297.db.object.Views;
import edu.sjsu.cmpe297.fb.OpenGraphUser;

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
	
	//This method will be used to get the friends that navigated the
	//same product. It will pass back the configurable number of friends.
	  @GET
	  @Path("/friendsvisited/{userid}/{compid}/{facebookprodid}/{listsize}") 
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getFriendsVisitedProd(@PathParam("userid") String userid, @PathParam("compid") String compid, 
			  							  @PathParam("facebookprodid") String facebookprodid, @PathParam("listsize") String listsize){
		  
	   
	    String retdata = "";
	    JSONObject j = new JSONObject();
	    //Validate that all the fields have been passed
	    if(StringUtils.isEmpty(userid) || StringUtils.isEmpty(compid)
	    		|| StringUtils.isEmpty(facebookprodid) || StringUtils.isEmpty(listsize)){
	    		    	  
			  j.put("101", "REQUIRED PARAMETER FIELDS MISSING");
			  retdata = j.toString();	    	
	    }else{
		  
			  try {
				  //Get facebook freinds for the user
				  OpenGraphUser ogu = new OpenGraphUser(userid);
				  List<OpenGraphUser> friends = ogu.getFriends();
				  int flsize = 0;
				  //If friends returned for the user
				  if(friends.size()>0){
					  //Evaluate the size of the list to be returned
					  if(StringUtils.isNotEmpty(listsize) 
							  && this.isNumeric(listsize)){
						  flsize = Integer.parseInt(listsize);
					  }else{
						  flsize = friends.size();
					  }
					  
					  //Get all the users who viewed the product
					  ViewsDAO vd = ViewsDAO.getInstance();
					  List<Views> prodview = vd.getViewsForProduct(Long.parseLong(facebookprodid));
					  
					  //Create a hasmap of the view that is returned
					  HashMap<Long, Long> upmap = new HashMap<Long, Long>();
					  for(int i=0; i<prodview.size(); i++){
						  Views v = prodview.get(i);
						  upmap.put(v.getUserId(), v.getViewCount());						  
					  }
					  
					  //Check if the friend of users viewed the products
					  JSONObject jusers = new JSONObject();
					  int x = 0;
					  for(int i=0; i<flsize; i++){
						  //Check if friend's id exists in the list, then add it to the list to be returned
						  if(upmap.containsKey(friends.get(i).getId())){
							  x = x + 1;
							  jusers.put(x, friends.get(i).getId());							  
						  }						  
					  }
					  
					  //If list of friends was found
					  if(x>0){
						  retdata = jusers.toString();
					  }else{
						  j.put("10", "NO FRIENDS FOUND");
						  retdata = j.toString();
					  }
					  
					  //Insert/Update the views record for the user for this product if user already does not exist in the db
					  if(upmap.containsKey(userid)){
						  //User exist in the db for viewing the product
						  Views v = new Views(Long.parseLong(userid), Long.parseLong(facebookprodid), null);
						  v = vd.get(v);
						  Views vnew = new Views(v.getUserId(), v.getProductId(), new Long(v.getViewCount().longValue() + 1));						 
						  vd.update(v, vnew);
					  }else{
						  Views v = new Views(Long.parseLong(userid), Long.parseLong(facebookprodid), new Long(1));
						  vd.insert(v);
					  }
					  
				  }else{
					  j.put("10", "NO FRIENDS FOUND");
					  retdata = j.toString();
				  }
				  
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				j.put("100", "ERROR RETRIEVING DATA");
				retdata = j.toString();
			}
	    }
		  
		  return retdata;
	  }
	
	
	
	 //This method will be used to get the facebook Id for the company	
	  @GET
	  @Path("/compname/{facebookid}") 
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getCompanyName(@PathParam("facebookid") String facebookid){
		  
		  Company comp = new Company(new Long(facebookid), null);
		  CompanyDAO compDAO = CompanyDAO.getInstance();
		  try {
			comp = compDAO.get(comp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  JSONObject jObj = JSONObject.fromObject(comp);
		  
		  return jObj.toString();
	  }
	  
	  
	//This method will be used to get the facebook Id for the company	
	  @GET
	  @Path("/allcomp") 
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getCompanyName(){
		  
		  CompanyDAO compDAO = CompanyDAO.getInstance();
		  JSONObject jcomps = new JSONObject();
		  try {				
				ArrayList<Company>  list = (ArrayList<Company>) compDAO.list();
				
				for(int i=0; i<list.size(); i++){
					JSONObject jObj = new JSONObject();
					jObj = JSONObject.fromObject(list.get(i));
					jcomps.put(i, jObj.toString());
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		  return jcomps.toString();
		  
	  }
	
  //This method will be used to get the facebook Id for the company	
  @GET
  @Path("/facebookid/{compName}") 
  @Produces(MediaType.APPLICATION_JSON)
  public String getFacebookId(@PathParam("compName") String compName){
	  return "compName";
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
  
  
  //Check if the string passed is an integer number
  private boolean isNumeric(String str)  
  {  
    try  
    {  
        
    	Integer.parseInt(str);  
    }  
    catch(NumberFormatException nfe)  
    {  
      return false;  
    }  
    return true;  
  }


}

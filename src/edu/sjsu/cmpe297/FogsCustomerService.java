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
import edu.sjsu.cmpe297.db.dao.LikesDAO;
import edu.sjsu.cmpe297.db.dao.ViewsDAO;
import edu.sjsu.cmpe297.db.object.Company;
import edu.sjsu.cmpe297.db.object.Likes;
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
	  @Path("/friendsvisited/{userid}/{compid}/{facebookprodid}/{listsize}/{uname}/{prodname}") 
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getFriendsVisitedProd(@PathParam("userid") String userid, @PathParam("compid") String compid, 
			  							  @PathParam("facebookprodid") String facebookprodid, @PathParam("listsize") String listsize, 
			  							  @PathParam("uname") String uname, @PathParam("prodname") String prodname){
		  
	   
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
							  && FogsCSHelper.isNumeric(listsize)){
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
						  Views vnew = new Views(v.getUserId(), v.getProductId(), new Long(v.getViewCount().intValue() + 1));						 
						  vd.update(v, vnew);
					  }else{
						  
						  //Check and add user if not present in users table
						  FogsCSHelper.checkAndAddUser(Long.parseLong(userid), uname);
						  
						  //Check and add product if not present
						  FogsCSHelper.checkAndAddProduct(Long.parseLong(facebookprodid), prodname, Long.parseLong(compid));
						  
						  //Check and add product if not existing in db
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
	  
	  //This method will return the number of likes for a product by the user's friends
	  @GET
	  @Path("/friendsliked/{userid}/{facebookprodid}") 
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getFriendsLikesProd(@PathParam("userid") String userid, @PathParam("facebookprodid") String facebookprodid) 
	  {
		  String retdata = "";
		  JSONObject j = new JSONObject();
		  
		  //Validate that all the fields have been passed
		  if(StringUtils.isEmpty(userid) || StringUtils.isEmpty(facebookprodid)) 
		  {
			  j.put("101", "REQUIRED PARAMETER FIELDS MISSING");
			  retdata = j.toString();	    	
		  }
		  else 
		  {
			  try 
			  {
				  //Get list of user's facebook friends
				  OpenGraphUser openGraphUser = new OpenGraphUser(userid);
				  openGraphUser.setAccessToken("AAAAAAITEghMBANjAKnSblZAfZCf2gHzUV9bj43hEKzxz3B5NbZBNVZAGXgBgX66HJ9N2s2rhK3H25j9A7ffSuiLcezUV4t0lmzBY7kPvKgZDZD");
				  List<OpenGraphUser> friendsList = openGraphUser.getFriends();
				  
				  //If friends returned for the user
				  if(friendsList.size() > 0)
				  {
					  JSONObject jusers = new JSONObject();

					  //Get list of people who like the product
					  LikesDAO likesDAO = LikesDAO.getInstance();
					  List<Likes> productLikesList = likesDAO.getLikesForProduct(Long.parseLong(facebookprodid));
					
					  //Count for number of friends who like the product
					  int count = 0;
					  
					  //Determine whether any of the user's friends like the product
					  for(int i = 0; i < friendsList.size(); i++)
					  {
						  Likes friendLike = new Likes(Long.parseLong(friendsList.get(i).getId()), Long.parseLong(facebookprodid));
						  
						  if(productLikesList.contains(friendLike))
						  {
							  count++;
							  jusers.put(count, friendsList.get(i).toJson());
						  }
					  }

					  if(count > 0)
					  {
						  retdata = jusers.toString();
					  }
					  else
					  {
						  j.put("10", "NO FRIENDS FOUND");
						  retdata = j.toString();
					  }

				  }
				  else
				  {
					  j.put("10", "NO FRIENDS FOUND");
					  retdata = j.toString();
				  }
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				j.put("100", "ERROR RETRIEVING DATA");
				retdata = j.toString();
			}
	    }
		  
		return retdata;
	  }
	  
	//This method will return the number of likes for a product by the user's friends
	  @GET
	  @Path("/friendsliked2/{userid}/{facebookprodid}") 
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getFriendsLikesProd2(@PathParam("userid") String userid, @PathParam("facebookprodid") String facebookprodid) 
	  {
		  String retdata = "";
		  JSONObject j = new JSONObject();
		  
		  //Validate that all the fields have been passed
		  if(StringUtils.isEmpty(userid) || StringUtils.isEmpty(facebookprodid)) 
		  {
			  j.put("101", "REQUIRED PARAMETER FIELDS MISSING");
			  retdata = j.toString();	    	
		  }
		  else 
		  {
			  try 
			  {
				  //Get string of user's facebook friends
				  OpenGraphUser openGraphUser = new OpenGraphUser(userid);
				  openGraphUser.setAccessToken("AAAAAAITEghMBANjAKnSblZAfZCf2gHzUV9bj43hEKzxz3B5NbZBNVZAGXgBgX66HJ9N2s2rhK3H25j9A7ffSuiLcezUV4t0lmzBY7kPvKgZDZD");
				  String friends = openGraphUser.getFriendsString();
				  
				  //If friends returned for the user
				  if(friends.toCharArray().length != 0)
				  {
					  JSONObject jusers = new JSONObject();

					  //Get list of people who like the product
					  LikesDAO likesDAO = LikesDAO.getInstance();
					  List<Likes> productLikesList = likesDAO.getLikesForProduct(Long.parseLong(facebookprodid));
					
					  //Count for number of friends who like the product
					  int count = 0;
					  
					  //Determine whether any of the user's friends like the product
					  for(int i = 0; i < productLikesList.size(); i++)
					  {
						  if(friends.contains(String.valueOf(productLikesList.get(i).getUserId())))
						  {
							  count++;
							  OpenGraphUser ogu = new OpenGraphUser(String.valueOf(productLikesList.get(i).getUserId()));
							  jusers.put(count, ogu.toJson());
						  }
					  }

					  if(count > 0)
					  {
						  retdata = jusers.toString();
					  }
					  else
					  {
						  j.put("10", "NO FRIENDS FOUND");
						  retdata = j.toString();
					  }
				  }
				  else
				  {
					  j.put("10", "NO FRIENDS FOUND");
					  retdata = j.toString();
				  }
			} 
			catch (Exception e) 
			{
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

}

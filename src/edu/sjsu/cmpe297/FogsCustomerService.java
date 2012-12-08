/**
 * 
 */
package edu.sjsu.cmpe297;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import edu.sjsu.cmpe297.db.dao.CompanyDAO;
import edu.sjsu.cmpe297.db.dao.UsersDAO;
import edu.sjsu.cmpe297.db.dao.ViewsDAO;
import edu.sjsu.cmpe297.db.object.Company;
import edu.sjsu.cmpe297.db.object.Users;
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
	  
		//This method will return the number of likes for a product by the user's friends 
	  @GET
	  @Path("/friendsviewretall/{userid}/{facebookprodid}/{token}") 
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getFriendsVisitedProd2(@PathParam("userid") String userid, @PathParam("facebookprodid") String facebookprodid, @PathParam("token") String token) 
	  {
		  String retdata = "";
		  JSONObject j = new JSONObject();
		  
		  //Validate that all the fields have been passed
		  if(StringUtils.isEmpty(userid) || StringUtils.isEmpty(facebookprodid) || StringUtils.isEmpty(token)) 
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
				  openGraphUser.setAccessToken(token);
				  String friends = openGraphUser.getFriendsString();
				  
				  //If friends returned for the user
				  if(friends.toCharArray().length != 0)
				  {
					  JSONObject jusers = new JSONObject();

					  //Get list of people who viewed the product
					  ViewsDAO viewsDAO = ViewsDAO.getInstance();
					  List<Views> productViewList = viewsDAO.getViewsForProduct(Long.parseLong(facebookprodid));
					  //Count for number of friends who viewed the product
					  int count = 0;
					  
					  //Determine whether any of the user's friends viewed the product
					  for(int i = 0; i < productViewList.size(); i++)
					  {
						  if(friends.contains(String.valueOf(productViewList.get(i).getUserId())))
						  {
							  count++;
							  OpenGraphUser ogu = new OpenGraphUser(String.valueOf(productViewList.get(i).getUserId()));
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
					  
					  
					  
					  Views view = new Views(Long.valueOf(userid), Long.valueOf(facebookprodid), 1L);
					  
					  if(productViewList.contains(view))
					  {
						  productViewList.get(productViewList.indexOf(view)).incrementViewCount();
						  viewsDAO.update(view, productViewList.get(productViewList.indexOf(view)));
					  }
					  else
					  {
						  viewsDAO.insert(view);
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
	  @Path("/friendsviewretlimited/{userid}/{facebookprodid}/{retcount}/{token}") 
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getFriendsVisitedProd3(@PathParam("userid") String userid, @PathParam("facebookprodid") String facebookprodid, @PathParam("retcount") String retcount, @PathParam("token") String token) 
	  {
		  String retdata = "";
		  JSONObject j = new JSONObject();
		  
		  //Validate that all the fields have been passed
		  if(StringUtils.isEmpty(userid) || StringUtils.isEmpty(facebookprodid) || (StringUtils.isEmpty(retcount)) || StringUtils.isEmpty(token)) 
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
				  openGraphUser.setAccessToken(token);
				  String friends = openGraphUser.getFriendsString();
				  
				  //If friends returned for the user
				  if(friends.toCharArray().length != 0)
				  {
					  JSONObject jusers = new JSONObject();

					  //Get list of people who viewed the product
					  ViewsDAO viewsDAO = ViewsDAO.getInstance();
					  List<Views> productViewList = viewsDAO.getViewsForProduct(Long.parseLong(facebookprodid));
					
					  //Count for number of friends who viewed the product
					  int count = 0;					  
					  
					  //Determine whether any of the user's friends viewed the product
					  for(int i = 0; i < productViewList.size(); i++)
					  {
						  
						  if(count >= Integer.valueOf(retcount)) break;
						  
						  if(friends.contains(String.valueOf(productViewList.get(i).getUserId())))
						  {
							  count++;							  
							  OpenGraphUser ogu = new OpenGraphUser(String.valueOf(productViewList.get(i).getUserId()));
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
					  
					  
					  Views view = new Views(Long.valueOf(userid), Long.valueOf(facebookprodid), 1L);
					  
					  if(productViewList.contains(view))
					  {
						  productViewList.get(productViewList.indexOf(view)).incrementViewCount();
						  viewsDAO.update(view, productViewList.get(productViewList.indexOf(view)));
					  }
					  else
					  {
						  viewsDAO.insert(view);
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
	  @Path("/friendsliked/{userid}/{facebookprodid}/{token}") 
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getFriendsLikesProd(@PathParam("userid") String userid, @PathParam("facebookprodid") String facebookprodid, @PathParam("token") String token) 
	  {			
		  String retdata = "";
		  JSONObject j = new JSONObject();
		  
		  //Validate that all the fields have been passed
		  if(StringUtils.isEmpty(userid) || StringUtils.isEmpty(facebookprodid) || StringUtils.isEmpty(token)) 
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
				  openGraphUser.setAccessToken(token);
				  String friends = openGraphUser.getFriendsString();
				  
				  //Define list for friends who are registered users
				  List<String> friendWhoIsUserList = new ArrayList<String>();
				  
				  //Define list for open graph users who like the product
				  List<OpenGraphUser> oguList = new ArrayList<OpenGraphUser>();
				  
				  //If friends returned for the user
				  if(friends.toCharArray().length != 0)
				  {
					  JSONObject jusers = new JSONObject();

					  //Get list of existing users
					  UsersDAO usersDAO = UsersDAO.getInstance();
				      List<Users> userList = usersDAO.list();
				      
				      //Determine which friends are registered users
				      for(int i = 0; i < userList.size(); i++)
					  {
				    	  if(!userid.contains(String.valueOf(userList.get(i).getFacebookId())) && friends.contains(String.valueOf(userList.get(i).getFacebookId())))
						  {
							  friendWhoIsUserList.add(String.valueOf(userList.get(i).getFacebookId()));
						  }
					  }
					
					  //Count for number of friends who like the product
					  int count = 0;
					  
					  //Determine which registered friends like the product
					  for(int i = 0; i < friendWhoIsUserList.size(); i++)
				      {
						  String likes = openGraphUser.getFriendLikes(friendWhoIsUserList.get(i));
						  
						  if(likes.contains(facebookprodid))
						  {
							  count++;
							  OpenGraphUser ogu = new OpenGraphUser(friendWhoIsUserList.get(i));
							  oguList.add(ogu);
							  jusers.put(count, ogu.toJson());
						  }
					  }
					  
					  if(count > 0)
					  {
						  //Get list of existing views
						  ViewsDAO viewsDAO = ViewsDAO.getInstance();
						  List<Views> viewList = viewsDAO.list();
						
						  //Update or insert views as necessary
						  for(int i = 0; i < oguList.size(); i++)
						  {
							  Views view = new Views(Long.valueOf(oguList.get(i).getId()), Long.valueOf(facebookprodid), 1L);
							  
							  if(viewList.contains(view))
							  {
								  viewList.get(viewList.indexOf(view)).incrementViewCount();
								  viewsDAO.update(view, viewList.get(viewList.indexOf(view)));
							  }
							  else
							  {
								  viewsDAO.insert(view);
							  }
						  }

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
	  
	 //This method will return the number of likes for a product by the user's friends not exceeding the specified return count
	  @GET
	  @Path("/friendsliked/{userid}/{facebookprodid}/{retcount}/{token}") 
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getFriendsLikesProd(@PathParam("userid") String userid, @PathParam("facebookprodid") String facebookprodid, @PathParam("retcount") String retcount, @PathParam("token") String token) 
	  {			
		  String retdata = "";
		  JSONObject j = new JSONObject();
		  
		  //Validate that all the fields have been passed
		  if(StringUtils.isEmpty(userid) || StringUtils.isEmpty(facebookprodid) || StringUtils.isEmpty(token)) 
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
				  openGraphUser.setAccessToken(token);
				  String friends = openGraphUser.getFriendsString();
				  
				  //Define list for friends who are registered users
				  List<String> friendWhoIsUserList = new ArrayList<String>();
				  
				  //Define list for open graph users who like the product
				  List<OpenGraphUser> oguList = new ArrayList<OpenGraphUser>();
				  
				  //If friends returned for the user
				  if(friends.toCharArray().length != 0)
				  {
					  JSONObject jusers = new JSONObject();

					  //Get list of existing users
					  UsersDAO usersDAO = UsersDAO.getInstance();
				      List<Users> userList = usersDAO.list();
				      
				      //Determine which friends are registered users
				      for(int i = 0; i < userList.size(); i++)
					  {
				    	  if(!userid.contains(String.valueOf(userList.get(i).getFacebookId())) && friends.contains(String.valueOf(userList.get(i).getFacebookId())))
						  {
							  friendWhoIsUserList.add(String.valueOf(userList.get(i).getFacebookId()));
						  }
					  }
					
					  //Count for number of friends who like the product
					  int count = 0;
					  
					  //Determine which registered friends like the product
					  for(int i = 0; i < friendWhoIsUserList.size(); i++)
				      {
						  if(count >= Integer.valueOf(retcount)) break;
						  
						  String likes = openGraphUser.getFriendLikes(friendWhoIsUserList.get(i));
						  
						  if(likes.contains(facebookprodid))
						  {
							  count++;
							  OpenGraphUser ogu = new OpenGraphUser(friendWhoIsUserList.get(i));
							  oguList.add(ogu);
							  jusers.put(count, ogu.toJson());
						  }
					  }
					  
					  if(count > 0)
					  {
						  //Get list of existing views
						  ViewsDAO viewsDAO = ViewsDAO.getInstance();
						  List<Views> viewList = viewsDAO.list();
						
						  //Update or insert views as necessary
						  for(int i = 0; i < oguList.size(); i++)
						  {
							  Views view = new Views(Long.valueOf(oguList.get(i).getId()), Long.valueOf(facebookprodid), 1L);
							  
							  if(viewList.contains(view))
							  {
								  viewList.get(viewList.indexOf(view)).incrementViewCount();
								  viewsDAO.update(view, viewList.get(viewList.indexOf(view)));
							  }
							  else
							  {
								  viewsDAO.insert(view);
							  }
						  }

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

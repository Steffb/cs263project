package test3.test3;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UserServlet extends HttpServlet {
	
	@Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
	  
	  }
	
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
		      throws IOException {
		  
		  UserService userService = UserServiceFactory.getUserService();
		    User user = userService.getCurrentUser();
		    
		    
		    
		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    String userId = user.getUserId();
		    Key UserKey = KeyFactory.createKey("UserEntity", userId);
		    
			
			// Run an ancestor query to ensure we see the most up-to-date
			// view of the Greetings belonging to the selected Guestbook.
		    
		    System.out.println("looking for with key "+userId);
		    
			Query query = new Query("UserEntity",UserKey);
			System.out.println("This is the query "+query.toString());
			
			
			System.out.println(UserKey.getId()+" as UserId");
			
			
			
			List<Entity> userlist = datastore.prepare(query).asList(
					FetchOptions.Builder.withLimit(5));

						
	
			if (userlist.isEmpty()) {
				//This is a new user so add him to datastore
			
				String mail = user.getEmail();
				Date date = new Date();
				Entity userent = new Entity("UserEntity", UserKey);
				System.out.println("setting userentity with key "+userent.getKey());
				userent.setProperty("user", user);
				userent.setProperty("date", date);
				userent.setProperty("mail", mail);
				userent.setProperty("loginId", userId);
				
				datastore.put(userent);
				
			}else{
				System.out.println("this is not a new user");
			}
		    
		    
			
		    

		    resp.sendRedirect("/userpage.jsp");

		    }

}

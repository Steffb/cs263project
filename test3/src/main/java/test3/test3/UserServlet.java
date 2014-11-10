package test3.test3;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
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

		    String userId = user.getUserId();
		    Key UserKey = KeyFactory.createKey("UserId", userId);
		    String mail = user.getEmail();
		    Date date = new Date();
		    Entity userent = new Entity("UserEntity", UserKey);
		    userent.setProperty("user", user);
		    userent.setProperty("date", date);
		    userent.setProperty("mail", mail);

		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    datastore.put(userent);
		    
		    

		    resp.sendRedirect("/userpage.jsp");

		    }

}

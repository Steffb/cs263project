package test3.test3;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Request;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


/**
 * 
 * @author steffenfb
 * 
 * Takes over after successfull login, and saves user to datastore
 * 
 * 
 */
public class UserServlet extends HttpServlet {



	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		System.out.println("running post");

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		String userId = user.getUserId();
		if (req.getParameter("type").equals("save")){
			Key UserKey = KeyFactory.createKey("UserEntity", userId);

			Query query = new Query("UserEntity",UserKey);

			Entity dsuser = datastore.prepare(query).asSingleEntity();

			Entity savematch= new Entity("Savematch", dsuser.getKey());
			savematch.setProperty("winner", req.getParameter("winner"));
			savematch.setProperty("loser", req.getParameter("loser"));
			savematch.setProperty("kind", req.getParameter("kind"));
			savematch.setProperty("event", req.getParameter("event"));
			savematch.setProperty("comment", req.getParameter("comment"));
			datastore.put(savematch);

			System.out.println(req.getParameter("url"));
			resp.sendRedirect("ufc?eventurl="+req.getParameter("url"));

		}else if (req.getParameter("type").equals("comment")){
			System.out.println("a comment was made!");
			
			Key parent =KeyFactory.createKey(req.getParameter("parentkind"),req.getParameter("parentname"));
			datastore = DatastoreServiceFactory.getDatastoreService();
			//Long l =Long.parseLong(req.getParameter("id"));
			Entity e = new Entity("comment", parent);
			e.setProperty("content", req.getParameter("comment"));
			e.setProperty("user", user.getNickname());
			datastore.put(e);
			System.out.println("comment was saved");
			resp.sendRedirect("ufc?eventurl="+req.getParameter("url"));



			//each fight has a field vote 
		}else if (req.getParameter("type").equals("like")){
			System.out.println("match was liked");
			System.out.println(req.getParameter("parentkind"));
			System.out.println(req.getParameter("parentname"));
			
			Key parent =KeyFactory.createKey(req.getParameter("parentkind"),req.getParameter("parentname"));
			datastore = DatastoreServiceFactory.getDatastoreService();
			Long l =Long.parseLong(req.getParameter("id"));
			Key mykey = KeyFactory.createKey(parent,"Fight",l);
			
			try {
				Entity e =datastore.get(mykey);
				long v = (Long) e.getProperty("vote");
				v+=1;
				e.setProperty("vote", v);
				
				datastore.put(e);
				System.out.print("hey it worked");
				resp.getWriter().println("it worked");
				//needs to be dynamic
				resp.sendRedirect("ufc?eventurl="+req.getParameter("url"));
				
				
				
			} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}



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
			userent.setProperty("blobkey", null);

			datastore.put(userent);

		}else{
			System.out.println("this is not a new user");
		}



		resp.sendRedirect("/userpage.jsp");

	}

}

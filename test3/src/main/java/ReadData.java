
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;


public class ReadData extends HttpServlet{
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query myquery = new Query("TaskData");
		
		
		List<Entity> results = datastore.prepare(myquery).asList(
				FetchOptions.Builder.withLimit(10));
		
		resp.setContentType("text/plain");
		
		for (int i = 0; i < results.size(); i++) {
			System.out.println(results.get(i));
			resp.getWriter().println(results.get(i));
			
		}
		
		
		
		
		
	}

}

package test3.test3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.gson.Gson;

import test3.test3.Datascraper;

public class UfcServlet extends HttpServlet{

	//Needs to send full event as attribute 


	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{ 

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


		//Add the event to db
		String url = "http://en.wikipedia.org/wiki/UFC_"+req.getParameter("eventid");



		UfcEvent u= new UfcEvent();
		u.eventname=Datascraper.getwikiheader(Datascraper.urlToDoc(url));
		
		
		u.fights=Datascraper.createFights(Datascraper.urlToDoc(url));


		Key ufcKey = KeyFactory.createKey("UfcEvent",u.eventname );
		Entity result = null;
		Query query = new Query("UfcEvent",ufcKey);
		
		System.out.println(query);
		result = datastore.prepare(query).asSingleEntity();

		// if it is already stored
		if(result !=null){
			
			
			
		}// if it is not stored, make it in datastore
		else{
			
			Entity league = new Entity("UfcEvent",ufcKey);
			league.setProperty("Eventname", u.eventname);
			


			datastore.put(league);

			for (int i = 0; i < u.fights.size(); i++) {

				Fight f=u.fights.get(i);
				//create fight with UfcEvent as parent and an long for id 
				Entity e = new Entity("Fight", Long.valueOf(i+1), ufcKey);
				e.setProperty("loser", f.loser);
				e.setProperty("winner", f.winner);
				e.setProperty("weightclass", f.weightclass);
				e.setProperty("hasbeen", f.hasbeen);
				e.setProperty("method", f.method);
				e.setProperty("note", f.note);
				e.setProperty("round", f.round);
				e.setProperty("stopped", f.stopped);
				e.setProperty("time",f.time);
				
				datastore.put(e);
			}
			

		}
		
		//create object from datastore
		

		req.setAttribute("key", ufcKey);
		ServletContext sc = this.getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/ufc.jsp");
		req.setAttribute("event", u);
		//forward query to page
		rd.forward(req, resp);
		
		
		





	}


}

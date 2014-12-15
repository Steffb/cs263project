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
		String url = "http://en.wikipedia.org"+req.getParameter("eventurl");
		UfcEvent u= new UfcEvent();
		u.eventname=Datascraper.getwikiheader(Datascraper.urlToDoc(url));
		//because the form on Ufc.jsp does not like whitespaces
		String eventname =u.eventname.replaceAll("\\s+","");
		u.fights=Datascraper.createFights(Datascraper.urlToDoc(url));
		Key ufcKey = KeyFactory.createKey("UfcEvent",eventname);
		Entity result = null;
		Query query = new Query("UfcEvent",ufcKey);
		System.out.println(query);
		result = datastore.prepare(query).asSingleEntity();

		// if it is already stored
		if(result !=null){	
			System.out.println("was stored already");
			
		}// if it is not stored, make it in datastore
		else{
			System.out.println("creating in db");
			Entity league = new Entity("UfcEvent",ufcKey);
			league.setProperty("Eventname", eventname);
			System.out.println("eventname"+eventname);
			


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
				e.setProperty("vote", 0);
				System.out.println("this is key" +e.getKey());
				System.out.println("this is id"+e.getKey().getId());
				
				datastore.put(e);
			}
			

		}
		
		//create object from datastore
		

		req.setAttribute("key", ufcKey);
		req.setAttribute("event", eventname);
		ServletContext sc = this.getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/ufc.jsp");
		
		//forward query to page
		rd.forward(req, resp);
		
		
		





	}


}

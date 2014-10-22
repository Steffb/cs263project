
import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import java.util.Date;
// ...


// The Worker servlet should be mapped to the "/worker" URL.
public class Worker extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String key = request.getParameter("key");
        String value =request.getParameter("value");
        System.out.println("i worker "+key);
        System.out.println("i worker "+value);
        // Do something with key.
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity taskdata = new Entity("TaskData", key);
        
        taskdata.setProperty("value", value);
        Date date = new Date();
       
        taskdata.setProperty("date", date);
        System.out.println("these are the properties "+taskdata.getProperties());
        datastore.put(taskdata);
        
        
    }
        
}
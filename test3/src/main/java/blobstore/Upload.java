package blobstore;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

public class Upload extends HttpServlet {
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
    	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


        System.out.println("in the upload");
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
        BlobKey blobKey = blobs.get("myFile");
        System.out.println(blobKey.toString());
        
        
        //find user add key to user as param
        Key UserKey = KeyFactory.createKey("UserEntity", req.getParameter("userkey"));
        Query query = new Query("UserEntity",UserKey);
        System.out.println(query);
        Entity result = datastore.prepare(query).asSingleEntity();
        result.setProperty("blobkey", blobKey);
        datastore.put(result);
        	
       

        if (blobKey == null) {
        	
            res.sendRedirect("/blob?error=nofile");
            System.out.println("there was no key");
        } else {
            //res.sendRedirect("/serve?blob-key=" + blobKey.getKeyString());
        	res.sendRedirect("/userpage.jsp");
        }
    }
}
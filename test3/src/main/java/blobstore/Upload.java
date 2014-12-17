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
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

/**
 * 
 * @author steffenfb
 * When user uploads a user picture, it gets the blobkey and serving url and saves it to the user entity
 * it sets the size of served picture to 200 pixels
 * And it deletes the old picture if there already was one.
 */


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
        ImagesService is = ImagesServiceFactory.getImagesService(); 
        ServingUrlOptions arg0 = ServingUrlOptions.Builder.withBlobKey(blobKey);
		
       
        String url = is.getServingUrl(arg0);
        
        
        // sets the picture to a standard size
        url+="=s200";
        //find user add key to user as param
        Key UserKey = KeyFactory.createKey("UserEntity", req.getParameter("userkey"));
        Query query = new Query("UserEntity",UserKey);
        System.out.println(query);
        Entity result = datastore.prepare(query).asSingleEntity();
        
        if(result.getProperty("blobkey")!=null){
        	blobstoreService.delete((BlobKey)result.getProperty("blobkey"));
        	
        }
        
        result.setProperty("blobkey", blobKey);
        result.setProperty("picurl", url);
        datastore.put(result);
        
        
        	
       

        if (blobKey == null) {
        	
            res.sendRedirect("/blob?error=nofile");
            System.out.println("there was no key");
        } else {
            
        	res.sendRedirect("/userpage.jsp");
        }
    }
}
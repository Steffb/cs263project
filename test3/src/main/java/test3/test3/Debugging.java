package test3.test3;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserService;
import com.google.apphosting.api.DatastorePb.GetResponse.Entity;

public class Debugging {

	
	public static void main(String[] args) {
		

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		String userId = "12129913918120959188";
		Key UserKey = KeyFactory.createKey("UserEntity", userId);

		try {
			com.google.appengine.api.datastore.Entity dsuser = datastore.get(UserKey);
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("notfound");
			e.printStackTrace();
		}
	}
}

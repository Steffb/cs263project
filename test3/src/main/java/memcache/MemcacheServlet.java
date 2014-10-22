package memcache;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class MemcacheServlet extends HttpServlet{


	public void doGet(HttpServletRequest req,
			HttpServletResponse resp)
					throws IOException {
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
		
		if (memcache.contains("mkey")) {
			System.out.println("something is cached");
			memcache.increment("mkey", 1);
			System.out.println("new cached value is "+memcache.get("mkey"));
			
			
		}else{
			
			System.out.println("nothing is cached");
			
			System.out.println("setting cache");
		memcache.put("mkey", 500, Expiration.byDeltaSeconds(30));
		}



	}
}

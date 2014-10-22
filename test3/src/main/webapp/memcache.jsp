<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.google.appengine.api.memcache.Expiration"%>
<%@ page import="com.google.appengine.api.memcache.MemcacheService"%>
<%@ page
	import="com.google.appengine.api.memcache.MemcacheServiceFactory"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Insert title here</title>
</head>
<body>


	<%

MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();

if (memcache.contains("mkey")) {
	Object temp= memcache.get("mkey");
	System.out.println("something is cached");
	memcache.increment("mkey", 1);
	System.out.println("new cached value is "+memcache.get("mkey"));
	%>
	<p> something is cached and its value is <%=temp %></p>
	
	<p> Wait 30 sek for cache to empty </p>
	
	<%
}else{
	
	System.out.println("nothing is cached");
	
	System.out.println("setting cache");
memcache.put("mkey", 500, Expiration.byDeltaSeconds(30));
%>
 <p> nothing is cached </p>
<%

}



%>

</body>
</html>
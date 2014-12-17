<%@page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>    
<%@include file="header.jsp" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@page import= "com.google.appengine.api.images.ImagesService"%>
<%@page import="com.google.appengine.api.images.ImagesServiceFactory" %>
<%@page import="com.google.appengine.api.blobstore.BlobKey" %>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="jumbotron">
<div class="container">
<br>
<%Entity result = null;

MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
User user = userService.getCurrentUser();


DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


if (user!= null){
String userId = user.getUserId();


Key UserKey = KeyFactory.createKey("UserEntity", userId);


System.out.println("this is the key we are searching"+UserKey);


Query query = new Query("UserEntity",UserKey);
System.out.println(query);
result = datastore.prepare(query).asSingleEntity();
BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

%>

<%
// need to fetch from linkdirectly
if(result !=null && result.getProperty("blobkey")!= null){
ImagesService is = ImagesServiceFactory.getImagesService(); 

BlobKey b = (BlobKey) result.getProperty("blobkey");%>

<h1>Here is your lovely picture</h1>
<img src="<%= is.getServingUrl(b)%>=s200" alt="Mountain View" >
<%
}else{
	
	%>
	<h1>Upload a profile picture</h1>
	<form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
	<input type="file" name="myFile">
	<input type="submit" value="Submit">
	<input type="hidden" name="userkey" value=<%=user.getUserId() %>>
	</form>
	
	<%
	
}


%>

<div style="padding:10%;">

<%

}
if(result !=null ){
	List<Entity> le;

if (!memcache.contains("savedmatches")){
Query query = new Query("Savematch", result.getKey());

le = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());

memcache.put("savedmatches", le);}
else{
	le =(List <Entity>) memcache.get("savedmatches");
	System.out.println("got matches from cache");
	
	
}
%>
	<div class="row">
	<h3>Your saved matches</h3>
	<%
	
	
for (Entity e: le){
	
	System.out.println(e);
	%>
		
	<div class="col-sm-6 col-md-4">
		<div class="panel panel-primary">
		<div class="panel-heading"><%=e.getProperty("event") %></div>
		<div class="panel-body"><%=e.getProperty("winner") %> VS. <%=e.getProperty("loser") %></div>
  		<div class="panel-footer"><%=e.getProperty("comment") %></div>
			</div>
		</div>	
	
	
	
	
	
	<%

}%></div></div><%

}


		//sjekker om brukeren er logget inn
		if (user != null) {//IF LOGGED INN
			pageContext.setAttribute("user", user);
		
		
		
		
		
		%>
		<!-- <h3>This is your mail <%=user.getEmail() %></h3>
		<h3>This is your nickname <%=user.getNickname() %></h3>
		<h3>This is your id <%=user.getUserId() %></h3>
		<h3>This is your fed ed <%=user.getFederatedIdentity() %></h3>
		<h3>This is your authdomain <%=user.getAuthDomain() %></h3>
		<h2>This is from session <%=session.getAttribute("SuserId") %></h2> -->
		
		
		<%
	
		
		if (memcache.contains("mail")) {
			
			
			System.out.println("found in cache");
			%><h4>This is from the cache:<%=memcache.get("mail")%></h4><%
				
		}else{
			
		memcache.put("mail",result.getProperty("mail"));%>
		
		
		
		<%if(result !=null ){ %>
		<p><%=result.getProperty("mail")%></p>
		<%

		}
		}%>
		
		
		
		
		<A HREF="/jsontest?sport=plranking&id=354">Get PL</A><br>
		<A HREF="/jsontest?sport=plranking&id=355">Get other league</A><br>
		<A HREF="/jsontest?sport=footballold&id=354">Get other league</A><br>
		
		<A HREF="/ufc?eventid=180">Get other league</A><br>
		
		
	
	
		
		
		
	

	<p>	<%// sender til loginpage og sender tilbake til denne iden når den er ferdig %>
		Hello, ${fn:escapeXml(user.nickname)}! (You can <a
			href="<%=userService.createLogoutURL(request.getRequestURI())%>">sign
			out</a>.)
	</p>
	<%
		} else { // IF NOT LOGGED INN:
	%>
	<p>
		Hello! <a
			href="<%=userService.createLoginURL(request.getRequestURI())%>">Sign
			in</a> to view this page
	</p>
	<%
		}
	%>
	
</div>

</div>
</body>
</html>
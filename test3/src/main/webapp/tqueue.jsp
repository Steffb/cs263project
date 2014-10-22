<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page
	import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.Entity"%>
<%@ page import="com.google.appengine.api.datastore.FetchOptions"%>
<%@ page import="com.google.appengine.api.datastore.Key"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="com.google.appengine.api.datastore.Query"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="/stylesheet/main.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Here is result</h1>

	<%
		String key = request.getParameter("key");
	if (key!= null){
		System.out.println( "this is the key "+key );
		Key qkey = KeyFactory.createKey("TaskData", key);
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query= new Query("TaskData", qkey);
		
		
		
		
		
		// Run an ancestor query to ensure we see the most up-to-date
		// view of the Greetings belonging to the selected Guestbook.
		//Query query = new Query("Greeting", guestbookKey).addSort("date",
				//Query.SortDirection.DESCENDING);
		List<Entity> results = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(5));
	
		
		
		%>
	<p>
		The task 
		<%= key %></p>
		
		<% 
		System.out.println(results.size());
		for (int i = 0; i < results.size(); i++) {
		 %>

			
			<p>It's value from datastore is <%= results.get(i).getProperty("value").toString()%> <br></p>
			
		<%} }%>
		






</body>
</html>
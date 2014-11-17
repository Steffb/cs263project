<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<div class="container">
<div class="starter-template"></div>
<h1>Here are the tasks</h1>
<A HREF="guestbook.jsp">The guestbook</A><br>
<a HREF="/task">Task example</a><br>
<a HREF="/test">All tasks from datastore</a><br>
<a HREF="/blob">Blobstore</a><br>
<a HREF="/mem">My memcahce</a><br>
<a HREF="/login">log in as a user</a><br>

<%
UserService userService = UserServiceFactory.getUserService();

String thisURL = request.getRequestURI();


//if user logged in
if (request.getUserPrincipal() != null) { %>


   <p>Hello<%= request.getUserPrincipal().getName() %>You can <br><a href="<%=userService.createLogoutURL("/index.jsp")%>">sign out</a>.</p>
   <a href="/userpage.jsp">Here is your userpage</a>
<%} else {// if the user is not logged in %>
    <p>Please <a href="<%=userService.createLoginURL(thisURL)%>">sign in</a>.</p>
<%} %>

</div>
</div>










</body>
</html>
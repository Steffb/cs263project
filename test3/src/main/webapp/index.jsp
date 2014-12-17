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
<div class="starter-template">

<br>
<br>
<br>
<br>



</div>


<%
//if user logged in
if (request.getUserPrincipal() != null) { %>
<p>Hello<%= request.getUserPrincipal().getName() %>You can <br><a href="<%=userService.createLogoutURL("/index.jsp")%>">sign out</a>.</p>
   <a href="/userpage.jsp">Here is your userpage</a>
<%} else {// if the user is not logged in, does not save in datastore %>
    <p>Please <a href="<%=userService.createLoginURL("/UserServlet")%>">sign in</a>.</p>
<%} %>

</div>
</div>










</body>
</html>
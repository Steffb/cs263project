<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>    

    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


<%

UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		//sjekker om brukeren er logget inn
		if (user != null) {//IF LOGGED INN
			pageContext.setAttribute("user", user);
		
		%>
		<h3>This is your mail <%=user.getEmail() %></h3>
		<h3>This is your nickname <%=user.getNickname() %></h3>
		<h3>This is your id <%=user.getUserId() %></h3>
		<h3>This is your fed ed <%=user.getFederatedIdentity() %></h3>
		<h3>This is your authdomain <%=user.getAuthDomain() %></h3>
		<h2>This is from session <%=session.getAttribute("SuserId") %></h2>
		
		
		<A HREF="/jsontest?sport=plranking&id=354">Get PL</A><br>
		

		
		
		
		
		<%
		
		
		
			
	%>

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
			in</a> to include your name with greetings you post.
	</p>
	<%
		}
	%>


</body>
</html>
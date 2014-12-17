<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
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


<%
DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

String lid=request.getParameter("id");

Key LeagueKey = KeyFactory.createKey("LeagueEntity", lid);

Query query=new Query("LeagueMatch").setAncestor(LeagueKey);

List <Entity> results = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());


System.out.println(results);

%>

<h1>Try to vote </h1>

<table>
<tr>
<th>
header

</th>
</tr>
<tbody><%



for(Entity r:results){
	
	
%>
<tr>
<td><%=r.getProperty("id") %></td><td> <%=r.getProperty("awayteam") %> </td><td> <%=r.getProperty("hometeam") %> </td><td> <%=r.getProperty("vote") %> </td>
<td>
<form name="myform" action="/jsontest" method="POST">
<input type="hidden" name="id" value=<%=r.getProperty("id") %> />

<input type="hidden" name="parentkind" value=<%=r.getParent().getKind() %> />
<input type="hidden" name="parent" value=<%=r.getParent() %> />
<input type="hidden" name="parentname" value=<%=r.getParent().getName() %> />
<input type="hidden" name="parentnamespace" value=<%=r.getParent().getNamespace() %> />

<input type="hidden" name="parentkind" value=<%=r.getParent().getKind() %> />
<input class="btn btn-danger" type="submit" value="Like match!"><br>

</form>

</td>

</tr>
<%
}
	
	 %>
</tbody>
</table>
</body>
</html>
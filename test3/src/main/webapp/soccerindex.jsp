<%@page import="objects.SoccerIndex" %>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="header.jsp" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Soccer indexpage</title>
</head>
<body>
<h1> This is soccer league</h1>
<%

SoccerIndex[] soccerindex =(SoccerIndex[]) request.getAttribute("index");


%>
<div class="container">
<h1><%=request.getAttribute("event") %></h1>
<table class="table table-hover">

<thead><th>League id  </th><th>League name</th><th>short</th><th>Year</th></thead>

<tbody>

<%for (int i = 0; i < soccerindex.length; i++) { %>


<tr class='clickableRow' href='/jsontest?sport=plranking&id=<%=soccerindex[i].id %>'><td><%=soccerindex[i].id %></td><td><%=soccerindex[i].caption%></td><td><%=soccerindex[i].league %></td><td>
<%=soccerindex[i].year%></td></tr> </div>



<%} %>


<script>
jQuery(document).ready(function($) {
    $(".clickableRow").click(function() {
          window.document.location = $(this).attr("href");
    });
});
</script>

</tbody>
</table>



</div>





</body>
</html>

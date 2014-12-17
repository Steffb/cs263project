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
<div class="container">
<h1> This is soccer league</h1>
<%

SoccerIndex[] soccerindex =(SoccerIndex[]) request.getAttribute("index");


%>
<div class="container">
<h1><%//=request.getAttribute("event") %></h1>
<table class="table table-hover">

<thead><th> </th><th>League name</th><th>Year</th></thead>

<tbody>

<%for (int i = 0; i < soccerindex.length; i++) { %>

<%

String s =soccerindex[i].caption.replaceAll("\\s","");

%>

<tr class='clickableRow' href='/jsontest?sport=plranking&id=<%=soccerindex[i].id %>&leaguename=<%=s%>'><td></td><td><%=soccerindex[i].caption%></td><td>
<%=soccerindex[i].year%></td>
<td>


</td>


</tr> </div>



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



</div>

</body>
</html>

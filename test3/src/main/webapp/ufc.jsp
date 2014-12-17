<%@page import="test3.test3.UfcEvent"%>
<%@page import="java.util.Random"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="header.jsp" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ufcpage</title>

	
</head>
<body>




<div class="container">

<h1><%=request.getAttribute("event") %></h1>

<table class="table table-hover">



<thead><th>Fighter one </th><th>Fighter two</th><th>Method</th><th>Stopped?</th><th>Votes</th></thead>

<tbody>

<% 



Random rnd = new Random();

DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();



Key k =(Key) request.getAttribute("key");

Query q = new Query("Fight", k);

String cleankey =k.getName().replace("\"", "");



List <Entity> results = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());





for (Entity e:results){

// To make it random who is in wich collum

String first;

String second;

if(rnd.nextBoolean()){

first =(String)e.getProperty("winner");

second=(String)e.getProperty("loser");


}else{

first =(String)e.getProperty("loser");

second=(String)e.getProperty("winner");


}



%>

<tr>


<td><%=first %></td> <td><%=second %></td><td><div class="ttoggle" style="visibility:hidden;" ><%=e.getProperty("method") %></div></td>

<td><div class="ttoggle" style="visibility:hidden;">




<%


Boolean b = (Boolean) e.getProperty("stopped");

if(b){ %>Yes!


<%}else{ %>No!<%} %>



</div></td> <td><%=e.getProperty("vote") %></td><td><button class="save">

<form style="display:none;" action="/UserServlet" method="post">

<input type="hidden" name="winner" value=<%=e.getProperty("winner") %> />

<input type="hidden" name="loser" value=<%=e.getProperty("loser") %> />

<input type="hidden" name="kind" value=<%=e.getKind() %> />

<input type="hidden" name="event" value=<%=request.getAttribute("event") %> />

<input type="hidden" name="type" value="save" />

<input type="hidden" name="url" value=<%=request.getParameter("eventurl") %> />

<input type="text" name="comment">

<input type="submit" value="Submit">

</form>

SaveIt!</button></td>






</tr>


<td>

<form action="/UserServlet" method="post">


<input type="hidden" name="parentkind" value="UfcEvent" />

<input type="hidden" name="parentname" value=<%=cleankey %> />

<input type="hidden" name="id" value=<%=e.getKey().getId()%> />

<input type="hidden" name="type" value="like" />

<input type="hidden" name="url" value=<%=request.getParameter("eventurl") %> />


<input type="submit" value="Vote!">

</form>

</td>




<% } %>

</tbody>

</table>



<h1>Comments made:</h1>



<table class="table table-hover">

<%



System.out.println("");

Query q2 = new Query("comment", k);



System.out.println("searching with key "+k.getKind()+"  "+k.getName());





List <Entity> results2 = datastore.prepare(q2).asList(FetchOptions.Builder.withDefaults());

for (Entity e:results2){

System.out.println("fount entity");

%>

<tr><td><%=e.getProperty("content")	 %></td><td><%=e.getProperty("user")	 %></td></tr>



<%

}

%>

</table>

<button class="comment">

<form style="display:none;" action="/UserServlet" method="post">


<input type="hidden" name="parentname" value= <%=cleankey%>>

<input type="hidden" name="parentkind" value="UfcEvent" />


<input type="hidden" name="type" value="comment" />

<input type="hidden" name="url" value=<%=request.getParameter("eventurl") %> />


<input type="text" name="comment">

<input type="submit" value="Submit">

</form>


Comment!</button>

</div>









<script>



$(document).ready(function(){

  

  $("td").click(function(){

 

  console.log("td clicked");

  $(this).find(".ttoggle").css('visibility','visible');



  });

  

  $(".save").click(function(){

    $(this).find("form").show("slow");

  });

  $(".comment").click(function(){

    $(this).find("form").show("slow");

  });

  

  

  

});

</script>





</body>
</html>
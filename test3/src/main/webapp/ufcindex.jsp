<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@include file="header.jsp" %>
<%@ page import="objects.EventIndex" %>
<%@ page import="test3.test3.Datascraper" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ufc index</title>
</head>
<body>


<%  
MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
List<EventIndex> eilist;
if(!memcache.contains("fightindexlist")){

eilist= Datascraper.createIndex();

memcache.put("fightindexlist", eilist);

}else{
	
	eilist =(List<EventIndex>) memcache.get("fightindexlist");
	
}

	
		%>




<div class="container">

<h1>Select event you want to see here </h1>


<table class="table table-hover">

<thead><th>UFC Event  </th></thead>

<tbody>

<%for (EventIndex ei: eilist){ %>


<tr class='clickableRow' href='/ufc?eventurl=<%=ei.url%>'><td><%=ei.name %></td></tr> </div>



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
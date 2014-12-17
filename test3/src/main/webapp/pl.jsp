<%@page
	import="com.google.appengine.api.datastore.EntityNotFoundException"%>
<%@page import="test3.test3.JsonServlet"%>
<%@page import="test3.test3.PlJson"%>
<%@page import="org.glassfish.jersey.process.internal.RequestScope"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>
<%@page import="java.util.Random"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>

<%@ page import="com.google.gson.Gson"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Premier League table</title>

<style>
.comments {
    background-color: linen;
}

</style>
</head>
<body>
	<%	User user = userService.getCurrentUser();
		
		PlJson[] pp = (PlJson[]) request.getAttribute("league");
	%>


<div class="container">

	<h1>The currently played matched in</h1>
	<h1><%=request.getAttribute("leaguename")%></h1>
	
	</div>
	<table class="table table-hover">

		
		<%
			int matchday = pp[pp.length - 1].matchday;
			int len = pp.length;
			//showing latest matches first
			for (int i = len - 1; i >= 0; i--) {
				PlJson plm = pp[i];
				if (plm.matchday != matchday && plm.isPlayed()) {
					//adds the matchday header
					matchday = plm.matchday;
		%>
		<tr>
			<th></th>
			<th></th>
			<th>Match day <%=plm.matchday%></th>
		</tr>
		
		<tr>
			<th>hometeam</th>
			<th>HTgoal</th>
			<th>awayteam</th>
			<th>ATgoal</th>
			<th>Was goal</th>
			<th>goalcount</th>
			<th>Comment</th>
		</tr>
		


		<%
			}
				if (plm.isPlayed()) {
		%>

		<tr>

			<div class=<%=plm.matchday%>>
				<td><%=plm.homeTeam%></td>

				<td><div class="ttoggle" style="visibility: hidden;"><%=plm.goalsHomeTeam%></div></td>
				<td><%=plm.awayTeam%></td>

				<td><div class="ttoggle" style="visibility: hidden;"><%=plm.goalsAwayTeam%></div></td>

				<td><div class="ttoggle" style="visibility: hidden;">
				<%if(plm.wasGoal()){ %>
						Yes!
						<%}else{%> No!<%} %>
						</div></td>

				<td><div class="ttoggle" style="visibility: hidden;"><%=plm.goalCount()%></div></td>



				<td><button class="comment">

						<form style="display: none;" action="/jsontest" method="post">


							<input type="hidden" name="parentname" value=<%=request.getAttribute("lid")%>>
							<input type="hidden" name="id" value=<%=plm.id%>>
							<input type="hidden" name="leaguename" value=<%=request.getAttribute("leaguename")%>>
							<input type="hidden" name="iscomment" value="yes">
							
							<input type="hidden" name="sender" value=<%=user.getNickname() %>>
							<input type="hidden" name="parentkind" value="LeagueEntity" /> 
							<input type="hidden" name="type" value="comment" /> 
								 <input type="text" name="comment"> 
								 <input type="submit" value="Submit">

						</form>


						Comment!
					</button></td> 
					
					<td>  
					
					
					<form action="/jsontest" method="post">


							<input type="hidden" name="parentname" value=<%=request.getAttribute("lid")%>>
							
							<input type="hidden" name="leaguename" value=<%=request.getAttribute("leaguename")%>>
							<input type="hidden" name="iscomment" value="no">
								 <input type="submit" value="Showcomments">

						</form>
					</td>

		</tr>
		<tr><td class="comments" style="display:none">Comments: </td><td class="comments2" style="display:none">
		
		
		
		 <%for (String s: plm.comments){ %>
				<%=s%><br>
				<%} %> 
		
		
		
		
		<td>
		</tr>
		<%
			}
			}  %>
	</table>

	</div>

	<script>


$(document).ready(function(){
	
	$(".comments").fadeIn(1000);
	$(".comments2").fadeIn(2000);


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
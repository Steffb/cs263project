<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


    <p>Enqueue a value, to be processed by a worker.</p>
   
   
    
    <form action="/enqueue" method="post">	
      <input type="text" name="key">
      <input type="text" name="value">
      <input type="submit">
    </form>

</body>
</html>





<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"  %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

 <% Boolean b = (Boolean)session.getAttribute("result");
        if ( b!=null && b.booleanValue() ){
    %>
    	<h1> Welcome <c:out value="${param.fname}"></c:out> </h1>
             Captcha Matched ! Successfully signed up!!<br/>
             
             First Name:<c:out value="${param.fname}"></c:out><br/>  
			 Last Name:<c:out value="${param.lname}"></c:out>/
			 <c:out value="${param.background}"></c:out><br/> 
			 <c:out value="${param.count}"></c:out><br/> 
			 <c:out value="${param.deformation}"></c:out><br/> 
			 <c:out value="${param.complexity}"></c:out><br/> 
			 
    <% } else { %>
             Captcha did not match. Please retry !
             <% request.getRequestDispatcher( "index.jsp").forward( request, response ); %>
    <% } %>
 
</body>
</html>
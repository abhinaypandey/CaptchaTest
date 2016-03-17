<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.amgen.sharedservices.captcha.CaptchaService" import="javax.servlet.jsp.PageContext"%>

<%@ taglib uri="tags" prefix="m" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page trimDirectiveWhitespaces="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Captcha Service</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#reload_btn").click(function(){
			document.location.reload();
		});
		
		
	});
	
	function generateSound(surl) {
		$("#dummyspan").html( "<embed src='"+surl+"' hidden=true autostart=false loop=false>");
	};
</script>
</head>

<body>
	<h1>Captcha Test</h1>
	<form action="${pageContext.request.contextPath}/ImageCaptchaServlet" method="post"> 
		<table>
			<tr><td>FirstName:<input type="text" name="fname"/></td></tr>
			<tr><td>LastName:<input type="text" name="lname"/></td></tr>
			<tr><td><m:captcha captchaChars="7" bgColor="gold" complexity="medium" background="true" deformation="true" soundCaptcha="enabled" caseSensitivity="disabled" sessionId="<%= session.getId() %>" ></m:captcha></td></tr>
			<tr><td><input type="text" name="captcha_response" /></td></tr>
			<tr><td><input type="hidden" name="captchaID" value="<%= session.getId() %>"/></td></tr>
			<tr><td><input id="sbtn" type="submit" value="submit"/> </td></tr>
		</table>
	</form>

</body>
</html>
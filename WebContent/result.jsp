<!DOCTYPE html> 
<html>
<head>
<title>JCaptcha Demo - Result</title>
</head>
<body>
    <% Boolean b = (Boolean)session.getAttribute("result");
        if ( b!=null && b.booleanValue() ){
    %>
             Congrats!  You passed the Captcha test!
    <% } else { %>
             Sorry, you failed the Captcha test.
    <% } %>
</body>
</html>
<%@ page
    language="java" 
    contentType="text/html;charset=UTF-8" 
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"
%>
<jsp:include page="/header.jsp"/>

<h1>로그인</h1>
<form action='/auth/login'>
    <p>이메일: <input name='email' type='email'></p>
    <p>암호: <input name='password' type='password'></p>
    <input type='submit' value='로그인'>
</form>
</body>
</html>
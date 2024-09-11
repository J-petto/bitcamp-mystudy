<%@ page
    language="java" 
    contentType="text/html;charset=UTF-8" 
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/header.jsp"/>

<h1>로그인</h1>
<form action='/auth/login' method="post">
    <p>이메일: <input name='email' type='email' value="${cookie.email.value}"></p>
    <p>암호: <input name='password' type='password'></p>
    <p><input type="checkbox" name="saveEmail" ${empty cookie.email.value ? "" : "checked"}> 이메일 저장</p>
    <input type='submit' value='로그인'>
</form>
</body>
</html>
<%@ page
        language="java"
        contentType="text/html;charset=UTF-8"
        pageEncoding="UTF-8"
        trimDirectiveWhitespaces="true"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/header.jsp"/>

<h1>프로젝트 등록</h1>
<form action='add' method="post">
    <p>타이틀: <input name='title' type='text'></p>
    <p>내용: <textarea name='description'></textarea></p>
    <p>시작일: <input name='startDate' type='date'></p>
    <p>종료일: <input name='endDate' type='date'></p>
    팀원:<br>
    <ul>
        <c:forEach items="users" var="user">
            <li><input name='member' value='${user.no}' type='checkbox'>${user.name}</li>
        </c:forEach>
    </ul>
    <input type='submit' value='등록하기'>
</form>
</body>
</html>
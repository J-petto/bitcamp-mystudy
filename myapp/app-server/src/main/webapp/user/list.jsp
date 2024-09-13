<%@ page
        language="java"
        contentType="text/html;charset=UTF-8"
        pageEncoding="UTF-8"
        trimDirectiveWhitespaces="true"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/header.jsp"/>

<h1>회원 목록</h1>
<p><a href='add'>회원 등록</a></p>
<table>
    <thead>
    <tr>
        <th>번호</th>
        <th>이름</th>
        <th>이메일</th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${list}" var="user">
        <tr>
            <%-- <%= 리턴 값을 넣을 땐 이렇게 사용함 %> --%>
            <td>${user.no}</td>
            <td><a href='view?no=${user.no}'>${user.name}</a></td>
            <td>${user.email}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
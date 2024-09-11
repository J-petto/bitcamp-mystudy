<%@ page
        language="java"
        contentType="text/html;charset=UTF-8"
        pageEncoding="UTF-8"
        trimDirectiveWhitespaces="true"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/header.jsp"/>

<h1>회원 조회</h1>

<c:if test="${empty user}">
    <p>없는 회원입니다.</p>
</c:if>

<c:if test="${not empty user}">
    <form action='/user/update' method="post">
        <p>번호: <input name='no' type='text' value='${user.no}' readonly></p>
        <p>이름: <input name='name' type='text' value='${user.name}'></p>
        <p>이메일: <input name='email' type='email' value='${user.email}'></p>
        <p>암호: <input name='password' type='password'></p>
        <p>연락처: <input name='tel' type='tel' value='${user.tel}'></p>
        <button>변경하기</button>
        <button type='button' onclick='location.href="/user/delete?no=${user.no}"'>삭제하기</button>
    </form>
</c:if>
</body>
</html>
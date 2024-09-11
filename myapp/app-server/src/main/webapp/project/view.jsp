<%@ page
        language="java"
        contentType="text/html;charset=UTF-8"
        pageEncoding="UTF-8"
        trimDirectiveWhitespaces="true"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/header.jsp"/>

<h1>프로젝트 조회</h1>

<c:if test="${empty project}">
    <p>없는 프로젝트입니다.</p>
</c:if>

<c:if test="${not empty project}">
    <form action='/project/update' method="post">
        <p>번호: <input name='no' type='text' value='${project.no}' readonly></p>
        <p>타이틀: <input name='title' type='text' value='${project.title}'></p>
        <p>내용: <textarea name='description'>${project.description}</textarea></p>
        <p>시작일: <input name='startDate' type='date' value='${project.startDate}'></p>
        <p>종료일: <input name='endDate' type='date' value='${project.endDate}'></p>
        팀원:<br>
        <ul>
            <c:forEach items="${users}" var="user">
            <li>
                <input name='member' value='${user.no}' type='checkbox'
                       ${project.members.contains(user) ? "checked" : ""}
<%--                <c:forEach items="${project.members}" var="member">
                           <c:if test="${member.equals(user)}">checked</c:if>
                    </c:forEach>--%>
                >${user.name}
            </li>
            </c:forEach>
        </ul>
        <button>변경하기</button>
        <button type='button' onclick='location.href="/project/delete?no=${project.no}"'>삭제하기</button>
    </form>
</c:if>
</body>
</html>
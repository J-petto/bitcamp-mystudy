<%@ page
        language="java"
        contentType="text/html;charset=UTF-8"
        pageEncoding="UTF-8"
        trimDirectiveWhitespaces="true"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/header.jsp"/>

<h1>프로젝트 등록</h1>
<c:if test="${empty project}">
    <p>등록할 프로젝트 정보가 없습니다.</p>

</c:if>
<c:if test="${not empty project}">
    <form action='/project/add' method="post">
        <p>프로젝트명: ${project.title}
        </p>
        <p>내용: <textarea readonly>${project.description}</textarea></p>
        <p>기간: ${project.startDate} ~ ${project.endDate}
        </p>
        팀원:<br>
        <ul>
            <c:forEach items="${project.members}" var="user">
                <li>${user.name}(${user.email})</li>
            </c:forEach>
        </ul>
        <button>등록</button>
    </form>
</c:if>
</body>
</html>
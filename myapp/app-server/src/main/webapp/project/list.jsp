<%@ page
        language="java"
        contentType="text/html;charset=UTF-8"
        pageEncoding="UTF-8"
        trimDirectiveWhitespaces="true"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/header.jsp"/>

<h1>프로젝트 목록</h1>
<p><a href='/project/form1'>프로젝트 등록</a></p>
<table>
    <thead>
    <tr>
        <th>번호</th>
        <th>프로젝트</th>
        <th>기간</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="project">
        <tr>
            <td>${project.no}</td>
            <td><a href='/project/view?no=${project.no}'>${project.title}</a></td>
            <td>${project.startDate} ~ ${project.endDate}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
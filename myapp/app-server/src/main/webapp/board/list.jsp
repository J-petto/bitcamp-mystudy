<%@ page
    language="java" 
    contentType="text/html;charset=UTF-8" 
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/header.jsp"/>

<h1>게시글 목록</h1>
<p><a href='/board/add'>게시글 등록</a></p>
<table>
    <thead>
        <tr><th>번호</th><th><a>제목</a></th><th>작성자</th><th>작성일</th><th>조회수</th></tr>
    </thead>
    <tbody>

    <c:forEach items="${list}" var="board">
        <tr>
            <%-- <%= 리턴 값을 넣을 땐 이렇게 사용함 %> --%>
            <td>${board.no}</td>
            <td><a href='/board/view?no=${board.no}'>${board.title} </a></td>
            <td>${board.writer.name}</td>
            <td><fmt:formatDate value="${board.createdDate}" pattern="yyyy-MM-dd"/></td>
            <td>${board.viewCount}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
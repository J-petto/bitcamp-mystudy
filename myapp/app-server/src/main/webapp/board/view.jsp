<%@ page
        language="java"
        contentType="text/html;charset=UTF-8"
        pageEncoding="UTF-8"
        trimDirectiveWhitespaces="true"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/header.jsp"/>

<h1>게시글 조회</h1>
<c:if test="${empty board}">
    <p>없는 게시글입니다.</p>
</c:if>

<c:if test="${not empty board}">
    <form action='update' method="post" enctype="multipart/form-data">
        <p>번호: <input name='no' type='text' value='${board.no}' readonly></p>
        <p>제목: <input name='title' type='text' value='${board.title}'></p>
        <p>내용: <textarea name='content'>${board.content}</textarea></p>
        <p>작성일: <input type='text' value='<fmt:formatDate value="${board.createdDate}" pattern="yyyy-MM-dd hh:mm:ss"/>' readonly></p>
        <p>조회수: <input type="text" value="${board.viewCount}" readonly></p>
        <p>작성자: <input type="text" value="${board.writer.name}" readonly></p>
        첨부파일: <br>

        <c:if test="${board.attachedFiles.size() > 0}">
            <ul>
                <c:forEach items="${board.attachedFiles}" var="attachedFile">
                    <li>
                        <a href="download?path=board&fileNo=${attachedFile.fileNo}">${attachedFile.originFilename}</a>
                        <a href="file/delete?boardNo=${board.no}&fileNo=${attachedFile.fileNo}">[삭제]</a>
                    </li>
                </c:forEach>
            </ul>
        </c:if>

        <p><input name="files" type="file" multiple></p>
        <button>변경하기</button>
        <button type='button' onclick='location.href="delete?no=${board.no}"'>삭제하기</button>
    </form>
</c:if>

</body>
</html>
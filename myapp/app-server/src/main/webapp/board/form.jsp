<%@ page
    language="java" 
    contentType="text/html;charset=UTF-8" 
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"
%>
<%@ page import="bitcamp.myapp.vo.Board"%>

<jsp:include page="/header"/>

<h1>게시판 등록</h1>
    <form action='/board/add'>
        <p>제목: <input name='title' type='text'></p>
        <p>내용: <textarea name='content'></textarea> </p>
        <input type='submit' value='등록하기'>
    </form>

</body>
</html>
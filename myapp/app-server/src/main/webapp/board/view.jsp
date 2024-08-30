<%@ page
    language="java" 
    contentType="text/html;charset=UTF-8" 
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"
%>
<%@ page import="bitcamp.myapp.vo.Board"%>

<jsp:include page="/header"/>

<h1>게시글 조회</h1>
<%
Board board = (Board) request.getAttribute("board");
if (board == null) {
%>

<p>없는 게시글입니다.</p> 

<%
} else {
%>
<form action='/board/update'>
<p>번호: <input name='no' type='text' value='<%= board.getNo()%>' readonly></p>
<p>제목: <input name='title' type='text' value='<%= board.getTitle()%>'></p>
<p>내용: <textarea name='content'><%= board.getContent()%></textarea> </p>
<p>작성일: <input type='text' value='<%=String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", board.getCreatedDate())%>' readonly> </p>
<button>변경하기</button>
<button type='button' onclick='location.href="/board/delete?no=<%= board.getNo()%>"'>삭제하기</button>
</form>
<%
}
%>
</body>
</html>
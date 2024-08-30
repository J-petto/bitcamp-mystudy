<%@ page
    language="java" 
    contentType="text/html;charset=UTF-8" 
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"
%>
<%@ page import="bitcamp.myapp.vo.Board"%>
<%@ page import="java.util.List"%>

<jsp:include page="/header.jsp"/>

<h1>게시글 목록</h1>
<p><a href='/board/form'>게시글 등록</a></p>
<table>
    <thead>
        <tr><th>번호</th><th>제목</a></th><th>작성자</th><th>작성일</th><th>조회수</th></tr>
    </thead>
    <tbody>
<%-- <% 스크립트릿이라고함. %> - 자바 코드를 사용할 수 있게 만듦 --%>
<%
List<Board> list = (List<Board>) request.getAttribute("list");
for (Board board : list) {
%>
    <tr>
    <%-- <%= 리턴 값을 넣을 땐 이렇게 사용함 %> --%>
    <td><%= board.getNo()%></td>
    <td><a href='/board/view?no=<%= board.getNo() %>'> <%=board.getTitle()%> </a></td>
    <td><%= board.getWriter().getName()%></td>
    <td><%= board.getCreatedDate()%></td>
    <td><%= board.getViewCount()%></td>
    </tr>
<%
}
%>
    </tbody>
</table>
</body>
</html>
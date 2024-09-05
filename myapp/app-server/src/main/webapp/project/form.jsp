<%@ page
    language="java" 
    contentType="text/html;charset=UTF-8" 
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"
%>
<%@ page import="bitcamp.myapp.vo.User"%>
<%@ page import="java.util.List"%>

<jsp:include page="/header.jsp"/>

<h1>프로젝트 등록</h1>
    <form action='/project/add' method="post">
    <p>타이틀: <input name='title' type='text'></p>
    <p>내용: <textarea name='description'></textarea></p>
    <p>시작일: <input name='startDate' type='date'></p>
    <p>종료일: <input name='endDate' type='date'></p>
    팀원:<br>
    <ul>
<%
List<User> users = (List<User>) request.getAttribute("users");
for(User user : users){
%>
<li><input name='member' value='<%= user.getNo()%>' type='checkbox'><%= user.getName()%></li>
<%
}
%>
</ul>
<input type='submit' value='등록하기'>
</form>
</body>
</html>
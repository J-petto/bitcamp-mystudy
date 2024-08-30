<%@ page
    language="java" 
    contentType="text/html;charset=UTF-8" 
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"
%>
<%@ page import="bitcamp.myapp.vo.User"%>
<%@ page import="java.util.List"%>

<jsp:include page="/header.jsp"/>

<h1>회원 목록</h1>
<p><a href='/user/form'>회원 등록</a></p>
<table>
    <thead>
        <tr><th>번호</th><th>이름</th><th>이메일</th></tr>
    </thead>
    <tbody>
<%-- <% 스크립트릿이라고함. %> - 자바 코드를 사용할 수 있게 만듦 --%>
<%
List<User> list = (List<User>) request.getAttribute("list");
for (User user : list) {
%>

    <tr>
    <%-- <%= 리턴 값을 넣을 땐 이렇게 사용함 %> --%>
    <td><%= user.getNo()%></td>
    <td><a href='/user/view?no=<%= user.getNo() %>'> <%=user.getName()%> </a></td>
    <td><%= user.getEmail()%></td>
    </tr>

<%
}
%>
    </tbody>
</table>
</body>
</html>
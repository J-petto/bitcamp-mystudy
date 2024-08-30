<%@ page
    language="java" 
    contentType="text/html;charset=UTF-8" 
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"
%>
<%@ page import="bitcamp.myapp.vo.User"%>

<jsp:include page="/header.jsp"/>

<h1>회원 조회</h1>
<%
User user = (User) request.getAttribute("user");
if (user == null) {
%>

<p>없는 회원입니다.</p> 

<%
} else {
%>
<form action='/user/update'>
<p>번호: <input name='no' type='text' value='<%= user.getNo()%>' readonly></p>
<p>이름: <input name='name' type='text' value='<%= user.getName()%>'></p>
<p>이메일: <input name='email' type='email' value='<%= user.getEmail()%>'></p>
<p>암호: <input name='password' type='password'></p>
<p>연락처: <input name='tel' type='tel' value='<%= user.getTel()%>'></p>
<button>변경하기</button>
<button type='button' onclick='location.href="/user/delete?no=<%= user.getNo()%>"'>삭제하기</button>
</form>
<%
}
%>
</body>
</html>
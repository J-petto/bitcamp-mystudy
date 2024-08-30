<%@ page
    language="java" 
    contentType="text/html;charset=UTF-8" 
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"
%>
<%@ page import="bitcamp.myapp.vo.Project"%>
<%@ page import="bitcamp.myapp.vo.User"%>
<%@ page import="java.util.List"%>

<%!
private boolean isMember(List<User> members, User user){
    for(User member : members){
      if(member.equals(user)) return true;
    }
    return false;
}
%>

<jsp:include page="/header.jsp"/>

<h1>프로젝트 조회</h1>
<%
Project project = (Project) request.getAttribute("project");
if (project == null) {
%>
<p>없는 프로젝트입니다.</p> 
<%
} else {
%>
<form action='/project/update'>
<p>번호: <input name='no' type='text' value='<%= project.getNo()%>' readonly></p>
<p>타이틀: <input name='title' type='text' value='<%= project.getTitle()%>'></p>
<p>내용: <textarea name='description'><%= project.getDescription()%></textarea></p>
<p>시작일: <input name='startDate' type='date' value='<%= project.getStartDate()%>'></p>
<p>종료일: <input name='endDate' type='date' value='<%= project.getEndDate()%>'></p>
팀원:<br>
<ul>

<%

List<User> users = (List<User>) request.getAttribute("users");
for(User user : users){

%>
<li>
<input name='member' value='<%= user.getNo()%>' type='checkbox' <%= isMember(project.getMembers(), user) ? "checked" : ""%>><%=user.getName()%>
</li>

<%
}
%>

</ul>
<button>변경하기</button>
<button type='button' onclick='location.href="/project/delete?no=<%= project.getNo()%>"'>삭제하기</button>
</form>
<%
}
%>
</body>
</html>
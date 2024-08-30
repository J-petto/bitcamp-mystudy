<%-- res.setContentType("text/html;charset=UTF-8"); JSP에서는 해당 코드를 아래처럼 사용(contentType)--%>
<%@ page 
    language="java" 
    contentType="text/html;charset=UTF-8" 
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"    
%>
<%-- req.getRequestDispatcher("/header").include(req, res); JSP에서는 해당 코드를 아래처럼 사용--%>
<jsp:include page="header.jsp"/>

<h1>어서오세요.</h1>
<p>이 사이트는 팀 프로젝트를 관리하는 서비스를 제공합니다.</p>

</body>
</html>
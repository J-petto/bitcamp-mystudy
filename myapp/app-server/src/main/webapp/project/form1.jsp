<%@ page
        language="java"
        contentType="text/html;charset=UTF-8"
        pageEncoding="UTF-8"
        trimDirectiveWhitespaces="true"
%>

<jsp:include page="/header.jsp"/>

<h1>프로젝트 등록 - 기본 정보</h1>
<form action='form2' method="post">
    <p>타이틀: <input name='title' type='text'></p>
    <p>내용: <textarea name='description'></textarea></p>
    <p>시작일: <input name='startDate' type='date'></p>
    <p>종료일: <input name='endDate' type='date'></p>
    <button>다음</button>
</form>
</body>
</html>
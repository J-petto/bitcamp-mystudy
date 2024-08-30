<%@ page
    language="java" 
    contentType="text/html;charset=UTF-8" 
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"
%>
<jsp:include page="/header"/>

<h1>회원 등록</h1>
    <form action='/user/add'>
        <p>이름: <input name='name' type='text'></p>
        <p>이메일: <input name='email' type='email'></p>
        <p>암호: <input name='password' type='password'></p>
        <p>연락처: <input name='tel' type='tel'></p>
        <input type='submit' value='등록하기'>
    </form>
</body>
</html>
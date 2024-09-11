<%@ page
    language="java" 
    contentType="text/html;charset=UTF-8" 
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"
%>

<jsp:include page="/header.jsp"/>

<h1>게시판 등록</h1>
    <form action='/board/add' method="post" enctype="multipart/form-data">
        <p>제목: <input name='title' type='text'></p>
        <p>내용: <textarea name='content'></textarea> </p>
        <p>첨부파일: <input name="files" type="file" multiple></p>
        <input type='submit' value='등록하기'>
    </form>

</body>
</html>
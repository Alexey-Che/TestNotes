<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Notes Application</title>
</head>
<body>
    <center>
        <h1>Notes Application</h1>
        <h2>
            <a href="new">Add New Note</a>
        </h2>
    </center>
    <div align="center">
                <form action="search" method="post">
                    <input type="text" name="searchText" size="50" />
                    <input type="submit" value="Search" />
               </form>
    </div>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of Notes</h2></caption>
            <tr>
                <th>Title</th>
                <th>Text</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="note" items="${listNotes}">
                <tr>
                    <td><c:out value="${note.title}" /></td>
                    <td><c:out value="${note.text}"/></td>
                    <td>
                        <a href="delete?id=<c:out value='${note.id}' />">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
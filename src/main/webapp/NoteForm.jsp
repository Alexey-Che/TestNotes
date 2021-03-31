<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Notes Application</title>
</head>
<body>
    <center>
        <h1>Notes Management</h1>
    </center>
    <div align="center">
        <form action="insert" method="post">
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                        Add New Note
                </h2>
            </caption>

            <tr>
                <th>Title: </th>
                <td>
                    <input type="text" name="title" size="45"
                            value="<c:out value='${note.title}' />"
                        />
                </td>
            </tr>
            <tr>
                <th>Text: </th>
                <td>
                    <input type="text" name="text" size="45"
                            value="<c:out value='${note.text}' />"
                    />
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Save" />
                </td>
            </tr>
        </table>
        </form>
    </div>
</body>
</html>
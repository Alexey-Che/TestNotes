<%@ page import ="java.util.*" %>
<!DOCTYPE html>
<html>
<body>
<center>
    <h1>
        Notes
    </h1>
        <%
List result= (List) request.getAttribute("notes");
Iterator it = result.iterator();


while(it.hasNext()){

out.println(it.next()+"<br>");

}

%>
</body>
</html>
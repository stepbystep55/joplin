<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Top</title>
</head>
<body>
<h1>
	Hello world!  
</h1>
<P>
	<c:forEach items="${userDisplays}" var="userDisplay">
		<c:out value="${userDisplay.name}"/>
	</c:forEach>
</P>
</body>
</html>

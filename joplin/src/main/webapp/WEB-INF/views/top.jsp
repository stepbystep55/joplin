<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Top</title>
	<%@ include file="_head_base.jsp"%>
</head>
<body>

<div id="header">
	<h1>Viva Joplin!</h1>
	<div id="user_info">
	<a href="${userDisplay.profileUrl}">
		<img alt="profile image" src="${userDisplay.imageUrl}">
		<c:out value="${userDisplay.name}"/>
	</a>
	</div>
</div>

<div id="body">
	<h2>VS</h2>
</div>

<div id="footer">
	&copy;ippoippo
</div>

</body>
</html>

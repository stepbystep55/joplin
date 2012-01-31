<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="_head_base.jsp"%>
	<title>Sign in</title>
</head>
<body>
<h1>Sign in</h1>
<h2>Facebook</h2>
<form:form action="signin/facebook" method="post">
	<input type="hidden" name="scope" value="email,read_stream,offline_access" />
	<input type="submit" name="connectFacebook" value="connect" />
</form:form>
</body>
</html>

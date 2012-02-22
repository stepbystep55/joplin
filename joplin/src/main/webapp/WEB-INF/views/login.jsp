<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="_headBase.jsp"%>
	<title>Login</title>
</head>
<body>

<div id="header">
</div>

<div id="body">
	<h2>Sign in</h2>
	<h3>with Facebook</h3>
	<form:form action="signin/facebook" method="post">
		<input type="hidden" name="scope" value="user_likes,publish_stream,offline_access" />
		<input type="submit" name="connectFacebook" value="connect" />
	</form:form>
	<h3>with Twitter</h3>
	<form:form action="signin/twitter" method="post">
		<input type="submit" name="connectTwitter" value="connect" />
	</form:form>
</div>

<div id="footer">
	<jsp:include page="_footer.jsp"/>
</div>

</body>
</html>

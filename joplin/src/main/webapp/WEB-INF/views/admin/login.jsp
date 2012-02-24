<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="../_headBase.jsp"%>
	<title>Admin Login</title>
</head>
<body>

<div id="header">
</div>

<div id="body">
	<h3>
		<c:if test="${failed}"><span class="confirm"><spring:message code="login.failed" /></span></c:if>
	</h3>

	<h1>Admin login</h1>
	<form action="login" method="post">
		<input type="password" name="password" size="16" maxlength="32" />
		<input type="submit" name="login" value="login" />
	</form>
</div>

<div id="footer">
	<jsp:include page="../_footer.jsp"/>
</div>

</body>
</html>

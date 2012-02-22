<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
	<title>article new</title>
	<%@ include file="../_headBase.jsp"%>
</head>
<body>

<div id="header">
	<c:import url="${request.contextPath}/header" />
</div>

<div id="body">
	<h2>article new</h2>
	<form:form modelAttribute="article" action="create" method="post">
		subject:
		<form:input path="subject" size="32" maxlength="128"/>
		<spring:hasBindErrors name="article"><form:errors path="subject" cssStyle="color:red" /></spring:hasBindErrors>
		<br/>
		<input type="submit" />
	</form:form>

</div>

<div id="footer">
	<jsp:include page="../_footer.jsp"/>
</div>

</body>
</html>

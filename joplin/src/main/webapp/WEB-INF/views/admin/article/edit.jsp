<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<title>article edit</title>
	<%@ include file="../../_headBase.jsp"%>
</head>
<body>

<div id="header">
	<jsp:include page="../header.jsp"/>
</div>

<div id="body">
	<h3>
		<c:if test="${created}"><span class="confirm"><spring:message code="result.created" /></span></c:if>
		<c:if test="${updated}"><span class="confirm"><spring:message code="result.updated" /></span></c:if>
	</h3>

	<h1>article edit</h1>
	<form:form modelAttribute="article" action="delete" method="post">
		<form:hidden path="id" />
		<input type="submit" value="delete" />
	</form:form>
	<form:form modelAttribute="article" action="update" method="post">
		subject:
		<form:input path="subject" size="32" maxlength="128"/>
		<form:radiobutton path="active" value="true"/>active
		<form:radiobutton path="active" value="false"/>inactive
		<input type="submit" value="update" />
		<spring:hasBindErrors name="article"><form:errors path="subject" cssStyle="color:red" /></spring:hasBindErrors>
	</form:form>

	<h1>item list</h1>
	<c:if test="${(items != null) && !(empty items)}">
	<ol>
		<c:forEach items="${items}" var="item">
		<li>
			<span class="colId">
				<fmt:formatNumber value="${item.id}" pattern="000"/>
			</span>
			<span class="colVal">
				<iframe width="360" height="213" src="http://www.youtube.com/embed/${item.videoId}?rel=0" frameborder="0" allowfullscreen></iframe>
			</span>
		</li>
		</c:forEach>
	</ol>
	</c:if>
	<ul>
		<li>
			<form action="newItem" method="post">
				<input type="submit" value="new item" />
			</form>
		</li>
	</ul>

</div>

<div id="footer">
	<jsp:include page="../../_footer.jsp"/>
</div>

</body>
</html>

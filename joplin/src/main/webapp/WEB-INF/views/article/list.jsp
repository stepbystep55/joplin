<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
	<title>article list</title>
	<%@ include file="../_headBase.jsp"%>
</head>
<body>

<div id="header">
	<c:import url="${request.contextPath}/header" />
</div>

<div id="body">
	<h3>
		<c:if test="${deleted}"><span class="confirm"><spring:message code="result.deleted" /></span></c:if>
	</h3>

	<h2>article list</h2>
	<c:if test="${(articles != null) && !(empty articles)}">
	<ol>
		<c:forEach items="${articles}" var="article">
		<li>
			<span class="col_id">
				<a href="<%= request.getContextPath() %>/article/edit/${article.id}"><fmt:formatNumber value="${article.id}" pattern="00000000"/></a>
			</span>
			<span class="col_sub"><c:out value="${article.subject}"></c:out></span>
		</li>
		</c:forEach>
	</ol>
	</c:if>
	<ul>
		<li>
			<form action="new" method="post">
				<input type="submit" value="new article" />
			</form>
		</li>
	</ul>
</div>

<div id="footer">
	<jsp:include page="../_footer.jsp"/>
</div>

</body>
</html>

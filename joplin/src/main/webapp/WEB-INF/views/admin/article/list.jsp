<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
	<title>article list</title>
	<%@ include file="../../_headBase.jsp"%>
</head>
<body>

<div id="header">
	<jsp:include page="../header.jsp"/>
</div>

<div id="body">
	<h3>
		<c:if test="${deleted}"><span class="confirm"><spring:message code="result.deleted" /></span></c:if>
	</h3>

	<h1>article list</h1>
	<c:if test="${(articles != null) && !(empty articles)}">
	<ol>
		<c:forEach items="${articles}" var="article">
		<li>
			<span class="colId">
				<a href="${article.id}/edit"><fmt:formatNumber value="${article.id}" pattern="00000000"/></a>
			</span>
			<span class="colVal"><c:out value="${article.subject}"></c:out></span>
			<span class="colVal">
				<c:choose>
					<c:when test="${article.active}">実施中</c:when><c:otherwise>停止</c:otherwise>
				</c:choose>
			</span>
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
	<jsp:include page="../../_footer.jsp"/>
</div>

</body>
</html>

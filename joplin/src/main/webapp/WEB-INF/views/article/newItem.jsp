<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
	<title>item new</title>
	<%@ include file="../_headBase.jsp"%>
</head>
<body>

<div id="header">
	<c:import url="${request.contextPath}/header" />
</div>

<div id="body">
	<h2>item new</h2>
	<h3>videos</h3>
	<form:form modelAttribute="youtubeSearchForm" action="searchItem" method="post">
		<form:hidden path="articleId" />
		<form:hidden path="startIndex" />
		<form:hidden path="listSize" />
		<form:input path="searchText" size="32" maxlength="128"/>
		<input type="submit" value="search" />
		<spring:hasBindErrors name="youtubeSearchForm"><form:errors path="searchText" cssStyle="color:red" /></spring:hasBindErrors>
	</form:form>
	<c:if test="${(youtubeItems != null) && !(empty youtubeItems)}">
	<ol>
		<c:forEach items="${youtubeItems}" var="youtubeItem" varStatus="status">
		<li>
			<span class="colVal">
				<iframe width="360" height="202" src="http://www.youtube.com/embed/${youtubeItem.videoId}?rel=0" frameborder="0" allowfullscreen></iframe>
			</span>
			<span class="colVal">
				<form id="${status.index}form" action="addItem" method="post">
					<input type="hidden" name="articleId" value="${youtubeItem.articleId}" />
					<input type="hidden" name="videoId" value="${youtubeItem.videoId}" />
					<input type="submit" id="${videoId}" class="addItemBtn" value="add" />
				</form>
			</span>
		</li>
		</c:forEach>
	</ol>
	</c:if>

</div>

<div id="footer">
	<jsp:include page="../_footer.jsp"/>
</div>

<script type="text/javascript">
<!--
/*
$(function(){
	$('.addItemBtn').click(function(){
		$('#youtubeItem #videoId').val($(this).attr('id'));
		return true;
	});
});
*/
// -->
</script>
</body>
</html>

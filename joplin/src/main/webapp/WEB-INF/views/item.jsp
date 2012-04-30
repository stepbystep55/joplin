<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.ippoippo.joplin.dto.YoutubeSearchForm" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="_headBase.jsp"%>
<%--
	<link rel="stylesheet" type="text/css" media="all" href="<%= request.getContextPath() %>/resources/tableScroll/jquery.tablescroll.css" />
--%>
	<title>New item</title>
</head>
<body>

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
			<%--
				<a class="btn btn-navbar" data-toggle="collapse" data-target="nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
			--%>
				<a class="brand" href="#">VivaJoplin</a>
			<%--
				<div class="nav-collapse">
			--%>
				<div>
					<ul class="nav">
						<li class="active"><a href="battle">Battle</a></li>
						<li><a href="rank">Ranking</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="container">

		<header class="header">
			<h1><c:out value="${article.subject}" /></h1>
		</header>

		<section id="items">
			<h2>Search form</h2>
			<br/>
			<form:form modelAttribute="youtubeSearchForm" action="searchItem" method="post">
				<form:hidden path="startIndex" />
				<form:hidden path="listSize" />
				<form:hidden path="command" />
				<div class="row">
					<div class="span2">
						<label class="control-label" for="searchText">Search text</label>
					</div>
					<div class="span4">
						<form:input path="searchText" maxlength="128" class="input-xlarge search-query"/>
						<input type="hidden" id="orgnSearchText" name="orgnSearchText" value="${youtubeSearchForm.searchText}" />
					</div>
					<div class="span6">
						<input type="button" id="searchBtn" value="search" />
					</div>
				</div>
			</form:form>

			<c:if test="${(items != null) && (fn:length(items) != 0)}">
			<div class="row">
				<div class="span2">
					<ul class="pager">
						<li><a id="prevLink" href="#">Previous</a></li>
						<li><a id="nextLink" href="#">Next</a></li>
					</ul>
				</div>
				<div class="span10"></div>
			</div>
			</c:if>
			<div id="resultHolder" style="overflow: auto; max-height: 470px; width: 550px;">
				<c:choose>
					<c:when test="${(items != null) && (fn:length(items) != 0)}">
						<table class="table table-striped">
							<tbody>
								<c:forEach items="${items}" var="item" varStatus="status">
								<tr>
									<td>
										<iframe
											width="360" height="213"
											src="http://www.youtube.com/embed/${item.videoId}?rel=0"
											frameborder="0" allowfullscreen>
										</iframe>
									</td>
									<td>
										<form id="form${status.index}" action="addItem" method="post">
											<input type="hidden" name="videoId" value="${item.videoId}" />
											<input type="submit" id="${videoId}" class="addItemBtn" value="select" /><br/>
											<input type="checkbox" id="canShare" name="canShare" value="true" checked="checked" />&nbsp;Share
										</form>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:when>
					<c:otherwise>
						<c:if test="${message != null}"><h3><c:out value="${message}" /></h3></c:if>
					</c:otherwise>
				</c:choose>
			</div>
		</section>

		<jsp:include page="_footer.jsp"/>
	</div>

<%@ include file="_footBase.jsp"%>
<%--
<script type="text/javascript" charset="utf-8" src="<%= request.getContextPath() %>/resources/tableScroll/jquery.tablescroll.js"></script>
<script type="text/javascript" charset="utf-8" src="<%= request.getContextPath() %>/resources/malsup-form/jquery.form.js"></script>
--%>

<script type="text/javascript">
<!--
$(function(){
	$('#searchBtn').click(function() {
		$('#command').val('<%=YoutubeSearchForm.COMMAND_RESET%>');
		$('#youtubeSearchForm').submit();
	});
	$('#prevLink').click(function() {
		$('#command').val('<%=YoutubeSearchForm.COMMAND_PREV%>');
		$('#searchText').val($('#orgnSearchText').val());
		$('#youtubeSearchForm').submit();
	});
	$('#nextLink').click(function() {
		$('#command').val('<%=YoutubeSearchForm.COMMAND_NEXT%>');
		$('#searchText').val($('#orgnSearchText').val());
		$('#youtubeSearchForm').submit();
	});
	$('.addItemBtn').click(function() {
	});
});
// -->
</script>
</body>
</html>

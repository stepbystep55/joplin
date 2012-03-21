<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="../../_headBase.jsp"%>
	<title>New item</title>
</head>
<body>

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
				<a class="brand" href="#">VivaJoplin</a>
				<div class="nav-collapse">
					<ul class="nav pull-right">
						<li><a href="<%= request.getContextPath() %>/admin/logout">Logout</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="container">

		<header class="header">
			<h1>New item</h1>
		</header>

		<ul class="breadcrumb" id="brdCrmb">
			<li><a href="../list">Articles</a> <span class="divider">/</span></li>
			<li><a href="edit">Edit Article</a> <span class="divider">/</span></li>
			<li class="active">New item</li>
		</ul>

		<section id="items">
			<h2>Search form</h2>
			<form:form modelAttribute="youtubeSearchForm" action="searchItem" method="post" cssClass="well form-search">
				<form:hidden path="startIndex" />
				<form:hidden path="listSize" />
				<fieldset>
					<div class="control-group">
						<label class="control-label" for="subject">Subject</label>
						<div class="controls">
							<form:input path="searchText" maxlength="128" cssClass="input-xlarge search-query"/>
							<spring:hasBindErrors name="youtubeSearchForm">
								<span class="help-inline"><form:errors path="searchText" cssStyle="color:red" /></span>
							</spring:hasBindErrors>
						</div>
					</div>
				</fieldset>
				<div class="form-actions">
					<input type="submit" name="command" value="search" />
				</div>
				<div class="form-actions">
					<input type="submit" name="command" value="prev" />
					<input type="submit" name="command" value="next" />
				</div>
			</form:form>

			<h2>Search result</h2>
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Movie</th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${(items != null) && (fn:length(items) != 0)}">
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
									<form id="${status.index}form" action="addItem" method="post">
										<input type="hidden" name="videoId" value="${item.videoId}" />
										<input type="submit" id="${videoId}" class="addItemBtn" value="add" />
									</form>
								</td>
							</tr>
							</c:forEach>
						</c:when>
					</c:choose>
				</tbody>
			</table>

			<div class="row">
				<div class="span12 centering">
					<a href="edit">return</a>
				</div>
			</div>
		</section>

		<jsp:include page="../../_footer.jsp"/>
	</div>

<%@ include file="../../_footBase.jsp"%>

<script type="text/javascript">
<!--
$(function(){
});
// -->
</script>
</body>
</html>

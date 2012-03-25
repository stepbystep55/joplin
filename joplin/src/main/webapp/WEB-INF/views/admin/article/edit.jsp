<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="../../_headBase.jsp"%>
	<title>Edit article</title>
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
			<h1>Edit article</h1>
		</header>

		<ul class="breadcrumb" id="brdCrmb">
			<li><a href="../list">Articles</a> <span class="divider">/</span></li>
			<li class="active">Edit Article</li>
		</ul>

		<section id="article">
			<h2><c:out value="${article.id}" /></h2>
			<br/>

			<form:form modelAttribute="article" action="update" method="post">
			<div class="row">
				<div class="span1">
					<label class="control-label" for="subject">Subject</label>
				</div>
				<div class="span11">
					<form:input path="subject" maxlength="128" cssClass="input-xxlarge" />
					<spring:hasBindErrors name="article">
					<span class="help-inline"><form:errors path="subject" cssStyle="color:red" /></span>
					</spring:hasBindErrors>
				</div>
			</div>
			<div class="row">
				<div class="span1">
					<label class="control-label" for="active">Active</label>
				</div>
				<div class="span11">
					<label class="radio"><form:radiobutton path="active" value="true"/>&nbsp;active</label>
					<label class="radio"><form:radiobutton path="active" value="false"/>&nbsp;inactive</label>
				</div>
			</div>
			<div class="row">
				<div class="span1">&nbsp;</div>
				<div class="span1">
					<input type="submit" id="updateBtn" name="updateBtn" value="update" />
				</div>
				<div class="span10">
					<input type="submit" id="deleteBtn" name="deleteBtn" value="delete" />
				</div>
			</div>
			</form:form>

			<div class="row">
				<div class="span1">&nbsp;</div>
				<div class="span11">
					<form action="item" method="post">
						<input type="submit" id="newItemBtn" value="new item" />
					</form>
				</div>
			</div>
			<div class="row">
				<div class="span1">
					Items
				</div>
				<div class="span11">
					<table class="table">
						<thead>
							<tr>
								<th>Id</th>
								<th>Movie</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<tbody>
						<c:choose>
							<c:when test="${(items != null) && (fn:length(items) != 0)}">
								<c:forEach items="${items}" var="item">
								<tr>
									<td>
										<c:out value="${item.id}" />
									</td>
									<td>
										<iframe width="360" height="213" src="http://www.youtube.com/embed/${item.videoId}?rel=0" frameborder="0" allowfullscreen></iframe>
									</td>
									<td>
										<form action="deleteItem" method="post">
											<input type="hidden" name="itemId" value="${item.id}" />
											<input type="submit" id="deleteItemBtn" value="delete" />
										</form>
									</td>
								</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="3">No item</td>
								</tr>
							</c:otherwise>
						</c:choose>
						</tbody>
					</table>
				</div>
			</div>

		</section>

		<jsp:include page="../../_footer.jsp"/>
	</div>

<%@ include file="../../_footBase.jsp"%>

<script type="text/javascript">
<!--
$(function(){
	$('#deleteBtn').click(function() {
		return confirm('Are you sure?');
	});
	$('#deleteItemBtn').click(function() {
		return confirm('Are you sure?');
	});
});
// -->
</script>
</body>
</html>

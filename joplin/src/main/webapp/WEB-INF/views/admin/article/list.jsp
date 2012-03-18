<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="../../_headBase.jsp"%>
	<title>Articles</title>
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

		<ul class="breadcrumb" id="brdCrmb">
			<li class="active">Articles</li>
		</ul>

		<header class="header">
			<h1>Articles</h1>
		</header>

		<section id="articles">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Id</th>
						<th>Subject</th>
						<th>Active</th>
					</tr>
				</thead>
				<c:if test="${(articles != null) && !(empty articles)}">
				<tbody>
					<tr>
						<c:forEach items="${articles}" var="article">
						<td>
							<a href="${article.id}/edit"><c:out value="${article.id}" /></a>
						</td>
						<td>
							<c:out value="${article.subject}" />
						</td>
						<td>
							<c:choose>
								<c:when test="${article.active}">Active</c:when>
								<c:otherwise>Suspend</c:otherwise>
							</c:choose>
						</td>
						</c:forEach>
					</tr>
				</tbody>
				</c:if>
			</table>
		</section>

		<section id="newArticle">
			<div class="row">
				<div class="span12">
					<div class="control-group">
						<form action="new" method="post">
							<input type="submit" value="New article" />
						</form>
					</div>
				</div>
			</div>
		</section>

		<jsp:include page="../../_footer.jsp"/>
	</div>

<%@ include file="../../_footBase.jsp"%>
</body>
</html>
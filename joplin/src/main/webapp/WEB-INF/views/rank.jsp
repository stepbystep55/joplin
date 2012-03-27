<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="_headBase.jsp"%>
	<title>Ranking</title>
</head>
<body>

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse" data-target="nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
				<a class="brand" href="#">VivaJoplin</a>
				<div class="nav-collapse">
					<ul class="nav">
						<li><a href="battle">Battle</a></li>
						<li class="active"><a href="#">Ranking</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="container">

		<header class="header">
			<h1><c:out value="${article.subject}" /></h1>
		</header>

		<section id="ranking">
			<table class="table">
				<thead>
					<tr>
						<th>Rank</th>
						<th>Movie</th>
					</tr>
				</thead>
				<c:if test="${(items != null) && (fn:length(items) != 0)}">
				<tbody>
					<c:forEach items="${items}" var="item" varStatus="status">
					<tr>
						<td>
							<c:out value="${status.index + 1}" />
						</td>
						<td>
							<iframe width="400" height="233" src="http://www.youtube.com/embed/${item.videoId}?rel=0" frameborder="0" allowfullscreen></iframe>
						</td>
					</tr>
					</c:forEach>
				</tbody>
				</c:if>
			</table>
		</section>

		<jsp:include page="_footer.jsp"/>
	</div>

<%@ include file="_footBase.jsp"%>
<script type="text/javascript">
<!--
$(function(){
});
// -->
</script>
</body>
</html>

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
			<%--
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
			--%>
				<a class="brand" href="#">VivaJoplin</a>
				<%-- <div class="nav-collapse collpase"> --%>
				<div>
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
							<a data-toggle="modal" href="#video${status.index + 1}">
								<img id="Thumbnail${status.index + 1}" class="vThumbnail" src="${item.thumbnailUrl}" width="320" height="240" />
							</a>
							<h5 style="padding-left: 70px;">
								<a data-toggle="modal" href="#video${status.index + 1}"><i class="icon-play"></i>&nbsp;Click image to view video</a>
							</h5>
							<div class="modal hide fade" id="video${status.index + 1}">
								<div class="modal-header">
									<button class="close" data-dismiss="modal">×</button>
									<h4>Current rank: No.${status.index + 1}</h4>
								</div>
								<div class="modal-body">
								<c:choose>
									<c:when test="${voteMinCountReached}">
										<iframe width="530" height="299" src="http://www.youtube.com/embed/${item.videoId}?rel=0" frameborder="0" allowfullscreen></iframe>
									</c:when>
									<c:otherwise>
										<h3 style="color:#FF6347;">
										You vote more to view video. Ciao!</h3>
									</c:otherwise>
								</c:choose>
								</div>
							</div>
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

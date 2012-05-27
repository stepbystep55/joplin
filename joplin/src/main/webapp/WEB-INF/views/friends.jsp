<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="_headBase520.jsp"%>
	<title>Friends</title>
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
						<li><a href="rank">Ranking</a></li>
						<li class="active"><a href="#">Friends</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="container">

		<header class="header">
			<h1><c:out value="${article.subject}" />&nbsp;</h1>
		</header>

		<section id="friends">
			<table class="table">
				<thead>
					<tr>
						<th>Friend</th>
						<th>Rank</th>
						<th>Movie</th>
					</tr>
				</thead>
				<tbody>
				<c:choose>
					<c:when test="${(contributions != null) && (fn:length(contributions) != 0)}">
						<c:forEach items="${contributions}" var="contribution" varStatus="status">
						<tr>
							<td>
								<c:out value="${contribution.displayName}" />
							</td>
							<td>
								<c:out value="${contribution.rank}" />
							</td>
							<td>
								<a class="vDialogBtn" href="#${contribution.item.videoId}">
									<img id="Thumbnail${status.index + 1}" class="vThumbnail" src="${contribution.item.thumbnailUrl}" width="240" height="180" />
								</a>
								<h5 style="padding-left: 70px;">
									<a class="vDialogBtn" href="#${contribution.item.videoId}"><i class="icon-play"></i>&nbsp;Click image to view video</a>
								</h5>
							</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td colspan="3"><i>No video</i></td></tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
		</section>

		<div class="modal hide fade" id="vDialog">
			<div class="modal-header">
				<button class="close" data-dismiss="modal">×</button>
				<h4>Video view</h4>
			</div>
			<div class="modal-body centering">
				<img id="logo" alt="logo" src="<%= request.getContextPath() %>/resources/img/mark/square/image128.png" />
				<h3 style="color:#FF6347;">You vote more to view video. Ciao!</h3>
			</div>
		</div>
	</div>

<jsp:include page="_footer.jsp"/>

<%@ include file="_footBase.jsp"%>
<script type="text/javascript">
<!--
<c:if test=" ${voteMinCountReached}">
var vfrm = '<iframe width="530" height="299" src="http://www.youtube.com/embed/_VID_?rel=0&autoplay=1" frameborder="0" allowfullscreen></iframe>';
</c:if>
$(function(){
	$('.vDialogBtn').click(function(){
<c:if test=" ${voteMinCountReached}">
		var videoId = $(this).attr('href').substring(1);
		$('#vDialog').on('show', function(){$('.modal-body').html(vfrm.replace('_VID_',videoId));});
		$('#vDialog').on('hidden', function(){$('.modal-body').html('');});
</c:if>
		$('#vDialog').modal();
	});
});
// -->
</script>
</body>
</html>

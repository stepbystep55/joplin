<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="_headBase.jsp"%>
	<title>Your item</title>
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
						<li><a href="friends">Friends</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="container">

		<header class="header">
			<h1><c:out value="${article.subject}" />&nbsp;</h1>
		</header>

		<section id="video">
			<c:if test="${contribution.item.videoId != null}">
			<div class="row">
				<div class="span12">
					<h3>Current rank: <c:out value="${contribution.rank}" /></h3>
					<div>
						<a class="vDialogBtn" href="#${contribution.item.videoId}">
							<img id="thumbnail" class="vThumbnail" src="${contribution.item.thumbnailUrl}" width="320" height="240" />
						</a>
						<h5 style="padding-left: 70px;">
							<a class="vDialogBtn" href="#${contribution.item.videoId}">
								<i class="icon-play"></i>&nbsp;Click image to view video
							</a>
						</h5>
					</div>
					
				</div>
			</div>
			<div class="row"><div class="span12">&nbsp;</div></div>
			<div class="row">
				<div class="span12">
					<p>You can post only one video for each article.</p>
				</div>
			</div>
			</c:if>
		</section>

		<div class="modal hide fade" id="vDialog">
			<div class="modal-header">
				<button class="close" data-dismiss="modal">×</button>
				<h4>Your posted video</h4>
			</div>
			<div class="modal-body"></div>
		</div>

		<jsp:include page="_footer.jsp"/>
	</div>

<%@ include file="_footBase.jsp"%>
<script type="text/javascript">
<!--
var vfrm = '<iframe width="530" height="299" src="http://www.youtube.com/embed/_VID_?rel=0&autoplay=1" frameborder="0" allowfullscreen></iframe>';
$(function(){
	$('.vDialogBtn').click(function(){
		var videoId = $(this).attr('href').substring(1);
		$('#vDialog').on('show', function(){$('.modal-body').html(vfrm.replace('_VID_',videoId));});
		$('#vDialog').on('hidden', function(){$('.modal-body').html('');});
		$('#vDialog').modal();
	});
});
// -->
</script>
</body>
</html>

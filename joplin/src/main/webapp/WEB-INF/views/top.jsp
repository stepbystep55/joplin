<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="_headBase.jsp"%>
	<title>VS</title>
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
						<li class="active"><a href="#">VS</a></li>
						<li><a href="ranking">Ranking</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="container">

		<header class="header">
			<h1>VS</h1>
		</header>

		<section id="vs">
			<form id="vsForm" action="vs" method="post">
				<input type="hidden" id="articleId" name="articleId" value="${articleId}" />
				<input type="hidden" id="firstItemId" name="firstItemId" value="${firstItem.id}" />
				<input type="hidden" id="secondItemId" name="secondItemId" value="${secondItem.id}" />
				<input type="hidden" id="winnerItemId" name="winnerItemId" value="" />
				<div class="row">
					<div class="span6 centering">
						<iframe width="400" height="233" src="http://www.youtube.com/embed/${firstItem.videoId}?rel=0" frameborder="0" allowfullscreen></iframe>
					</div>
					<div class="span6 centering">
						<iframe width="400" height="233" src="http://www.youtube.com/embed/${secondItem.videoId}?rel=0" frameborder="0" allowfullscreen></iframe>
					</div>
				</div>
				<div class="row">
					<div class="span6 centering">
						<input type="button" id="firstBtn" class="btn-large" name="firstBtn" value="Good!" />
					</div>
					<div class="span6 centering">
						<input type="button" id="secondBtn" class="btn-large" name="secondBtn" value="Good!" />
					</div>
				</div>
			</form>
		</section>

		<jsp:include page="_footer.jsp"/>
	</div>

<%@ include file="_footBase.jsp"%>
<script type="text/javascript">
<!--
$(function(){
	$('input[name="firstBtn"]').click(function(){
		doVs('${firstItem.id}', '${secondItem.id}');
	});
	$('input[name="secondBtn"]').click(function(){
		doVs('${secondItem.id}', '${firstItem.id}');
	});
});
function doVs(chosenItemId, discardItemId) {
	$('input[name="winnerItemId"]').val(chosenItemId);
	$('#vsForm').submit();
	
}
// -->
</script>
</body>
</html>

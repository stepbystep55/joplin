<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="_headBase.jsp"%>
	<title>top</title>
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
						<li class="active"><a href="#">Home</a></li>
						<li><a href="#about">About</a></li>
						<li><a href="#contact">Contact</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

<div id="body">
	<h1>top</h1>
	<form id="vsForm" action="vs" method="post">
		<input type="hidden" id="articleId" name="articleId" value="${articleId}" />
		<input type="hidden" id="firstItemId" name="firstItemId" value="${firstItem.id}" />
		<input type="hidden" id="secondItemId" name="secondItemId" value="${secondItem.id}" />
		<input type="hidden" id="winnerItemId" name="winnerItemId" value="" />
		<span class="colVal">
			<iframe width="360" height="213" src="http://www.youtube.com/embed/${firstItem.videoId}?rel=0" frameborder="0" allowfullscreen></iframe>
			<input type="button" id="firstBtn" name="firstBtn" value="chose this" />
		</span>
		&nbsp;
		<span class="colVal">
			<iframe width="360" height="213" src="http://www.youtube.com/embed/${secondItem.videoId}?rel=0" frameborder="0" allowfullscreen></iframe>
			<input type="button" id="secondBtn" name="secondBtn" value="chose this" />
		</span>
	</form>
</div>

<div id="footer">
	<jsp:include page="_footer.jsp"/>
</div>

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

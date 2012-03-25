<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="_headBase.jsp"%>
	<title>HotOrNot</title>
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
						<li class="active"><a href="#">HotOrNot</a></li>
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

		<section id="match">
		</section>

		<div class="row">
			<div class="span12">
				<form id="itemForm" action="item" method="post">
					<a id="itemLink" href="#">Add yours</a>
				</form>
			</div>
		</div>

		<jsp:include page="_footer.jsp"/>
	</div>

<%@ include file="_footBase.jsp"%>
<script type="text/javascript">
<!--
$(function(){
	$.ajax({
		type: 'GET',
		url: 'vs',
		cache: false,
		success: function(html){
			$("#match").html(html);
		}
	});
	$('#itemLink').click(function() {
		$('#itemForm').submit();
	});
});
// -->
</script>
</body>
</html>

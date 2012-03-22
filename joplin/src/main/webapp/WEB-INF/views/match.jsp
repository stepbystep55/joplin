<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="_headBase.jsp"%>
	<title>Match</title>
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
						<li class="active"><a href="#">Match</a></li>
						<li><a href="rank">Ranking</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="container">

		<header class="header">
			<h1>Match</h1>
		</header>

		<section id="match">
		</section>

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
});
// -->
</script>
</body>
</html>

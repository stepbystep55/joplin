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
				<a class="btn btn-navbar" data-toggle="collapse" data-target="nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
				<a class="brand" href="#">VivaJoplin</a>
				<div class="nav-collapse">
					<ul class="nav">
						<li class="active"><a href="battle">Battle</a></li>
						<li><a href="rank">Ranking</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="container">

		<header class="header">
			<h1>&nbsp;</h1>
		</header>

		<section id="video">
			<div class="row">
				<div class="span12">
					<c:if test="${videoId != null}">
					<h3>Current rank: <c:out value="${rank}" /></h3>
					<iframe
						width="400" height="233"
						src="http://www.youtube.com/embed/${videoId}?rel=0"
						frameborder="0" allowfullscreen>
					</iframe>
					<p>You can post only one video for each article.</p>
					</c:if>
				</div>
			</div>
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

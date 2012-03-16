<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="_headBase.jsp"%>
	<title>Sign in</title>
</head>
<body>

<div id="header">
</div>

<div id="body">
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
					<ul class="nav">
						<li class="active"><a href="#">Home</a></li>
						<li><a href="#about">About</a></li>
						<li><a href="#contact">Contact</a></li>
					</ul>
				</div><!--/.nav-collapse -->
			</div>
		</div>

	</div>
	<h1>Sign in</h1>

	<div class="row">
		<div class="span8 offset4">
			<h2>with Facebook</h2>
			<form:form action="signin/facebook" method="post">
				<input type="hidden" name="scope" value="user_likes,publish_stream,offline_access" />
				<input type="submit" name="connectFacebook" value="connect" />
			</form:form>
		</div>
	</div>
	<div class="row">
		<div class="span8 offset4">
			<h2>with Twitter</h2>
			<form:form action="signin/twitter" method="post">
				<input type="submit" name="connectTwitter" value="connect" />
			</form:form>
		</div>
	</div>

</div>

<div id="footer">
	<jsp:include page="_footer.jsp"/>
</div>

	<%@ include file="_headBase.jsp"%>
</body>
</html>

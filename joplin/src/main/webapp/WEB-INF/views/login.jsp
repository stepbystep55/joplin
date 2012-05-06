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
			</div>
		</div>
	</div>

	<div class="container">

		<header class="header centering">
			<img id="logo" alt="logo" src="<%= request.getContextPath() %>/resources/img/all_icons_png/square/image200.png" />
		</header>

		<section id="login">
			<div class="row">
				<div class="offset5 span7">
					<form:form action="signin/facebook" method="post" target="_top">
					<div class="control-group">
						<input type="hidden" name="scope" value="user_likes,publish_stream,offline_access" />
						<input type="submit" class="btn-large" name="connectFacebook" value="connect" />
						<span class="help-inline"><strong>with Facebook</strong></span>
					</div>
					</form:form>
				</div>
			</div>
			<%--
			<div class="row">
				<div class="offset5 span7">
					<div class="control-group">
						<form:form action="signin/twitter" method="post">
							<input type="submit" class="btn-large" name="connectTwitter" value="connect" />
							<span class="help-inline"><strong>with Twitter</strong></span>
						</form:form>
					</div>
				</div>
			</div>
			--%>
		</section>

		<jsp:include page="_footer.jsp"/>
	</div>

<%@ include file="_footBase.jsp"%>
</body>
</html>

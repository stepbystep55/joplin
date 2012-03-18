<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="../_headBase.jsp"%>
	<title>Admin Login</title>
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
			</div>
		</div>
	</div>

	<div class="container">

		<ul class="breadcrumb" id="brdCrmb">
			<li></li>
		</ul>

		<header class="header">
			<h1>Admin login</h1>
		</header>

		<section id="login">
			<div class="row">
				<div class="span12">
					<form action="login" method="post">
					<div class="control-group<c:if test='${failed}'> error</c:if>">
						<input type="password" name="password" size="16" maxlength="32" />
						<input type="submit" name="login" value="login" />
						<c:if test='${failed}'><span class="help-inline">&nbsp;<spring:message code="login.failed" /></span></c:if>
					</div>
					</form>
				</div>
			</div>
		</section>

		<jsp:include page="../_footer.jsp"/>
	</div>

<%@ include file="../_footBase.jsp"%>
</body>
</html>

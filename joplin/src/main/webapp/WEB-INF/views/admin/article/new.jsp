<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="../../_headBase.jsp"%>
	<title>New article</title>
</head>
<body>

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
					<ul class="nav pull-right">
						<li><a href="<%= request.getContextPath() %>/admin/logout">Logout</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="container">

		<ul class="breadcrumb" id="brdCrmb">
			<li><a href="list">Articles</a> <span class="divider">/</span></li>
			<li class="active">New Article</li>
		</ul>

		<header class="header">
			<h1>New Article</h1>
		</header>

		<section id="newArticle">
			<div class="row">
				<div class="span12">
					<form:form modelAttribute="article" action="create" method="post" cssClass="form-horizontal">
					<div class="control-group">
						<label class="control-label" for="subject">subject</label>
						<div class="controls">
							<form:input path="subject" size="32" maxlength="128"/>
							<spring:hasBindErrors name="article"><form:errors path="subject" cssStyle="color:red" /></spring:hasBindErrors>
							<input type="submit" value="Create" />
						</div>
					</div>
					</form:form>
				</div>
			</div>
		</section>

		<jsp:include page="../../_footer.jsp"/>
	</div>

<%@ include file="../../_footBase.jsp"%>
</body>
</html>

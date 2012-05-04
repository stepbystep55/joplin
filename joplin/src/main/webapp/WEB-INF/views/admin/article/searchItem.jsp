<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="../../_headBase.jsp"%>
<%--
	<link rel="stylesheet" type="text/css" media="all" href="<%= request.getContextPath() %>/resources/tableScroll/jquery.tablescroll.css" />
--%>
	<title>New item</title>
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

		<header class="header">
			<h1>New item</h1>
		</header>

		<ul class="breadcrumb" id="brdCrmb">
			<li><a href="../list">Articles</a> <span class="divider">/</span></li>
			<li><a href="edit">Edit article</a> <span class="divider">/</span></li>
			<li class="active">New item</li>
		</ul>

		<section id="searchForm">
			<h2>Search form</h2>
			<br/>
			<form:form modelAttribute="youtubeSearchForm" action="#" method="post">
				<form:hidden path="startIndex" />
				<form:hidden path="listSize" />
				<form:hidden path="command" />
				<div class="row">
					<div class="span2">
						<label class="control-label" for="searchText">Search text</label>
					</div>
					<div class="span4">
						<form:input path="searchText" maxlength="128" class="input-xlarge search-query"/>
					</div>
					<div class="span6">
						<input type="button" id="searchBtn" value="search" />
					</div>
				</div>
			</form:form>
		</section>

		<section id="searchResult"></section>

		<jsp:include page="../../_footer.jsp"/>
	</div>

<%@ include file="../../_footBase.jsp"%>
<%--
<script type="text/javascript" charset="utf-8" src="<%= request.getContextPath() %>/resources/tableScroll/jquery.tablescroll.js"></script>
--%>

<script type="text/javascript">
<!--
$(function(){
	$('#searchBtn').click(function(){
		loadSearchResult($('#command').val(),$('#startIndex').val(),$('#listSize').val(),$('#searchText').val());
	});
});
function loadSearchResult(command, startIndex, listSize, searchText){
	$.ajax({
		type: 'POST',
		url: 'searchItem',
		data: {
			startIndex: startIndex,
			listSize: listSize,
			command: command,
			searchText: searchText
		},
		cache: false,
		success: function(html){$("#searchResult").html(html);}
	});
}
// -->
</script>
</body>
</html>

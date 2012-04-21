<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.ippoippo.joplin.dto.ItemListForm" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="../../_headBase.jsp"%>
	<title>Edit article</title>
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
			<h1>Edit article</h1>
		</header>

		<ul class="breadcrumb" id="brdCrmb">
			<li><a href="../list">Articles</a> <span class="divider">/</span></li>
			<li class="active">Edit article</li>
		</ul>

		<section id="article">
			<h2><c:out value="${article.id}" /></h2>
			<c:if test="${errorMessage != null}">
				<h3 style="color: red;"><c:out value="${errorMessage}" /></h3>
			</c:if>
			<br/>

			<form:form modelAttribute="article" action="update" method="post">
			<div class="row">
				<div class="span1">
					<label class="control-label" for="subject">Subject</label>
				</div>
				<div class="span11">
					<form:input path="subject" maxlength="128" cssClass="input-xxlarge" />
					<spring:hasBindErrors name="article">
					<span class="help-inline"><form:errors path="subject" cssStyle="color:red" /></span>
					</spring:hasBindErrors>
				</div>
			</div>
			<div class="row">
				<div class="span1">
					<label class="control-label" for="active">Active</label>
				</div>
				<div class="span11">
					<label class="radio"><form:radiobutton path="active" value="true"/>&nbsp;active</label>
					<label class="radio"><form:radiobutton path="active" value="false"/>&nbsp;inactive</label>
				</div>
			</div>
			<div class="row">
				<div class="span1">&nbsp;</div>
				<div class="span1">
					<input type="submit" id="updateBtn" name="updateBtn" value="update" />
				</div>
				<div class="span10">
					<input type="submit" id="deleteBtn" name="deleteBtn" value="delete" />
				</div>
			</div>
			</form:form>

			<div class="row">
				<div class="span1">&nbsp;</div>
				<div class="span11">
					<form action="item" method="post">
						<input type="submit" id="newItemBtn" value="new item" />
					</form>
				</div>
			</div>

			<div id="itemList"></div>

		</section>

		<jsp:include page="../../_footer.jsp"/>
	</div>

<%@ include file="../../_footBase.jsp"%>

<script type="text/javascript">
<!--
$(function(){
	$('#deleteBtn').click(function() {
		return confirm('Are you sure?');
	});
	$.ajax({
		type: 'POST',
		url: 'listItem',
		data: {
			startIndex: '<%= ItemListForm.DEFAULT_START_INDEX %>',
			listSize: '<%= ItemListForm.DEFAULT_LIST_SIZE %>',
			command: '<%= ItemListForm.COMMAND_RESET %>'
		},
		cache: false,
		success: function(html){
			$("#itemList").html(html);
		}
	});
});
// -->
</script>
</body>
</html>

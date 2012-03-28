<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="_headBase.jsp"%>
	<title>Battle</title>
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
						<li class="active"><a href="#">Battle</a></li>
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

		<section id="vs">
		</section>

		<div class="row">
			<div class="span12">
				<form id="itemForm" action="item" method="post">
					<a id="itemLink" href="javascript:void(0)">Post yours</a>
				</form>
			</div>
		</div>

		<jsp:include page="_footer.jsp"/>
	</div>

<%--
<div id="shout" style="display:none; z-index:99; position:fixed; width:100%; top:10%; text-align:center;">
	<span style="color:blue; font-size:30px;">どんだけ～！</span>
</div>
--%>

<%@ include file="_footBase.jsp"%>
<script type="text/javascript">
<!--
$(function(){
	$.ajax({
		type: 'GET',
		url: 'vs',
		cache: false,
		success: function(html){
			$("#vs").html(html);
		}
	});
	$('#itemLink').click(function() {
		$('#itemForm').submit();
		return false;
	});
});
function winningRun(){
	var windiv = '<div id="shout" style="display:none; z-index:99; position:fixed; width:100%; top:10%; text-align:center;"><span style="color:blue; font-size:30px;">どんだけ～！</span></div>';
	$('body').append(windiv);
	$('#shout').show().fadeOut(2000, function(){
		$(this).remove();
	});
}
// -->
</script>
</body>
</html>

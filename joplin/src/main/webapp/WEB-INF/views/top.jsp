<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>top</title>
	<%@ include file="_headBase.jsp"%>
</head>
<body>

<div id="header">
	<c:import url="${request.contextPath}/header" />
</div>

<div id="body">
	<h1>top</h1>
	<form id="vsForm" action="vs" method="post">
		<input type="hidden" name="articleId" value="${articleId}" />
		<input type="hidden" id="chosenItemId" name="chosenItemId" value="" />
		<input type="hidden" id="discardItemId" name="discardItemId" value="" />
		<span class="colVal">
			<iframe width="360" height="213" src="http://www.youtube.com/embed/${firstItem.videoId}?rel=0" frameborder="0" allowfullscreen></iframe>
			<input type="button" id="firstBtn" name="firstBtn" value="chose this" />
		</span>
		&nbsp;
		<span class="colVal">
			<iframe width="360" height="213" src="http://www.youtube.com/embed/${secondItem.videoId}?rel=0" frameborder="0" allowfullscreen></iframe>
			<input type="button" id="secondBtn" name="secondBtn" value="chose this" />
		</span>
	</form>
</div>

<div id="footer">
	<jsp:include page="_footer.jsp"/>
</div>

<script type="text/javascript">
<!--
$(function(){
	$(input[name='firstBtn']).click(function(){
		doVs('${firstItem.id}', '${secondItem.id}');
	});
	$(input[name='secondBtn']).click(function(){
		doVs('${secondItem.id}', '${firstItem.id}');
	});
});
function doVs(chosenItemId, discardItemId) {
	$(input[name='chosenItemId']).val(chosenItemId);
	$(input[name='discardItemId']).val(discardItemId);
	$('#vsForm').submit();
	
}
// -->
</script>
</body>
</html>

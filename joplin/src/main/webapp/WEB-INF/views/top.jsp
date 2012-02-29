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
		<input type="hidden" id="chosenObjectId" name="chosenObjectId" value="" />
		<input type="hidden" id="discardObjectId" name="discardObjectId" value="" />
		<span class="colVal">
			<iframe width="360" height="213" src="http://www.youtube.com/embed/${chosenObjectId}?rel=0" frameborder="0" allowfullscreen></iframe>
			<input type="button" id="chosenBtn" name="chosenBtn" value="chose this" />
		</span>
		&nbsp;
		<span class="colVal">
			<iframe width="360" height="213" src="http://www.youtube.com/embed/${competitorObjectId}?rel=0" frameborder="0" allowfullscreen></iframe>
			<input type="button" id="competitorBtn" name="competitorBtn" value="chose this" />
		</span>
	</form>
</div>

<div id="footer">
	<jsp:include page="_footer.jsp"/>
</div>

<script type="text/javascript">
<!--
$(function(){
	$(input[name='chosenBtn']).click(function(){
		doChoose('${chosenObjectId}', '${competitorObjectId}');
	});
	$(input[name='chosenBtn']).click(function(){
		doChoose('${competitorObjectId}', '${chosenObjectId}');
	});
});
function doVs(chosenObjectId, discardObjectId) {
	$(input[name='chosenObjectId']).val(chosenObjectId);
	$(input[name='discardObjectId']).val(discardObjectId);
	$('#vsForm').submit();
	
}
// -->
</script>
</body>
</html>

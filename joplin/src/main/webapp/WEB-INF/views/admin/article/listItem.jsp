<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.ippoippo.joplin.dto.ItemListForm" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<div class="row">
	<div class="span1">&nbsp;</div>
	<div class="span2">
		<ul class="pager">
			<li><a id="prevLink" href="#">Previous</a></li>
			<li><a id="nextLink" href="#">Next</a></li>
		</ul>
	</div>
	<div class="span9">
		<form action="#" method="post">
			<input type="hidden" id="resultStartIndex" name="startIndex" value="${itemListForm.startIndex}" />
			<input type="hidden" id="resultListSize" name="listSize" value="${itemListForm.listSize}" />
		</form>
	</div>
</div>
<c:if test="${(items != null) && (fn:length(items) != 0)}">
	<div class="row">
		<div class="span1">
			Items
		</div>
		<div class="span11">
			<div id="resultHolder" style="overflow: auto; max-height: 470px;">
					<table class="table table-striped">
						<tbody>
							<c:forEach items="${items}" var="item">
							<tr>
								<td>
									<c:out value="${item.id}" />
								</td>
								<td>
									<iframe width="360" height="213" src="http://www.youtube.com/embed/${item.videoId}?rel=0" frameborder="0" allowfullscreen></iframe>
								</td>
								<td>
									<form action="deleteItem" method="post">
										<input type="hidden" name="itemId" value="${item.id}" />
										<input type="submit" id="deleteItemBtn" value="delete" />
									</form>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
			</div>
		</div>
	</div>
</c:if>

<script type="text/javascript">
<!--
$(function(){
	$('#deleteItemBtn').click(function() {
		return confirm('Are you sure?');
	});
	$('#prevLink').click(function() {
		loadItemList('<%= ItemListForm.COMMAND_PREV %>',$('#resultStartIndex').val(),$('#resultListSize').val());
	});
	$('#nextLink').click(function() {
		loadItemList('<%= ItemListForm.COMMAND_NEXT %>',$('#resultStartIndex').val(),$('#resultListSize').val());
	});
});
// -->
</script>
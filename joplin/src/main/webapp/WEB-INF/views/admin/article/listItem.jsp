<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.ippoippo.joplin.dto.ItemListForm" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div class="row">
	<div class="span2">
		<ul class="pager">
			<li><a id="prevLink" href="#">Previous</a></li>
			<li><a id="nextLink" href="#">Next</a></li>
		</ul>
	</div>
	<div class="span10"></div>
</div>
<div class="row">
	<div class="span1">
		Items
	</div>
	<div class="span11">
		<div id="resultHolder" style="overflow: auto; max-height: 470px;">
			<table class="table table-striped">
				<tbody>
				<c:choose>
					<c:when test="${(items != null) && (fn:length(items) != 0)}">
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
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="3">No item</td>
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
		</div>
	</div>
</div>

<script type="text/javascript">
<!--
$(function(){
	$('#deleteItemBtn').click(function() {
		return confirm('Are you sure?');
	});
	$('#prevLink').click(function() {
		$.ajax({
			type: 'POST',
			url: 'listItem',
			data: {
				startIndex: '${itemListForm.startIndex}',
				listSize: '${itemListForm.listSize}',
				command: '<%= ItemListForm.COMMAND_PREV %>'
			},
			cache: false,
			success: function(html){
				$("#itemList").html(html);
			}
		});
	});
	$('#nextLink').click(function() {
		$.ajax({
			type: 'POST',
			url: 'listItem',
			data: {
				startIndex: '${itemListForm.startIndex}',
				listSize: '${itemListForm.listSize}',
				command: '<%= ItemListForm.COMMAND_NEXT %>'
			},
			cache: false,
			success: function(html){
				$("#itemList").html(html);
			}
		});
	});
});
// -->
</script>
<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.ippoippo.joplin.dto.YoutubeSearchForm" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row">
	<div class="span2">
		<ul class="pager">
			<li><a id="prevLink" href="#">Previous</a></li>
			<li><a id="nextLink" href="#">Next</a></li>
		</ul>
	</div>
	<div class="span10">
		<form action="#" method="post">
			<input type="hidden" id="resultStartIndex" name="startIndex" value="${youtubeSearchForm.startIndex}" />
			<input type="hidden" id="resultListSize" name="listSize" value="${youtubeSearchForm.listSize}" />
			<input type="hidden" id="resultSearchText" name="searchText" value="${youtubeSearchForm.searchText}" />
		</form>
	</div>
</div>
<div id="resultHolder" style="overflow: auto; max-height: 470px; width: 550px;">
	<c:choose>
		<c:when test="${(items != null) && (fn:length(items) != 0)}">
			<table class="table table-striped">
				<tbody>
					<c:forEach items="${items}" var="item" varStatus="status">
					<tr>
						<td>
							<div>
								<a class="vDialogBtn" href="#${item.videoId}">
									<img id="thumbnail" class="vThumbnail" src="${item.thumbnailUrl}" width="300" height="200" />
								</a>
								<h5 style="padding-left: 70px;">
									<a class="vDialogBtn" href="#${item.videoId}">
										<i class="icon-play"></i>&nbsp;Click image to view video
									</a>
								</h5>
							</div>
						</td>
						<td>
							<form id="form${status.index}" action="addItem" method="post">
								<input type="hidden" name="articleId" value="${articleId}" />
								<input type="hidden" name="videoId" value="${item.videoId}" />
								<input type="hidden" name="encodedTitle" value="${item.encodedTitle}" />
								<input type="hidden" name="encodedThumbnailUrl" value="${item.encodedThumbnailUrl}" />
								<input type="submit" id="${item.videoId}" class="addItemBtn" value="select" /><br/>
								<input type="checkbox" id="canShare" name="canShare" value="true" checked="checked" />&nbsp;Share
							</form>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<c:if test="${message != null}"><h3><c:out value="${message}" /></h3></c:if>
		</c:otherwise>
	</c:choose>
</div>

<div class="modal hide fade" id="vDialog">
	<div class="modal-header">
		<button class="close" data-dismiss="modal">×</button>
		<h4>Video view</h4>
	</div>
	<div class="modal-body"></div>
</div>

<script type="text/javascript">
<!--
var vfrm = '<iframe width="530" height="299" src="http://www.youtube.com/embed/_VID_?rel=0&autoplay=1" frameborder="0" allowfullscreen></iframe>';
$(function(){
	$('.vDialogBtn').click(function(){
		var videoId = $(this).attr('href').substring(1);
		$('#vDialog').on('show', function(){$('.modal-body').html(vfrm.replace('_VID_',videoId));});
		$('#vDialog').on('hidden', function(){$('.modal-body').html('');});
		$('#vDialog').modal();
	});
	$('#prevLink').click(function(){
		loadSearchResult('<%=YoutubeSearchForm.COMMAND_PREV%>',$('#resultStartIndex').val(),$('#resultListSize').val(),$('#resultSearchText').val());
	});
	$('#nextLink').click(function(){
		loadSearchResult('<%=YoutubeSearchForm.COMMAND_NEXT%>',$('#resultStartIndex').val(),$('#resultListSize').val(),$('#resultSearchText').val());
	});
});
// -->
</script>
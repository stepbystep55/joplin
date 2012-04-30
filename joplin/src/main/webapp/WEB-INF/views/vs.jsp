<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

	<form id="vsForm" action="vote" method="post">
		<input type="hidden" id="firstItemId" name="firstItemId" value="${firstItem.id}" />
		<input type="hidden" id="secondItemId" name="secondItemId" value="${secondItem.id}" />
		<input type="hidden" id="winnerItemId" name="winnerItemId" value="" />
		<div class="row">
			<div class="span6 centering">
				<div>
					<%--<img id="firstThumbnail" src="${firstItem.thumbnailUrl}" width="320" height="240" /> --%>
					<a data-toggle="modal" href="#video1"><img id="firstThumbnail" src="${firstItem.thumbnailUrl}" width="320" height="240" />
					<h5><i class="icon-play"></i>&nbsp;Click image to view video</h5>
					</a>
				</div>
				<br/>
				<input type="button" id="firstBtn" class="btn-large" name="firstBtn" value="Good!" />
			</div>
			<div class="span6 centering">
				<div>
					<%--<img id="secondThumbnail" src="${secondItem.thumbnailUrl}" width="320" height="240" /> --%>
					<a data-toggle="modal" href="#video2"><img id="secondThumbnail" src="${secondItem.thumbnailUrl}" width="320" height="240" />
					<h5><i class="icon-play"></i>&nbsp;Click image to view video</h5>
					</a>
				</div>
				<br/>
				<input type="button" id="secondBtn" class="btn-large" name="secondBtn" value="Good!" />
			</div>
		</div>
	</form>
	<div class="modal hide fade" id="video1">
		<div class="modal-header">
			<button class="close" data-dismiss="modal">×</button>
			<h4>Video one</h4>
		</div>
		<div class="modal-body">
			<object width="530" height="299">
				<param name="movie" value="http://www.youtube.com/v/${firstItem.videoId}?version=3&hl=ja_JP&rel=0&autoplay=1"></param>
				<param name="allowFullScreen" value="true"></param>
				<param name="allowscriptaccess" value="always"></param>
				<embed
					src="http://www.youtube.com/v/${firstItem.videoId}?version=3&hl=ja_JP&rel=0&autoplay=1"
					type="application/x-shockwave-flash" width="530" height="299"
					allowscriptaccess="always" allowfullscreen="true">
				</embed>
			</object>
		</div>
	</div>
	<div class="modal hide fade" id="video2">
		<div class="modal-header">
			<button class="close" data-dismiss="modal">×</button>
			<h3>Video two</h3>
		</div>
		<div class="modal-body">
			<object width="530" height="299">
				<param name="movie" value="http://www.youtube.com/v/${secondItem.videoId}?version=3&hl=ja_JP&rel=0&autoplay=1"></param>
				<param name="allowFullScreen" value="true"></param>
				<param name="allowscriptaccess" value="always"></param>
				<embed
					src="http://www.youtube.com/v/${secondItem.videoId}?version=3&hl=ja_JP&rel=0&autoplay=1"
					type="application/x-shockwave-flash" width="530" height="299"
					allowscriptaccess="always" allowfullscreen="true">
				</embed>
			</object>
		</div>
	</div>
<script type="text/javascript">
<!--
$(function(){
<%--
	$('#firstThumbnail').click(function(){$('#video1').modal();});
	$('#secondThumbnail').click(function(){$('#video2').modal();});
--%>
	$('input[name="firstBtn"]').click(function(){
		vote('${firstItem.id}','${secondItem.id}','${firstItem.id}');
	});
	$('input[name="secondBtn"]').click(function(){
		vote('${firstItem.id}','${secondItem.id}','${secondItem.id}');
	});
});
function vote(firstItemId,secondItemId,winnerItemId) {
	winningRun();
	$.ajax({
		type: 'POST',
		url: 'vote',
		data: {
			firstItemId: firstItemId,
			secondItemId: secondItemId,
			winnerItemId: winnerItemId
		},
		cache: false,
		success: function(html){
			$("#vs").html(html);
		}
	});
}
// -->
</script>
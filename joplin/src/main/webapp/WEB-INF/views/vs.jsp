<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

	<form id="vsForm" action="vote" method="post">
		<input type="hidden" id="firstItemId" name="firstItemId" value="${firstItem.id}" />
		<input type="hidden" id="secondItemId" name="secondItemId" value="${secondItem.id}" />
		<input type="hidden" id="winnerItemId" name="winnerItemId" value="" />
		<div class="row">
			<div class="span6 centering">
				<div>
					<a data-toggle="modal" href="#video1">
						<img id="firstThumbnail" class="vThumbnail" src="${firstItem.thumbnailUrl}" width="320" height="240" />
					</a>
					<h5>
						<a data-toggle="modal" href="#video1"><i class="icon-play"></i>&nbsp;Click image to view video</a>
					</h5>
				</div>
				<br/>
				<input type="button" id="firstBtn" class="btn-large" name="firstBtn" value="Good!" />
			</div>
			<div class="span6 centering">
				<div>
					<a data-toggle="modal" href="#video2">
						<img id="secondThumbnail" class="vThumbnail" src="${secondItem.thumbnailUrl}" width="320" height="240" />
					</a>
					<h5>
						<a data-toggle="modal" href="#video2"><i class="icon-play"></i>&nbsp;Click image to view video</a>
					</h5>
				</div>
				<br/>
				<input type="button" id="secondBtn" class="btn-large" name="secondBtn" value="Good!" />
			</div>
		</div>
	</form>
	<div class="modal hide fade" id="video1">
		<div class="modal-header">
			<button class="close" data-dismiss="modal">×</button>
			<h4>Do you like this?</h4>
		</div>
		<div class="modal-body">
			<iframe width="530" height="299" src="http://www.youtube.com/embed/${firstItem.videoId}?rel=0&autoplay=1" frameborder="0" allowfullscreen></iframe>
		</div>
	</div>
	<div class="modal hide fade" id="video2">
		<div class="modal-header">
			<button class="close" data-dismiss="modal">×</button>
			<h4>Do you like this?</h4>
		</div>
		<div class="modal-body">
			<iframe width="530" height="299" src="http://www.youtube.com/embed/${secondItem.videoId}?rel=0&autoplay=1" frameborder="0" allowfullscreen></iframe>
		</div>
	</div>
<script type="text/javascript">
<!--
$(function(){
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
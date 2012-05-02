<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

	<form id="vsForm" action="vote" method="post">
		<input type="hidden" id="firstItemId" name="firstItemId" value="${firstItem.id}" />
		<input type="hidden" id="secondItemId" name="secondItemId" value="${secondItem.id}" />
		<input type="hidden" id="winnerItemId" name="winnerItemId" value="" />
		<div class="row">
			<div class="span6 centering">
				<div>
					<a class="vDialogBtn" href="#${firstItem.videoId}">
						<img class="vThumbnail" src="${firstItem.thumbnailUrl}" width="320" height="240" alt="first video thumbnail" />
					</a>
					<h5>
						<a class="vDialogBtn" href="#${firstItem.videoId}"><i class="icon-play"></i>&nbsp;Click image to view video</a>
					</h5>
				</div>
				<br/>
				<input type="button" id="firstBtn" class="btn-large" name="firstBtn" value="Good!" />
			</div>
			<div class="span6 centering">
				<div>
					<a class="vDialogBtn" href="#${secondItem.videoId}">
						<img class="vThumbnail" src="${secondItem.thumbnailUrl}" width="320" height="240" alt="second video thumbnail" />
					</a>
					<h5>
						<a class="vDialogBtn" href="#${secondItem.videoId}"><i class="icon-play"></i>&nbsp;Click image to view video</a>
					</h5>
				</div>
				<br/>
				<input type="button" id="secondBtn" class="btn-large" name="secondBtn" value="Good!" />
			</div>
		</div>
	</form>

	<div class="modal hide fade" id="vDialog">
		<div class="modal-header">
			<button class="close" data-dismiss="modal">×</button>
			<h4>Do you like this?</h4>
		</div>
		<div class="modal-body centering"></div>
	</div>

<script type="text/javascript">
<!--
var vfrm = '<iframe width="530" height="299" src="http://www.youtube.com/embed/_VID_?rel=0&autoplay=1" frameborder="0" allowfullscreen></iframe>';
var canVote = false;
var viewed1st = null;
$(function(){
	$('.vDialogBtn').click(function(){
		var videoId = $(this).attr('href').substring(1);
		setCanVote(videoId);
		$('#vDialog').on('show', function(){$('.modal-body').html(vfrm.replace('_VID_',videoId));});
		$('#vDialog').on('hidden', function(){$('.modal-body').html('');});
		$('#vDialog').modal();
	});
	$('input[name="firstBtn"]').click(function(){vote('${firstItem.id}','${secondItem.id}');});
	$('input[name="secondBtn"]').click(function(){vote('${secondItem.id}','${firstItem.id}');});
});
function setCanVote(videoId){
	if(viewed1st == null){
		viewed1st = videoId;
	} else {
		if(viewed1st != videoId) canVote = true;
	}
	return;
}
function vote(winnerItemId,loserItemId){
	if(canVote){
		winningRun();
		$.ajax({
			type: 'POST',
			url: 'vote',
			data: {
				winnerItemId: winnerItemId,
				loserItemId: loserItemId
			},
			cache: false,
			success: function(html){
				$("#vs").html(html);
			}
		});
	}else{
		alertVote("You didn't watch both videos, did you?");
	}
}
// -->
</script>
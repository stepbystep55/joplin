<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

	<form id="vsForm" action="vote" method="post">
		<input type="hidden" id="firstItemId" name="firstItemId" value="${firstItem.id}" />
		<input type="hidden" id="secondItemId" name="secondItemId" value="${secondItem.id}" />
		<input type="hidden" id="winnerItemId" name="winnerItemId" value="" />
		<div class="row">
			<div class="span6 centering">
				<div>
					<object width="400" height="233">
						<param name="movie" value="http://www.youtube.com/v/${firstItem.videoId}?version=3&amp;hl=ja_JP&amp;rel=0"></param>
						<param name="allowFullScreen" value="true"></param>
						<param name="allowscriptaccess" value="always"></param>
						<embed
							src="http://www.youtube.com/v/${firstItem.videoId}?version=3&amp;hl=ja_JP&amp;rel=0"
							type="application/x-shockwave-flash" width="400" height="233"
							allowscriptaccess="always" allowfullscreen="true">
						</embed>
					</object>
					<%--
					<!-- ajax実行後、IE8で画面がおかしくなるためiframeではなくobject版採用 -->
					<iframe
						width="400" height="233"
						src="http://www.youtube.com/embed/${firstItem.videoId}?rel=0"
						frameborder="0" allowfullscreen>
					</iframe>
					--%>
				</div>
				<input type="button" id="firstBtn" class="btn-large" name="firstBtn" value="Good!" />
			</div>
			<div class="span6 centering">
				<div>
					<object width="400" height="233">
						<param name="movie" value="http://www.youtube.com/v/${secondItem.videoId}?version=3&amp;hl=ja_JP&amp;rel=0"></param>
						<param name="allowFullScreen" value="true"></param>
						<param name="allowscriptaccess" value="always"></param>
						<embed
							src="http://www.youtube.com/v/${secondItem.videoId}?version=3&amp;hl=ja_JP&amp;rel=0"
							type="application/x-shockwave-flash" width="400" height="233"
							allowscriptaccess="always" allowfullscreen="true">
						</embed>
					</object>
					<%--
					<iframe
						width="400" height="233"
						src="http://www.youtube.com/embed/${secondItem.videoId}?rel=0"
						frameborder="0" allowfullscreen>
					</iframe>
					--%>
				</div>
				<input type="button" id="secondBtn" class="btn-large" name="secondBtn" value="Good!" />
			</div>
		</div>
	</form>
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
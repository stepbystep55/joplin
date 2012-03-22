<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<section id="match">
	<form id="matchForm" action="vote" method="post">
		<input type="hidden" id="firstItemId" name="firstItemId" value="${firstItem.id}" />
		<input type="hidden" id="secondItemId" name="secondItemId" value="${secondItem.id}" />
		<input type="hidden" id="winnerItemId" name="winnerItemId" value="" />
		<div class="row">
			<div class="span6 centering">
				<div>
					<iframe width="400" height="233" src="http://www.youtube.com/embed/${firstItem.videoId}?rel=0" frameborder="0" allowfullscreen></iframe>
				</div>
				<input type="button" id="firstBtn" class="btn-large" name="firstBtn" value="Good!" />
			</div>
			<div class="span6 centering">
				<div>
					<iframe width="400" height="233" src="http://www.youtube.com/embed/${secondItem.videoId}?rel=0" frameborder="0" allowfullscreen></iframe>
				</div>
				<input type="button" id="secondBtn" class="btn-large" name="secondBtn" value="Good!" />
			</div>
		</div>
	</form>
</section>

<div id="shout" style="display:none; z-index:99; position:fixed; width:100%; top:20%; left:45%;"><h1 style="color:red;">どんだけ～！</h1></div>

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
			$("#match").html(html);
			$('#shout').show();
			$('#shout').fadeOut(2000);
		}
	});
}
// -->
</script>
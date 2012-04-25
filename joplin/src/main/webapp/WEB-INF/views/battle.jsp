<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="_headBase.jsp"%>
	<title>Battle</title>
</head>
<body>

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse" data-target="nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
				<a class="brand" href="#">VivaJoplin</a>
				<div class="nav-collapse">
					<ul class="nav">
						<li class="active"><a href="#">Battle</a></li>
						<li><a href="rank">Ranking</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="container">

		<header class="header">
			<h1><c:out value="${article.subject}" /></h1>
			<div class="row">
				<div class="span12 centering"  style="line-height: 48px; color:#FF6600; font-size: 36px;">
					<span>&nbsp;</span><span id="shout"></span>
				</div>
			</div>
		</header>

		<section id="vs">
		</section>

		<c:if test="${(articleId != 'noarticle')}">
		<section id="tool">
			<div class="row">
				<div class="span12">
					<form id="itemForm" action="item" method="post">
						<a id="itemLink" href="javascript:void(0)">Post yours</a>
					</form>
				</div>
			</div>
			<div class="row"><div class="span12">&nbsp;</div></div>
			<div class="row">
				<div class="span12">
					<div class="fb-like" data-href="${homeUrl}" data-send="false" data-width="450" data-show-faces="true"></div>
				</div>
			</div>
		</section>
		</c:if>

		<jsp:include page="_footer.jsp"/>
	</div>

<%@ include file="_footBase.jsp"%>
<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/ja_JP/all.js#xfbml=1&appId=${facebookClientId}";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>
<script type="text/javascript">
<!--
$(function(){
	$.ajax({
		type: 'GET',
		url: 'vs',
		cache: false,
		success: function(html){
			$("#vs").html(html);
		}
	});
	$('#itemLink').click(function() {
		$('#itemForm').submit();
		return false;
	});
});
var msgArr = new Array(
'%u3069%u3093%u3060%u3051%uFF5E'
,'%u30DE%u30B8%uFF01%uFF1F'
,'%5C%28%5Eo%5E%29/'
,'Oh%2C%20babe%21'
,'%u304A%u30FC%u3001%u3058%u30FC%u3056%u3059%u3002'
,'%u30C1%u30E5%u30C3%uFF08%u306F%u3041%u3068%uFF09'
,'Yeaaahh%21'
,'%u3046%u30FC%u3001%u30DE%u30F3%u30DC%uFF01'
,'%u4F1A%u5FC3%u306E%u4E00%u6483%uFF01'
,'%u30A8%u30FC%u30FC%u30FC%u3063%uFF01'
,'%u30DE%u30B8%u306A%u3093%uFF1F'
,'Thank%20you.'
,'Viva%21%21'
,'Viva%21%20Viva%21%20Viva%20Joplin%21%21'
,'You%20are%20so%20nice.'
,'You%20are%20so%20cute%20%3C3'
,'%u2665'
,'Summertime%21'
,'Be%20my%20baby%21'
,'Fxxk%20you%u2661'
,'%u5ACC%u3044%u3058%u3083%u306A%u3044%u306D%u30A7'
,'%u304A%u524D%u3001%u6700%u9AD8%u3067%u3059%u3002'
,'%u3061%u3087%u3079%u308A%u3050%u3045%u30FC'
,'%u8B1D%u8B1D'
,'%u2605%uFF3C%28%5E0%5E%29/%u2605'
,'%u591C%u9732%u6B7B%u82E6'
,'%u30B2%u30ED%u30B2%u30ED'
,'%u30B6%u30AF%u5C02%u7528%u30B7%u30E3%u30A2'
,'%u3044%u3084%u3093%20%28%5E_%5E%3B%29'
,'v%28%5E_%5E%29v'
,'orz'
,'%28%uFF9F%u2207%uFF9F%20%3B%29%u30A8%u30A8%u3063%uFF01%uFF1F'
);
function winningRun(){
	var idx = Math.floor(Math.random()*msgArr.length);
	$('#shout').html(unescape(msgArr[idx])).show().fadeOut(2000);
	//$('#shout').html(unescape(msgArr[idx])).show().slideUp(2000);
}
// -->
</script>
</body>
</html>

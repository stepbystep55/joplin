<%@ page language="java" session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<div id="header_menu">
		<ul>
			<li><a href="<%= request.getContextPath() %>/">top</a></li>
			<li><a href="<%= request.getContextPath() %>/article/">article</a></li>
		</ul>
	</div>
	<div id="header_userinfo">
		<c:choose>
		<c:when test="${userDisplay != null}">
			<a href="${userDisplay.profileUrl}">
				<img alt="profile image" src="${userDisplay.imageUrl}">
				<c:out value="${userDisplay.name}"/>
			</a>
		</c:when>
		<c:otherwise>
			anonymous
		</c:otherwise>
		</c:choose>
	</div>
	<h1>Viva Joplin!</h1>
<hr/>
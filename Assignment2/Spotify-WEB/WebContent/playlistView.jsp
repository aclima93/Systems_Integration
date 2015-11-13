<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${empty user}">
	<c:redirect url="/Main"/>
</c:if>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Spotify</title>
	<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
</head>
<body>
	<div class="w3-container">
		<header class="w3-container w3-blue w3-row">
			<div class="w3-row">
				<h2 class="w3-col m10">Spotify</h2>
				<div class="w3-col m2">
					<form class="w3-form" method="post" action="Main">
						<input type="hidden" name="action" value="logout"/>
						<input type="submit" class="w3-btn w3-cyan" value="Logout"/>
					</form>
				</div>
			</div>
			<div class="w3-row">
				<div class="w3-col m10">
					<h3>Playlists</h3>
				</div>
				<div class="w3-col m2">
					<form class="w3-form" method="post" action="Main">
						<input type="hidden" name="action" value="playlistMenu"/>
						<input type="submit" class="w3-btn w3-cyan" value="Playlist Menu"/>
					</form>
				</div>
			</div>
		</header>
		
		<div class="w3-container w3-aqua">
			<div class="w3-row">
				<form method="post" action="Main">
					<div class="w3-col m9">
						<input class="w3-input w3-aqua" type="text" name="name" value="${playlist.name}"/>
					</div>
					<div class="w3-col m3">
						<input type="hidden" name="action" value="changePlaylistName"/>
						<input type="hidden" name="playlist" value="${playlist.id}"/>
						<input type="submit" class="w3-btn w3-aqua" value="Change name"/>
					</div>
				</form>
			</div>
		</div>
		<c:if test="${not empty message}">
			<h3>${message}</h3>
		</c:if>
		<c:if test="${not empty list}">
			<h1>Yay</h1>
		</c:if>
	</div>
</body>
</html>
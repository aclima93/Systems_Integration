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
						<input type="hidden" name="action" value="index"/>
						<input type="submit" class="w3-btn w3-cyan" value="Main Menu"/>
					</form>
				</div>
			</div>
		</header>
		<div class="w3-container">
			<c:if test="${not empty message}">
				<h3>${message}</h3>
			</c:if>
			<form class="w3-form" method="post" action="Main">
				<div class="w3-input-group">
					<label>Create playlist</label>
					<input class="w3-input" name="name" type="text" placeholder="Playlist name" required/>
					<input type="hidden" name="action" value="createPlaylist"/>
					<input type="submit" class="w3-btn w3-cyan" value="Create playlist"/>
				</div>
			</form>
			<div class="w3-row">
				<div class="w3-col m4">
					<form class="w3-form" method="post" action="Main">
						<input type="hidden" name="action" value="playlistMenu"/>
						<input type="hidden" name="order" value="asc"/>
						<input type="submit" class="w3-btn w3-cyan" value="Sort playlists in ascending order"/>
					</form>
				</div>
				<div class="w3-col m4">
					<form class="w3-form" method="post" action="Main">
						<input type="hidden" name="action" value="playlistMenu"/>
						<input type="hidden" name="order" value="desc"/>
						<input type="submit" class="w3-btn w3-cyan" value="Sort playlists in descending order"/>
					</form>
				</div>
			</div>
			<c:forEach items="${list}" var="element">
				<div class="w3-row">
					<div class="w3-col m10">
						<form class="w3-form" method="post" action="Main">
							<input type="hidden" name="playlist" value="${element.id}"/>
							<input type="hidden" name="action" value="listMusicInPlaylist"/>
							<input style="width:100%" type="submit" class="w3-btn w3-teal" value="${element.name}"/>
						</form>
					</div>
					<div class="w3-col m2">
						<form class="w3-form" method="post" action="Main">
							<input type="hidden" name="playlist" value="${element.id}"/>
							<input type="hidden" name="action" value="deletePlaylist"/>
							<input style="width:100%" type="submit" class="w3-btn w3-teal" value="Delete"/>
						</form>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>
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
					<h3>Edit song info</h3>
				</div>
				<div class="w3-col m2">
					<form class="w3-form" method="post" action="Main">
						<input type="hidden" name="action" value="musicMenu"/>
						<input type="submit" class="w3-btn w3-cyan" value="Music Menu"/>
					</form>
				</div>
			</div>
		</header>
		<div class="w3-container">
			<c:if test="${not empty message}">
				<h3>${message}</h3>
			</c:if>
			<form class="w3-form" method="post" action="Main">
				<label>Change title</label>
				<input class="w3-input" name="title" type="text" placeholder="${song.title}" required/>
				<input type="hidden" name="action" value="editSongTitle"/>
				<input type="submit" class="w3-btn w3-cyan" value="Change title"/>
			</form>
			<form class="w3-form" method="post" action="Main">
				<label>Change artist</label>
				<input class="w3-input" name="artist" type="text" placeholder="${song.artist}" required/>
				<input type="hidden" name="action" value="editSongTitle"/>
				<input type="submit" class="w3-btn w3-cyan" value="Change artist"/>
			</form>
			<form class="w3-form" method="post" action="Main">
				<label>Change album</label>
				<input class="w3-input" name="album" type="text" placeholder="${song.album}" required/>
				<input type="hidden" name="action" value="editSongAlbum"/>
				<input type="submit" class="w3-btn w3-cyan" value="Change album"/>
			</form>
			<form class="w3-form" method="post" action="Main">
				<label>Change year</label>
				<input class="w3-input" name="year" type="text" placeholder="${song.year}" required/>
				<input type="hidden" name="action" value="editSongYear"/>
				<input type="submit" class="w3-btn w3-cyan" value="Change year"/>
			</form>
			<form class="w3-form" method="post" action="Main">
				<input type="hidden" name="action" value="deleteSong"/>
				<input type="submit" class="w3-btn w3-deep-orange" value="Delete song"/>
			</form>
		</div>
	</div>
</body>
</html>
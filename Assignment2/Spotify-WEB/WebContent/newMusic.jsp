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
					<h3>Add music</h3>
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
				<input class="w3-input" type="text" name="title" placeholder="Song title" required/>
				<input class="w3-input" type="text" name="artist" placeholder="Song artist" required/>
				<input class="w3-input" type="text" name="album" placeholder="Song album" required/>
				<input class="w3-input" type="text" name="year" placeholder="Song year" required/>
				<input type="hidden" name="action" value="verifyNewSong"/>
				<input type="submit" class="w3-btn w3-cyan" value="Create song"/>
			</form>
		</div>
	</div>
</body>
</html>
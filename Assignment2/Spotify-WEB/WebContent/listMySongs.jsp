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
					<h3>My songs</h3>
				</div>
				<div class="w3-col m2">
					<form class="w3-form" method="post" action="Main">
						<input type="hidden" name="action" value="musicMenu"/>
						<input type="submit" class="w3-btn w3-cyan" value="Music Menu"/>
					</form>
				</div>
			</div>
		</header>
		<br/>
		<c:if test="${not empty message}">
			<h3>${message}</h3>
		</c:if>
		<div class="w3-row w3-cyan">
			<div class="w3-col m3">
				<p>Title</p>
			</div>
			<div class="w3-col m3">
				<p>Artist</p>
			</div>
			<div class="w3-col m3">
				<p>Album</p>
			</div>
			<div class="w3-col m1">
				<p>Year</p>
			</div>
			<div class="w3-col m2" style="vertical-align:center; text-align:center">
				<p>Options</p>
			</div>
			<c:forEach items="${list}" var="element">
					<div class="w3-col m3">
						<p>${element.title}</p>
					</div>
					<div class="w3-col m3">
						<p>${element.artist}</p>
					</div>
					<div class="w3-col m3">
						<p>${element.album}</p>
					</div>
					<div class="w3-col m1">
						<p>${element.year}</p>
					</div>
					<div class="w3-col m2">
						<form style="vertical-align:center; text-align:center" method="post" action="Main">
							<input type="hidden" name="song" value="${element.id}"/>
							<input type="hidden" name="action" value="editSongInfo"/>
							<input type="submit" class="w3-btn w3-cyan" value="Edit"/>
						</form>
					</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>
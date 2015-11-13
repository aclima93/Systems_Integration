<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Spotify</title>
	<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
</head>
<body>
	<div class="w3-container">
		<header class="w3-container w3-blue">
			<h2>Spotify</h2>
			<h4>${message}</h4>
		</header>
		<div class="w3-container">
			<form class="w3-form" method="post" action="Main">
				<div class="w3-input-group">
					<input type="submit" class="w3-btn w3-cyan" value="Go back to login page"/>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
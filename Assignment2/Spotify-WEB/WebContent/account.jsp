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
					<h3>Account settings</h3>
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
			<h3>${message}</h3>
			<form class="w3-form" method="post" action="Main">
				<div class="w3-input-group">
					<label>Change name</label>
					<input class="w3-input" name="name" type="text" placeholder="${user.name}" required/>
					<input type="hidden" name="action" value="changeName"/>
					<input type="submit" class="w3-btn w3-cyan" value="Change name"/>
				</div>
			</form>
			<form class="w3-form" method="post" action="Main">
				<div class="w3-input-group">
					<label>Change email</label>
					<input class="w3-input" name="email" type="text" placeholder="${user.email}" required/>
					<input type="hidden" name="action" value="changeEmail"/>
					<input type="submit" class="w3-btn w3-cyan" value="Change e-mail"/>
				</div>
			</form>
			<form class="w3-form" method="post" action="Main">
				<div class="w3-input-group">
					<label>Change password</label>
					<input class="w3-input" name="password" type="password" placeholder="********" required/>
					<input type="hidden" name="action" value="changePassword"/>
					<input type="submit" class="w3-btn w3-cyan" value="Change password"/>
				</div>
			</form>
			<form class="w3-form" method="post" action="Main">
				<div class="w3-input-group">
					<input type="hidden" name="action" value="deleteUser"/>
					<input type="submit" class="w3-btn w3-deep-orange" value="Delete account"/>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
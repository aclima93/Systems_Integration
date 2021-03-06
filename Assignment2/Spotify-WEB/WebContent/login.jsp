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
			<h2>You have to login</h2>
			<h3><c:out value="${user}"/></h3>
		</header>
		<div class="w3-row">
			<div class="w3-col w3-half">
				<div class="w3-container">
					<h3>Register</h3>
					<form class="w3-form" method="post" action="Main">
						<div class="w3-input-group">
							<label>Name</label>
							<input class="w3-input" name="name" type="text" required/>
						</div>
						<div class="w3-input-group">
							<label>E-mail</label>
							<input class="w3-input" name="email" type="text" required/>
						</div>
						<div class="w3-input-group">
							<label>Password</label>
							<input class="w3-input" name="password" type="password" required/>
						</div>
						<input type="hidden" name="action" value="register"/>
						<input type="submit" class="w3-btn w3-cyan" value="Register"/>
					</form>
				</div>
			</div>
			<div class="w3-col w3-half">
				<div class="w3-container">
					<h3>Login</h3>
					<form class="w3-form" method="post" action="Main">
						<div class="w3-input-group">
							<label>E-mail</label>
							<input class="w3-input" name="email" type="text" required/>
						</div>
						<div class="w3-input-group">
							<label>Password</label>
							<input class="w3-input" name="password" type="password" required/>
						</div>
						<input type="hidden" name="action" value="login"/>
						<button type="submit" class="w3-btn w3-cyan">Login</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
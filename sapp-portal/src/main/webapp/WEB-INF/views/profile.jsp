<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Spittle</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/style.css" />">
</head>
<body>
	<h1>Your profile</h1>
	User Name:
	<c:out value="${spitter.userName}"></c:out>
	<br /> First Name:
	<c:out value="${spitter.firstName}"></c:out>
	<br /> Last Name:
	<c:out value="${spitter.lastName}"></c:out>
</body>
</html>
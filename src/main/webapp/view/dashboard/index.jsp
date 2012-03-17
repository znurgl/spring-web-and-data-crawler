<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Dashboard</title>
</head>
<body>
	Kampányok:
	
		<c:forEach var="campaign" items="${campaigns}">
			<p>${campaign.name}</p>
			<p>Kulcsszavak: </p>
			<ul>
			<c:forEach var="keyword" items="${campaign.keywords}">
				<li>${keyword.value}</li>
			</c:forEach>
			</ul>
		</c:forEach>
	

</body>
</html>

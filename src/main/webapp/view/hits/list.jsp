<%@ page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>
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

	<c:forEach var="data" items="${dataList}">
		<div class="data-header">${data.originalDate} > ${data.type} > <a href="${data.url}" target="_blank">${data.url}</a></div>
		<div class="data-body">
			${data.body} 
			
				Kulcsszavak:
				<c:forEach var="keyword" items="${data.keywords}">
				${keyword.value} 
			</c:forEach>
			
		</div>
		<div class="data-footer"></div>
	</c:forEach>


</body>
</html>

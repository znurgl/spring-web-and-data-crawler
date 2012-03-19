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
<title>Találatok</title>
<script type="text/javascript" src="/resources/js/hits.js"></script>
</head>
<body>
	<div id="dialog-modal" title="Basic modal dialog">
		<p>Adding the modal overlay screen makes the dialog look more
			prominent because it dims out the page content.</p>
	</div>

	<div id="filter">
		<form name="filterForm" id="filterForm">
			<div class="harmad">
				KAMPÁNYVÁLASZTÁS<br /> <select name="campaign">
					<c:forEach var="campaign" items="${campaigns}">
						<option value="${campaign.id}">${campaign.name}</option>
					</c:forEach>
				</select>
			</div>
			<div id="postData">
				<input type="button" value="Szűrés" onClick="dataFilter()" />
			</div>
			<div id="createReport">
				<input type="button" value="Riport készítés" onClick="createReport()" />
			</div>
		</form>
	</div>

	<div id="data-div">
		<c:forEach var="data" items="${dataList}">
			<div class="data-content">
				<div class="data-header">
					${data.originalDate} > ${data.type} > <a href="${data.url}"
						target="_blank">${data.url}</a>
				</div>
				<div class="data-body">
					<div class="data-icon ${data.type}-icon"></div>
					${data.body} Kulcsszavak:
					<c:forEach var="keyword" items="${data.keywords}">
				${keyword.value} 
			</c:forEach>

				</div>
				<div class="data-footer"></div>
			</div>
		</c:forEach>
	</div>


</body>
</html>

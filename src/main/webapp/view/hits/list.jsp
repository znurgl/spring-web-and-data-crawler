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
	<div id="dialog-modal" title="Riport készítése" style="display: none;">
		<p>
			Riport neve: <input type="text" name="name" id="name" />.pdf
		</p>
		<p>
			A riport mentés után a <a href="/reports">riportok</a> menüpont alatt
			található meg.
		</p>
		<p>
			<input type="checkbox" name="download" /> a riportot le szeretném
			egyből tölteni.
		</p>
		<input type="button" value="riport készítése" onClick="createReport()" />
	</div>

	<div id="filter">
		<form name="filterForm" id="filterForm">
			<div class="harmad">
				KAMPÁNYVÁLASZTÁS<br /> <select name="campaign">
					<c:forEach var="campaign" items="${campaigns}">
						<option
							<c:if test="${active_campaign_id == campaign.id}" > selected="selected"</c:if>
							value="${campaign.id}">${campaign.name}</option>
					</c:forEach>
				</select>
			</div>
			<p>
				Kulcsszavak:
				<c:forEach var="keyword" items="${filter_keywords}">
					<input type="checkbox" id="filter_keyword-${keyword.id}"
						name="filter_keyword-${keyword.id}" /> ${keyword.value}  
			</c:forEach>
			</p>
			<div id="postData">
				<input type="button" value="Szűrés" onClick="dataFilter()" />
			</div>
			<div id="createReport">
				<input type="button" value="Riport készítés"
					onClick="createReportModal()" />
			</div>
		</form>
	</div>

	<div id="data-div"></div>


</body>
</html>

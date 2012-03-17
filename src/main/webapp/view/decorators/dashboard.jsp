<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<html>
<head>
<title><decorator:title default="Intelligens találatok" /> - BrandBrother</title>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="hu-hu" />
<link rel="stylesheet" type="text/css" href="/resources/style.css">
<decorator:head />
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<a href="/"><img id="header-logo" src="/images/logo.png" /></a>

			<div id="header-menu-1">
				<a href="/dashboard">irányítópult</a>
			</div>
			<div id="header-menu-2">
				<a href="/campaigns">kampányok</a>
			</div>
			<div id="header-menu-3">
				<a href="/hits">találatok</a>
			</div>
			<div id="header-menu-4">
				<a href="/reports">riportok</a>
			</div>
			<div id="header-nav"></div>
		</div>
		<div id="content">
		<decorator:body />
		</div>
		<div id="footer" ></div>
	</div>
</body>
</html>
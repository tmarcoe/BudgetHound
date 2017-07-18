<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib prefix="sec" uri="/WEB-INF/tld/security.tld"%>
<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost/donzalma_peachys" user="donzalma_admin" password="In_heaven3" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="Enjoy the taste of the Philippines. Enjoy Peach's Coffee.">
<meta name="keywords" content="coffee">
<title><tiles:insertAttribute name="title"></tiles:insertAttribute></title>
<link rel="icon" href="<c:url value='/images/favicon.png' />">
<link href="/css/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="/script/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="/script/Chart.js"></script>
<sec:getPrincipal />
<tiles:insertAttribute name="includes"></tiles:insertAttribute>

</head>

<body>
	<div class="toolbar">
		<tiles:insertAttribute name="toolbar"></tiles:insertAttribute>
	</div>
	<c:choose>
		<c:when test="${principal  != 'anonymousUser'}">
			<sql:query var="rs" dataSource="${ds}">
				SELECT firstname, lastname, currency, user_id FROM user_profile WHERE username='${principal}'
			</sql:query>
			<c:forEach var="row" items="${rs.rows}">
				<c:set var="firstName" value="${row.firstname}" />
				<c:set var="lastName" value="${row.lastname}" />
				<c:set var="currency" value="${row.currency}" />
				<c:set var="user_id" value="${row.user_id}" />
			</c:forEach>
		</c:when>
		<c:otherwise>
			<c:set var="firstName" value="Anonymous" />
			<c:set var="lastName" value="User" />
		</c:otherwise>
	</c:choose>
	<input id="nameHolder" type="hidden" value="${firstName}${lastName}">
	<table class="leftside">
		<tr>
			<c:if test="${principal  != 'anonymousUser'}">
				<c:set var="total" value="${0}" />
				<td>Welcome back</td>
			</c:if>
			<td><h6 class="label">${firstName}</h6></td>
			<td><h6 class="label">${lastName}</h6></td>
		</tr>
	</table>
	<script type="text/javascript">
		function getCookie(cname) {
			var name = cname + "=";
			var decodedCookie = decodeURIComponent(document.cookie);
			var ca = decodedCookie.split(';');
			for (var i = 0; i < ca.length; i++) {
				var c = ca[i];
				while (c.charAt(0) == ' ') {
					c = c.substring(1);
				}
				if (c.indexOf(name) == 0) {
					return c.substring(name.length, c.length);
				}
			}
			return "";
		}
	</script>
	<div class="heading">
		<div class="container">
			<tiles:insertAttribute name="heading_title"></tiles:insertAttribute>
		</div>
	</div>
	<div class="content">
		<tiles:insertAttribute name="content"></tiles:insertAttribute>
	</div>

	<tiles:insertAttribute name="footer" />

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="/WEB-INF/tld/security.tld"%>

<div class="nav-bar">
	<div class="container">
		<ul class="nav">
			<li><a href="/public/home">Home</a></li>
			<sec:isAuthenticated>
				<li><a href="/logout">logout</a></li>
				<li><a href="/user/root/listtrans">View Transactions</a></li>
				<li><a href="/user/root/listcategories">Categories</a></li>
			</sec:isAuthenticated>
			<sec:hasRole role="ADMIN">
				<li><a href="/admin/listusers">Users</a></li>
				<li><a href="/admin/shutdown">Shutdown</a></li>
			</sec:hasRole>		
			<li><a href="/public/help">Help</a></li>
		</ul>
	</div>
</div>
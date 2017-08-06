<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="/WEB-INF/tld/security.tld"%>

<div class="nav-bar">
	<div class="container">
		<ul class="nav">
			<li><a class="south" href="/public/home" title="Home page" >Home</a></li>
			<sec:isAuthenticated>
				<li><a class="south" href="/logout" title="Logout" >logout</a></li>
				<li><a class="south" href="/user/root/listtrans" title="View/Edit/Create transactions">View Transactions</a></li>
				<li><a class="south" href="/user/root/listcategories" title="View/Edit/Create spending categories<p>This will also display the total that is spent on each category">Categories</a></li>
			</sec:isAuthenticated>
			<sec:hasRole role="ADMIN">
				<li><a class="south" href="/admin/listusers" title="List of registered users" >Users</a></li>
				<li><a class="south" href="#" onclick="shutDown()" title="Shutdown the website">Shutdown</a></li>
			</sec:hasRole>		
			<li><a class="south" href="/public/help" title="A brief introduction to BudgetHound">Help</a></li>
		</ul>
	</div>
</div>
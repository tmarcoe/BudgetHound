<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table class="tableview tableborder">
	<tr>
		<th>Id</th>
		<th>Name</th>
		<th>Adults</th>
		<th>Children</th>
		<th>Email</th>
		<th>Enabled</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
	</tr>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td>${item.household_id}</td>
			<td>${item.name}</td>
			<td>${item.num_adults}</td>
			<td>${item.num_children}</td>
			<td>${item.username}</td>
			<td>${item.enabled}</td>
			<td><button type="button" onclick="window.location.href='/admin/edituser?household_id=${item.household_id}'">Edit</button></td>
			<td><button type="button" onclick="remove(${item.household_id})">Delete</button></td>
		</tr>
	</c:forEach>
</table>
<script type="text/javascript">
	function remove(id) {
		if (confirm("Are you sure you wish to remove " + id) == true) {
			window.location.href='/admin/deleteuser?household_id=' + id;
		}
	}
</script>

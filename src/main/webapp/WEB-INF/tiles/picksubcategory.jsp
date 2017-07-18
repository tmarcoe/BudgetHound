<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table>
	<tr>
		<th>Category</th>
	</tr>
	<tr>
		<td><select>
			<c:forEach var="item" items="${catList}">
				<option value="${item.category}">${item.category}</option>
			</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td><input type="hidden" id="parent" name="parent" value="${parent}"></td>
	</tr>
	<tr>
		<td><button type="button" onclick="window.location.href='/user/listcategories'" >OK</button></td>
	</tr>
</table>

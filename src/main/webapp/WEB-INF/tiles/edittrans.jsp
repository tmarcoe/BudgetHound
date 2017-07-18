<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<sf:form method="post" action="/user/${parent}/updatetrans" commandName="register">
	<table>
		<tr>
			<th>Entry Id</th>
			<th>Date</th>
			<th>Household ID</th>
			<th>Recipient/Source</th>
		</tr>
		<tr>
			<td><sf:input path="entry_id" readonly="true"/></td>
			<td><sf:input path="trans_date" readonly="true"/></td>
			<td><sf:input path="household_id" readonly="true"/></td>
			<td><sf:input path="recipient" /></td>
		</tr>
		<tr>
			<th>Description</th>
			<th>withdrawal</th>
			<th>Deposit</th>
			<th>Category</th>
		</tr>
		<tr>
			<td><sf:input path="description"/></td>
			<td><sf:input path="withdrawal"/></td>
			<td><sf:input path="deposit"/></td>
			<td><sf:select path="category">
				<sf:option value="">--------</sf:option>
				<sf:options items="${catList}" />
			</sf:select>
			</td>
		</tr>
		<tr>
			<td><sf:button type="submit">Save</sf:button></td>
		</tr>
	</table>
</sf:form>
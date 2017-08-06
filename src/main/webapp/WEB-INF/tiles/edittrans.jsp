<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<sf:form method="post" action="/user/${parent}/updatetrans" commandName="register">
	<table>
		<tr>
			<th>Transaction Id</th>
			<th>Date</th>
			<th>Household ID</th>
			<th>Recipient/Source</th>
		</tr>
		<tr>
			<td><sf:input class="north" path="entry_id" readonly="true" title="Transaction Id" /></td>
			<td><sf:input class="north" path="trans_date" readonly="true" title="Transaction Date" /></td>
			<td><sf:input class="north" path="household_id" readonly="true" title="Your Id" /></td>
			<td><sf:input class="north" path="recipient"
					title="If this is a withdrawal, then this is the recipient.<p>If this is a deposit, then this is the source" /></td>
		</tr>
		<tr>
			<th>Description</th>
			<th>withdrawal</th>
			<th>Deposit</th>
			<th>Category</th>
		</tr>
		<tr>
			<td><sf:input class="north" path="description" title="Description of the transaction"/></td>
			<td><sf:input class="north" path="withdrawal" title="Amount of the withdrawal (0.0 if this is a deposit)"/></td>
			<td><sf:input class="north" path="deposit" title="Amount of the deposit (0.0 if this is a withdrawal)"/></td>
			<td><sf:select class="north" path="category" title="Assigned category if this is a withdrawal (blank for deposit)">
					<sf:option value="">--------</sf:option>
					<sf:options items="${catList}" />
				</sf:select></td>
		</tr>
		<tr>
			<td><sf:button type="submit">Save</sf:button></td>
			<td><button type="button" onclick="window.history.back();">Cancel</button></td>
		</tr>
	</table>
</sf:form>
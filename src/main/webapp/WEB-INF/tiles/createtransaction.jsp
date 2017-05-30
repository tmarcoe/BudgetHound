<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql"uri="http://java.sun.com/jsp/jstl/sql" %>

<sf:form method="post" action="/savetrans"
	commandName="withdrawal">
	<table>
		<tr>
			<th>Company</th>
			<th>Amount</th>
		</tr>
		<tr>
			<td><sf:input path="company" /></td>
			<td><sf:input path="amount"/>
		</tr>
		<tr>
			<th>Category</th>
			<th>SubCategory</th>
		</tr>
		<tr>
			<td><sf:select path="category">
				<sf:option value="">------</sf:option>
				<c:forEach var="item" items="${catList}">
					<sf:option value="${item.cat}">${item.cat}</sf:option>
				</c:forEach>
			</sf:select></td>
			<td><sf:select path="subCategory">
				<sf:option value="">------</sf:option>
				<c:forEach var="item" items="${subList}">
					<sf:option value="${item.sub}">${item.sub}</sf:option>
				</c:forEach>
			</sf:select></td>
		</tr>
		<tr>
			<th>Reason</th>
		</tr>
		<tr>
			<td><sf:textarea path="reason" rows="5" cols="50" /></td>
		</tr>
		<tr><td><sf:button type="submit">Save</sf:button><td></tr>
	</table>
</sf:form>
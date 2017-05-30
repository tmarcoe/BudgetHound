<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<sf:form method="post" action="/updatetrans"
	commandName="withdrawal">
	<table>
		<tr>
			<th>Transaction #:<fmt:formatNumber pattern="00000000" value="${withdrawal.entryId}"/></th>
			<th>Transaction Date:<fmt:formatDate value="${withdrawal.entryDate}"/></th>
		</tr>
		<tr><td colspan="2"><hr></td></tr>
		<tr><td><sf:hidden path="entryId"/></td></tr>
		<tr><td><sf:hidden path="entryDate"/></td></tr>
	</table>
	<table>
		<tr>
			<th>Company</th>
			<th>Amount</th>
		</tr>
		<tr>
			<td class="standout"><sf:input path="company" /></td>
			<td><sf:input path="amount"/>
		</tr>
		<tr>
			<th>Category</th>
			<th>SubCategory</th>
		</tr>
		<tr>
			<td><sf:input path="category"/></td>
			<td><sf:input path="subCategory"/></td>
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
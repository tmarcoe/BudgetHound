<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<sf:form method="post" action="/updatedeposit"
	commandName="deposit">
	<table>
		<tr>
			<th>Transaction #:<fmt:formatNumber pattern="00000000" value="${deposit.entryId}"/></th>
			<th>Transaction Date:<fmt:formatDate value="${deposit.entryDate}"/></th>
		</tr>
		<tr><td colspan="2"><hr></td></tr>
		<tr><td><sf:hidden path="entryId"/></td></tr>
		<tr><td><sf:hidden path="entryDate"/></td></tr>
	</table>
	<table>
		<tr>
			<th>Source</th>
			<th>Amount</th>
		</tr>
		<tr>
			<td><sf:input path="source" /></td>
			<td><sf:input path="amount"/>
		</tr>
		<tr><td><sf:button type="submit">Save</sf:button><td></tr>
	</table>
</sf:form>
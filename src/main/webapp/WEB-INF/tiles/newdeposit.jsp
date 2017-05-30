<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<sf:form method="post" action="/savedeposit"
	commandName="deposit">
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
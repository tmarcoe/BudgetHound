<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<sf:form method="post" action="/savecategory" commandName='category'>
	<table>
		<tr>
			<th>Enter the Category</th>
			<td><sf:input path="cat"/>
			<th>Taxible?</th>
			<td><sf:checkbox path="taxable"/></td>
		</tr>
		<tr>
			<td><sf:button type="submit">Save Category</sf:button></td>
		</tr>
	</table>
</sf:form>
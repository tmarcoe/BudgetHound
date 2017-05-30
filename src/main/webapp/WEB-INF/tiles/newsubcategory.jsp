<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<sf:form method="post" action="/saveSubCategory" commandName='subCategory'>
	<table>
		<tr>
			<th>Enter the Category</th>
			<td><sf:input path="sub"/>
		</tr>
		<tr>
			<td><sf:button type="submit">Save SubCategory</sf:button></td>
		</tr>
	</table>
</sf:form>
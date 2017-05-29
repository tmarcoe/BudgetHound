<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<sf:form method="post" action="/edittrans" commandName="objectList">

	<table class="tableView tableBorder centerTable">
		<caption class="lightText" >Witdrawals</caption>
		<tr>
			<th>Entry Id</th>
			<th>Date</th>
			<th>Company</th>
			<th>Reason</th>
			<th>Category</th>
			<th>SubCategory</th>
			<th>Amount</th>
			<th>&nbsp;</th>
		</tr>
		<c:forEach var="item" items="${objectList.pageList}">
			<tr>
				<td>${item.entryId}</td>
				<td><fmt:formatDate value="${item.entryDate}"/></td>
				<td>${item.company}</td>
				<td>${item.reason}</td>
				<td>${item.category}</td>
				<td>${item.subCategory}</td>
				<td>${item.amount}</td>
				<td><button type="button" onclick="window.location.href = '${pageContext.request.contextPath}/edittrans?entryId=${item.entryId}'">Edit</button>
			</tr>
		</c:forEach>
	</table>
</sf:form>
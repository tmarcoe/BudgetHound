<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<table  class="tableview tableshadow tableborder rjfourth rjfifth rjsixth">
	<caption>Parent: ${parent}</caption>
	<tr>
		<th>Date</th>
		<th>Source/Recipient</th>
		<th>Description</th>
		<th>Withdrawal</th>
		<th>Deposit</th>
		<th>Balance</th>
		<th>Category</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
	</tr>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td>
				<fmt:formatDate value="${item.trans_date}"/>
			</td>
			<td>
				${item.recipient}
			</td>
			<td>
				${item.description}
			</td>
			<td>
				<fmt:formatNumber type="currency"  value="${item.withdrawal}" currencySymbol="" /> 
			</td>
			<td>
				<fmt:formatNumber type="currency"  value="${item.deposit}" currencySymbol="" /> 
			</td>
			<td>
				<fmt:formatNumber type="currency"  value="${item.running_balance}" currencySymbol="" /> 
			</td>
			<td>
				${item.category}
			</td>
			<td><button type="button" onclick="window.location.href='/user/${parent}/edittrans?entry_id=${item.entry_id}'">Edit</button></td>
			<td><button type="button" onclick="remove('${item.entry_id}','${parent}')">Delete</button></td>		
		</tr>
	</c:forEach>
	<tfoot>	
		<tr>
			<td><button type="button" onclick="window.location.href='/user/${parent}/createtrans'">New Transaction</button></td>
		</tr>
	</tfoot>
</table>
<script type="text/javascript">
	function remove(entry_id, parent) {
		if (confirm("Are you sure you want to delete this transaction?") == true) {
			window.location.href="/user/"+ parent + "/deletetrans?entry_id=" + entry_id
		}
	}
</script>
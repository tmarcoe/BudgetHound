<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost/donzalma_budget" user="donzalma_admin"
	password="In_heaven3" />
<h3>Parent: ${parent}</h3>
<div class="error">${error}</div>
<table class="tableview tableshadow tableborder rjsixth rjseventh rjeighth">

	<tr>
		<th colspan="2">&nbsp;</th>
		<th>Date</th>
		<th>Source/Recipient</th>
		<th>Description</th>
		<th>Withdrawal</th>
		<th>Deposit</th>
		<th>Balance</th>
		<th>Category</th>
		<th>&nbsp;</th>
	</tr>
	<c:forEach var="item" items="${objectList.pageList}">
		<tr>
			<td><button class="north" type="button" onclick="window.location.href='/user/${parent}/edittrans?entry_id=${item.entry_id}'"
					title="Edit the transaction">Edit</button></td>
			<td><button class="north" type="button" onclick="remove('${item.entry_id}','${parent}')" title="Delete the transaction" >Delete</button></td>
			<td><fmt:formatDate value="${item.trans_date}" /></td>
			<td>${item.recipient}</td>
			<td>${item.description}</td>
			<td><fmt:formatNumber type="currency" value="${item.withdrawal}" currencySymbol="" /></td>
			<td><fmt:formatNumber type="currency" value="${item.deposit}" currencySymbol="" /></td>
			<td><fmt:formatNumber type="currency" value="${item.running_balance}" currencySymbol="" /></td>
			<td>${item.category}</td>
			<sql:query var="child" dataSource="${ds}"
				sql="SELECT * FROM categories WHERE household_id = ${item.household_id} AND parent = '${item.category}'" />
			<c:choose>
				<c:when test="${child.rowCount > 0}">
					<td><button type="button" onclick="window.location.href='/user/${item.category}/listtrans'">Subcategories</button></td>
				</c:when>
				<c:otherwise>
					<td>&nbsp;</td>
				</c:otherwise>
			</c:choose>
		</tr>
	</c:forEach>
	<tfoot class="tablefooter">
		<tr>
			<c:choose>
				<c:when test="${parent != 'root'}">
					<td colspan="8"><button type="button" onclick="window.location.href='/user/${parent}/uponetrans'">Up One Level</button></td>
				</c:when>
				<c:otherwise>
					<td colspan="8">&nbsp;</td>
				</c:otherwise>
			</c:choose>
			<td><button class="north" type="button" onclick="window.location.href='/user/${parent}/createtrans'" title="Add more transactions">New
					Entry</button></td>
			<td><button class="east" type="button" onclick="window.location.href='/user/${parent}/budgetbreakdown'"
					title="See a graph of spending percentages">Breakdown</button></td>
		</tr>
	</tfoot>
</table>
<div id="confirmDialog" title="Archive Required" class="hiddenPopup">
	<h5>It is strongly suggested that you archive The previous month's transactions.</h5>
	<p>Do you wish to do that now?</p>
</div>

<script type="text/javascript">
	var tst = ${hasPrev};
	var targetUrl = "/user/root/archive";
	$(document).ready(function() {
		if (tst == true) {
			$("#confirmDialog").dialog({
				autoOpen : true,
				modal : true,
				buttons : {
					"Yes" : function() {
						$(this).dialog("close");
						window.location.href = targetUrl;
					},
					"No" : function() {
						$(this).dialog("close");
					}
				}
			});
		} else {
			$("#confirmDialog").hide();
		}
	});

	function remove(entry_id, parent) {
		if (confirm("Are you sure you want to delete this transaction?") == true) {
			window.location.href = "/user/" + parent + "/deletetrans?entry_id="
					+ entry_id
		}
	}
</script>
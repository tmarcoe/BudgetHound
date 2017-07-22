<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost/budget" user="donzalma_admin" password="In_heaven3" />
<form id="listcat" action="/user/${parent}/createcategory" method="post">
	<h3>Parent: ${parent}</h3>
	<div class="error">${error}</div>
	<table class="tableview rjsecond tableshadow tableborder">
		<tr>
			<th>Category/Subcategory</th>
			<th>Total</th>
			<th>Level</th>
			<th>Protected</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
		</tr>
		<tr>
			<td colspan="3"><input type="hidden" id="parent" name="parent" value="${parent}" /></td>
			<td colspan="4"><input type="hidden" id="category" name="category" value="${parent}" /></td>
		</tr>
		<c:forEach var="item" items="${objectList.pageList}">
			<tr>
				<td>${item.category}</td>
				<td>${item.amount}</td>
				<td>${item.level}</td>
				<td>${item.protect}</td>
				<td><button type="button" onclick="window.location.href='/user/${item.category}/listcategories'">Open</button></td>
				<td><c:set var="tst" value="${item.protect}" /> <c:if test="${not tst}">
						<button type="button" onclick="remove('${item.category}','${parent}', ${item.id})" >Delete</button>
					</c:if>&nbsp;</td>
						<sql:query var="child" dataSource="${ds}" sql="SELECT * FROM Categories WHERE household_id = ${item.household_id} AND parent = '${item.category}'" />
						<c:choose>
							<c:when test="${child.rowCount > 0}">
								<td><button type="button" onclick="window.location.href='/user/${item.category}/listtrans'">View Transactions</button></td>
							</c:when>
							<c:otherwise>
								<td>&nbsp;</td>
							</c:otherwise>
						</c:choose>
			</tr>
		</c:forEach>
		<tfoot class="tablefooter">
			<tr>
				<td colspan="5"><button type="button" onclick="openPopup()">New Category</button></td>
				<c:choose>
					<c:when test="${parent != 'root'}">
						<td><button type="button" onclick="window.location.href='/user/${parent}/uponelevel'">Up One Level</button></td>
					</c:when>
					<c:otherwise>
						<td>&nbsp;</td>
					</c:otherwise>
				</c:choose>
				<td><button type="button" onclick="window.location.href='/user/${parent}/budgetbreakdown'">Budget Breakdown</button></td>
			</tr>
		</tfoot>
	</table>
	<div class="modal" id="entercategory">
		<div class="modal-content small-modal">
			<table>
				<tr>
					<th>Enter Category</th>
				</tr>
				<tr>
					<td><input id="cat" type="text" /></td>
				</tr>
				<tr>
					<td><button type="button" onclick="newCategory('${parent}')">OK</button></td>
				</tr>
			</table>
		</div>

	</div>
</form>
<script type="text/javascript">
	function openPopup() {
		var modal = document.getElementById('entercategory');
		modal.style.display = "block"
	}
	function remove(name, parent, id) {
		confirm("Do you really want to remove '" + name + "' from the list of categories.");
		window.location.href="/user/" + parent + "/deletecategory?id=" + id;
	}
	function newCategory(p) {
		var cat = document.getElementById('cat').value;
		$("#parent").val(p);
		$("#category").val(cat);
		$("#listcat").submit();
	}
</script>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<form id="listcat" action="/user/${parent}/createcategory" method="post">
	<c:choose>
		<c:when test="${parent == 'root'}">
			<c:set var="pTitle" value="'Top Level'" />
		</c:when>
		<c:otherwise>
			<c:set var="pTitle" value="'Parent is the group category'" />
		</c:otherwise>
	</c:choose>
	<div id="mousefollow">
		<h3 class="north" title="${pTitle}">Parent: ${parent}</h3>
	</div>
	<div class="error">${error}</div>
	<table class="tableview tableshadow tableborder rjfourth cjfifth">
		<tr>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
			<th>Category/Subcategory</th>
			<th>Total</th>
			<th>Protected</th>

		</tr>
		<tr>
			<td colspan="3"><input type="hidden" id="parent" name="parent" value="${parent}" /></td>
			<td colspan="3"><input type="hidden" id="category" name="category" value="${parent}" /></td>
		</tr>
		<c:forEach var="item" items="${objectList.pageList}">
			<tr>
				<td><c:set var="tst" value="${item.protect}" /> <c:if test="${not tst}">
						<button class="north" type="button" onclick="remove('${item.category}','${parent}', ${item.id})" title="Delete this category">Delete</button>
					</c:if>&nbsp;</td>
				<td><button class="north" type="button" onclick="window.location.href='/user/${item.category}/listcategories'"
						title="View/Add Subcategories">Open</button></td>
				<td class="north" title="Category Name">${item.category}</td>
				<td class="north" title="Total amount spent on this category"><fmt:formatNumber type="currency" value="${item.amount}" currencySymbol="" /> 
				<td class="north" title="This category is system generated and cannot be deleted.">${item.protect}</td>
			</tr>
		</c:forEach>
		<tfoot class="tablefooter">
			<tr>
				<c:choose>
					<c:when test="${parent != 'root'}">
						<td colspan="4"><button class="north" type="button" onclick="window.location.href='/user/${parent}/uponecat'"
								title="Go to the previous category">Up One Level</button></td>
					</c:when>
					<c:otherwise>
						<td colspan="4">&nbsp;</td>
					</c:otherwise>
				</c:choose>
				<td><button class="north" type="button" onclick="openPopup()" title="Add a user defined category">New Category</button></td>
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
					<td><button type="button" onclick="closePopup()">Cancel</button></td>
				</tr>
			</table>
		</div>


	</div>
</form>

<script type="text/javascript">

	function openPopup() {
		var modal = document.getElementById('entercategory');
		modal.style.display = "block";
	}
	function closePopup() {
		var modal = document.getElementById('entercategory');
		modal.style.display = "none";
	}
	function remove(name, parent, id) {
		if (confirm("Do you really want to remove '" + name + "' from the list of categories.") == true) {
			window.location.href="/user/" + parent + "/deletecategory?id=" + id;
		}
	}
	function newCategory(p) {
		var cat = document.getElementById('cat').value;
		$("#parent").val(p);
		$("#category").val(cat);
		$("#listcat").submit();
	}
</script>
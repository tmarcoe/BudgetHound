<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		</tr>
		<tr>
			<td colspan="3"><input type="hidden" id="parent" name="parent" value="${parent}" /></td>
			<td colspan="3"><input type="hidden" id="category" name="category" value="${parent}" /></td>
		</tr>
		<c:forEach var="item" items="${catList}">
			<tr>
				<td>${item.category}</td>
				<td>${item.amount}</td>
				<td>${item.level}</td>
				<td>${item.protect}</td>
				<td><button type="button" onclick="window.location.href='/user/${item.category}/listcategories'">View</button></td>
				<td><c:set var="tst" value="${item.protect}" /> <c:if test="${not tst}">
						<button type="button">Delete</button>
					</c:if>&nbsp;</td>
			</tr>
		</c:forEach>
		<tfoot class="tablefooter">
			<tr>
				<td colspan="3"><button type="button" onclick="openPopup()">New Category</button></td>
				<c:choose>
					<c:when test="${parent != 'root'}">
						<td colspan="3"><button type="button" onclick="window.location.href='/user/${parent}/uponelevel'">Up One Level</button></td>
					</c:when>
					<c:otherwise>
						<td colspan="3">&nbsp;</td>
					</c:otherwise>
				</c:choose>
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
	function newCategory(p) {
		var cat = document.getElementById('cat').value;
		$("#parent").val(p);
		$("#category").val(cat);
		$("#listcat").submit();
	}
</script>
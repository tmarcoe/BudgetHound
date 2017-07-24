<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<sf:form id="ful" method="post" modelAttribute="fileUpload" action="/user/root/archive" >
	<table>
		<tr>
			<td><sf:input type="file" name="file" path="file" webkitdirectory="directory" directory="multiple" /></td>
			<td><div class="error"><sf:errors path="file"></sf:errors></div></td>
		</tr>
		<tr>
			<td><button type="button" onclick="upLoad()">Upload File</button></td>
			<td><button type="button" onclick="followLink('/user/root/listtrans')">Cancel</button></td>
		</tr>
	</table>
</sf:form>
<div class="modal" id="popup">
	<div class="spinner">
		<img alt="gif not available" src="<c:url value='/images/spinner.gif'/>"  width="50" height="50">
	</div>
</div>
<script type="text/javascript">
function followLink(link) {
	window.location.href = "${pageContext.request.contextPath}" + link;
}
function upLoad() {
	 document.getElementById("popup").style.display = "block";
	 document.getElementById("ful").submit();
}
</script>
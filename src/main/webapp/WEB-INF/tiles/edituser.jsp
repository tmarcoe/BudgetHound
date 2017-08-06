<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<sf:form id="details" method="post" action="/admin/saveuser" modelAttribute="household" >
<input type="hidden" value="${roleIndex}" id="selectedRoles" />
	<table>
		<tr>
			<th>Name of Household</th>
			<th>Number Of Adults</th>
			<th>Number Of Children</th>
			<th>E-Mail</th>
		</tr>
		<tr>
			<td><sf:input class="north" path="name" title="The name of the household" /></td>
			<td><sf:input class="north" path="num_adults" type="number" title="Number of Adults" /></td>
			<td><sf:input class="north" path="num_children" type="number" title="Number of Children" /></td>
			<td><sf:input class="north" path="username" title="Valid email address" /></td>
		</tr>
		<tr>
			<td><div class="error">
					<sf:errors path="name" />
				</div></td>
			<td><div class="error">
					<sf:errors path="num_adults" />
				</div></td>
			<td><div class="error">
					<sf:errors path="num_children" />
				</div></td>
			<td><div class="error">
					<sf:errors path="username" />
				</div></td>
		</tr>
		<tr>
			<th>Community</th>
			<th>City</th>
			<th>Region</th>
			<th>Country</th>
		</tr>
		<tr>
			<td><sf:input class="north" path="community" title="This is for future expansion (no need to fill it out now)"/></td>
			<td><sf:input class="north" path="city" title="This is for future expansion (no need to fill it out now)"/></td>
			<td><sf:input class="north" path="region" title="This is for future expansion (no need to fill it out now)"/></td>
			<td><sf:input class="north" path="country" title="This is for future expansion (no need to fill it out now)"/></td>
		</tr>
		<tr>
			<td><div class="error">
					<sf:errors path="community" />
				</div></td>
			<td><div class="error">
					<sf:errors path="city" />
				</div></td>
			<td><div class="error">
					<sf:errors path="region" />
				</div></td>
			<td><div class="error">
					<sf:errors path="country" />
				</div></td>
		</tr>
		<tr>
			<td><sf:select class="north" path="roles" id="roles" multiselect="true" title="Assign security roles" >
					<sf:options items="${roles}" itemValue="id" itemLabel="role" />
				</sf:select></td>
			<td>Enabled:<sf:checkbox class="north" path="enabled" title="User enabled?"/></td>
		</tr>
		<tr>
			<td><sf:hidden path="roleString" id="roleString" /></td>
			<td><sf:hidden path="password"/></td>
			<td><sf:hidden path="start_balance"/></td>
			<td><sf:hidden path="household_id"/></td>
			<td><sf:hidden path="enabled"/></td>
			<!--  <td><sf:hidden path="roles"/></td>-->
		</tr>
		<tr>
			<td><button type="button" onclick="formSubmit()">Save</button></td>
			<td><button type="button" onclick="window.history.back();">Cancel</button></td>
		</tr>
	</table>
</sf:form>
<script type="text/javascript">
$( document ).ready(function() {
	var ndx = $("#selectedRoles").val();
	var selectedOptions = ndx.split(",");
	for(var i in selectedOptions) {
		 var optionVal = selectedOptions[i];
		$("#roles").find("option[value="+optionVal+"]").prop("selected", "selected");
	}
});
function formSubmit() {

	var opt = document.getElementById("roles");
	var userRoles = "";
	for (var i=0; i < opt.options.length; i++) {
		if (opt.options[i].selected) {
			if (userRoles == "") {
				userRoles += opt.options[i].text;
			}else{
				userRoles += ";" + opt.options[i].text;
			}
		}
	}
	var rs = document.getElementById("roleString");
	rs.value = userRoles;
	document.getElementById("details").submit();
}

</script>
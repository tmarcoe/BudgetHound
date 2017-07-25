<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<sf:form method="post" action="/public/createhousehold" commandName="household">
	<table>
		<tr>
			<th>Name of Household</th>
			<th>Number Of Adults</th>
			<th>Number Of Children</th>
			<th>E-Mail</th>
		</tr>
		<tr>
			<td><sf:input path="name" /></td>
			<td><sf:input path="num_adults" type="number" /></td>
			<td><sf:input path="num_children" type="number" /></td>
			<td><sf:input path="username" /></td>
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
			<td><sf:input path="community" /></td>
			<td><sf:input path="city" /></td>
			<td><sf:input path="region" /></td>
			<td><sf:input path="country" /></td>
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
			<td colspan="4"><hr>
			<td>
		</tr>
		<tr>
			<th>Password</th>
			<td><sf:input path="password" type="password" id="password" class="control" name="password" /></td>
			<th>Confirm</th>
			<td><input id="confirmpass" class="control" name="confirmpass" type="password"></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
				<div id="pbar">
					<label id="pLabel"></label>
					<div id="pStrength"></div>
				</div> &nbsp;
			</td>
			<td>&nbsp;</td>
			<td><div id="matchpass"></div></td>
		</tr>
		<tr>
			<td><div class="error">
					<sf:errors path="password" />
				</div></td>
		</tr>
		<tr>
			<td><sf:button type="submit">Save</sf:button></td>
		</tr>
	</table>
</sf:form>
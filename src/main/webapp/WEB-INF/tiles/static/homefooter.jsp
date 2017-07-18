<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sec" uri="/WEB-INF/tld/security.tld"%>
<sec:isNotAuthenticated>
	<table class="centerHorizontally">
		<tr>
			<td><a href="/public/signup" class="largeButton"> <span>Get Started</span></a></td>
			<td><a href="/login" class="largeButton"> <span>Sign In</span></a></td>
		</tr>
	</table>
</sec:isNotAuthenticated>
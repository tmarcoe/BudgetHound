<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="/WEB-INF/tld/security.tld"%>
<div class="center">
	<img alt="Budget Hound" src="<c:url value = '/images/budgethound.png'/>">
	<sec:isNotAuthenticated>
	<table class="centerHorizontally">
		<tr>
			<td><a href="/public/signup" class="largeButton"> <span>Get Started</span></a></td>
			<td><a href="/login" class="largeButton"> <span>Sign In</span></a></td>
		</tr>
	</table>
</sec:isNotAuthenticated>
</div>
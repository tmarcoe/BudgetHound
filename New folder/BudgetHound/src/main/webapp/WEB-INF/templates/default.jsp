<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<sql:setDataSource var="ds" dataSource="jdbc/Budget" />
<html>
<head>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<script src="/script/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="/script/Chart.js" type="text/javascript" ></script>
<title><tiles:getAsString name="title" /></title>
</head>
<body>
	<div class="toolbar">
		<tiles:insertAttribute name="toolbar"></tiles:insertAttribute>
	</div>
	<!-- Body -->
	<div class="content">
		<div class="heading">
			<tiles:insertAttribute name="heading_title"></tiles:insertAttribute>
		</div>
		<tiles:insertAttribute name="content" />
	</div>
	<tiles:insertAttribute name="footer"></tiles:insertAttribute>
</body>
</html>
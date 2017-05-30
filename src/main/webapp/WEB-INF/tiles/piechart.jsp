<div class="rightside lightBackground shadow">
	<table class="tableBorder dark-text">
		<caption>Summary</caption>
		<tr>
			<th>Total Income</th>
			<th>Total Expense</th>
			<th>Money Left Over</th>
		</tr>
		<tr>
			<td>${totalDeposits}</td>
			<td>${totalWithdrawals}</td>
			<td>${totalDeposits - totalWithdrawals}</td>
		</tr>
	</table>
</div>

<div class="pieChart">
	<canvas class="reportCharts  divshadow" id="myChart"></canvas>
</div>
<table class="buttonTable">
	<tr>
		<td><button type="button" onclick="followLink('/home')">OK</button></td>
		<td><button type="button" onclick="renderCanvas()">View/Save Image</button></td>
	</tr>
</table>

<script>
	var ctx = document.getElementById("myChart");
	var context = "${pageContext.request.contextPath}";
	var mnth = "${month}";
	var yr = "${year}";
	$(document).ready(function() {
		$.getJSON(context + "/data-service/budget?month=" + mnth + "&year=" + yr , function(data) {
			var myChart = new Chart(ctx, data);
		});
	});

	function followLink(link) {
		window.location.href = "${pageContext.request.contextPath}" + link;
	}
	function renderCanvas() {
		var canvas = document.getElementById("myChart");
		var img = canvas.toDataURL("image/png");

		document.write('<img src="'+img+'"/>');
	}
</script>


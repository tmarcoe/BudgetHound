
<div class="pieChart">
	<canvas class="reportCharts  divshadow" id="myChart"></canvas>
</div>
<table class="buttonTable">
	<tr>
		<td><button type="button" onclick="followLink('/user/${parent}/listtrans')">OK</button></td>
		<td><button type="button" onclick="renderCanvas()">View/Save Image</button></td>
	</tr>
</table>

<script>
	var ctx = document.getElementById("myChart");
	var context = "${pageContext.request.contextPath}";
	var par = "${parent}";
	$(document).ready(function() {
		$.getJSON(context + "/data-service/"+ par +"/budget", function(data) {
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


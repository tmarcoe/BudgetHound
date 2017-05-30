<form>
	<table>
		<tr>
			<th>Month</th>
			<th>Year</th>
		</tr>
		<tr>
			<td><select id="getMonth">
					<option value="1">January</option>
					<option value="2">February</option>
					<option value="3">March</option>
					<option value="4">April</option>
					<option value="5">May</option>
					<option value="6">June</option>
					<option value="7">July</option>
					<option value="8">August</option>
					<option value="9">September</option>
					<option value="10">October</option>
					<option value="11">November</option>
					<option value="12">December</option>
			</select></td>
			<td><select id="getYear">
					<option value="2017">2017</option>
					<option value="2018">2018</option>
					<option value="2019">2019</option>
					<option value="2020">2020</option>
					<option value="2021">2021</option>
					<option value="2022">2022</option>
					<option value="2023">2023</option>
					<option value="2024">2024</option>
					<option value="2025">2025</option>
					<option value="2026">2026</option>
					<option value="2027">2027</option>
			</select></td>
		</tr>
		<tr><td><button type="button" onclick="submitTime()">Get Period</button></td></tr>
	</table>
</form>
<script type="text/javascript">
	var context = '${pageContext.request.contextPath}/piechart?month=';
	function submitTime() {
		
		var mctx = document.getElementById("getMonth");
		var yctx = document.getElementById("getYear");
		var month = mctx.options[mctx.selectedIndex].value;
		var year = yctx.options[yctx.selectedIndex].value;
		window.location.href = context + month + "&year=" + year;
	}
</script>
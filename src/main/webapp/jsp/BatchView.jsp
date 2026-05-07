<%@page import="in.com.practice.bean.BatchBean"%>

<%
String smsg = (String) request.getAttribute("successMsg");
String emsg = (String) request.getAttribute("errorMsg");
BatchBean bean = (BatchBean) request.getAttribute("bean");
%>

<html>
<body>

	<%@ include file="Header.jsp"%>

	<div align="center">

		<h1><%=(bean != null && bean.getBatchId() != null && bean.getBatchId() > 0) ? "Update Batch" : "Add Batch"%></h1>

		<h3 style="color: green;"><%=smsg != null ? smsg : ""%></h3>
		<h3 style="color: red;"><%=emsg != null ? emsg : ""%></h3>

		<form action="BatchCtl" method="post">

			<input type="hidden" name="id"
				value="<%=(bean != null) ? bean.getBatchId() : ""%>">

			<table>

				<tr>
					<th>Batch Code</th>
					<td><input type="text" name="batchCode" placeholder="enter code"
						value="<%=(bean != null) ? bean.getBatchCode() : ""%>"></td>
				</tr>

				<tr>
					<th>Total Messages</th>
					<td><input type="number" name="totalMessages" placeholder="enter msg"
						value="<%=(bean != null) ? bean.getTotalMessages() : ""%>"></td>
				</tr>

				<tr>
					<th>Processed Count</th>
					<td><input type="number" name="processedCount" placeholder="enter count"
						value="<%=(bean != null) ? bean.getProcessedCount() : ""%>"></td>
				</tr>

				<tr>
					<th>Status</th>
					<td><input type="text" name="status"  placeholder="enter status"
						value="<%=(bean != null) ? bean.getStatus() : ""%>"></td>
				</tr>

				<tr>
					<td></td>
					<td><input type="submit" name="operation"
						value="<%=(bean != null && bean.getBatchId() != null && bean.getBatchId() > 0) ? "update" : "save"%>">
					</td>
				</tr>

			</table>

		</form>

	</div>

</body>
</html>
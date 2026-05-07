<%@page import="java.util.*"%>
<%@page import="in.com.practice.bean.BatchBean"%>

<%
List<BatchBean> list = (List<BatchBean>) request.getAttribute("list");
int pageNo = (Integer) request.getAttribute("pageNo");
int pageSize = (Integer) request.getAttribute("pageSize");
int nextListSize = (Integer) request.getAttribute("nextListSize");

int index = ((pageNo - 1) * pageSize) + 1;
%>

<html>
<body>

	<%@ include file="Header.jsp"%>

	<h1 align="center">Batch List</h1>

	<form action="BatchListCtl" method="post">

		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

		<table border="1" width="100%">

			<tr>
				<th>Select</th>
				<th>S.No</th>
				<th>Batch Code</th>
				<th>Total</th>
				<th>Processed</th>
				<th>Status</th>
				<th>Edit</th>
			</tr>

			<%
			if (list != null) {
				for (BatchBean bean : list) {
			%>

			<tr align="center">
				<td><input type="checkbox" name="ids"
					value="<%=bean.getBatchId()%>"></td>
				<td><%=index++%></td>
				<td><%=bean.getBatchCode()%></td>
				<td><%=bean.getTotalMessages()%></td>
				<td><%=bean.getProcessedCount()%></td>
				<td><%=bean.getStatus()%></td>
				<td><a href="BatchCtl?id=<%=bean.getBatchId()%>">Edit</a></td>
			</tr>

			<%
			}
			}
			%>

		</table>


		<br>

		<table width="100%">
			<tr>

				<td align="left"><input type="submit" name="operation"
					value="previous" <%=(pageNo > 1) ? "" : "disabled"%>></td>

				<td align="center"><input type="submit" name="operation"
					value="delete"></td>

				<td align="right"><input type="submit" name="operation"
					value="next" <%=(nextListSize == pageSize) ? "" : "disabled"%>>
				</td>

			</tr>
		</table>

	</form>

</body>
</html>
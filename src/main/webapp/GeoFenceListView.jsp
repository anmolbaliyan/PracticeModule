<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.com.practice.bean.GeoFenceBean"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>GeoFence List</title>
</head>
<body>

	<%
List<GeoFenceBean> list = (List<GeoFenceBean>) request.getAttribute("list");
String smsg = (String) request.getAttribute("successMsg");
String emsg = (String) request.getAttribute("errorMsg");

int pageNo = request.getAttribute("pageNo") != null ? (Integer) request.getAttribute("pageNo") : 1;
int pageSize = request.getAttribute("pageSize") != null ? (Integer) request.getAttribute("pageSize") : 5;
int nextListSize = request.getAttribute("nextListSize") != null ? (Integer) request.getAttribute("nextListSize") : 0;

int index = ((pageNo - 1) * pageSize) + 1;
%>

	<%@ include file="Header.jsp"%>

	<div align="center">

		<h1>GeoFence List</h1>

		<h3 style="color: green;"><%=smsg != null ? smsg : ""%></h3>
		<h3 style="color: red;"><%=emsg != null ? emsg : ""%></h3>

		<form action="GeoFenceListCtl" method="post">

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<!-- SEARCH -->
			<table>
				<tr>
					<th>Code</th>
					<td><input type="text" name="geoFenceCode"></td>

					<th>Location</th>
					<td><input type="text" name="locationName"></td>

					<th>Status</th>
					<td><input type="text" name="status"></td>

					<td><input type="submit" name="operation" value="search"></td>
				</tr>
			</table>

			<br>

			<!-- LIST -->
			<table border="1" width="100%">

				<tr style="background-color: skyblue">
					<th>Select</th>
					<th>S.No</th>
					<th>Code</th>
					<th>Location</th>
					<th>Radius</th>
					<th>Status</th>
					<th>Edit</th>
				</tr>

				<%
				if (list != null) {
					Iterator<GeoFenceBean> it = list.iterator();

					while (it.hasNext()) {
						GeoFenceBean bean = it.next();
				%>

				<tr align="center">
					<td><input type="checkbox" name="ids"
						value="<%=bean.getGeoFenceId()%>"></td>

					<td><%=index++%></td>
					<td><%=bean.getGeoFenceCode()%></td>
					<td><%=bean.getLocationName()%></td>
					<td><%=bean.getRadius()%></td>
					<td><%=bean.getStatus()%></td>

					<td><a href="GeoFenceCtl?id=<%=bean.getGeoFenceId()%>">Edit</a>
					</td>
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

	</div>

</body>
</html>
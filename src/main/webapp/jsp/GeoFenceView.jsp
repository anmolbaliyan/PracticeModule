<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="in.com.practice.bean.GeoFenceBean" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>GeoFence</title>
</head>
<body>

	<%@ include file="Header.jsp"%>

	<%
	String smsg = (String) request.getAttribute("successMsg");
	String emsg = (String) request.getAttribute("errorMsg");
	GeoFenceBean bean = (GeoFenceBean) request.getAttribute("bean");
	%>

	<div align="center">

		<h1><%= (bean != null && bean.getGeoFenceId() > 0) ? "Update GeoFence" : "Add GeoFence" %></h1>

		<h3 style="color: green;"><%=smsg != null ? smsg : ""%></h3>
		<h3 style="color: red;"><%=emsg != null ? emsg : ""%></h3>

		<form action="GeoFenceCtl" method="post">

			<input type="hidden" name="id"
				value="<%= (bean != null) ? bean.getGeoFenceId() : "" %>">

			<table>

				<tr>
					<th>GeoFence Code</th>
					<td>
						<input type="text" name="geoFenceCode"
							value="<%= (bean != null) ? bean.getGeoFenceCode() : "" %>"
							placeholder="Enter GeoFence Code">
					</td>
				</tr>

				<tr>
					<th>Location Name</th>
					<td>
						<input type="text" name="locationName"
							value="<%= (bean != null) ? bean.getLocationName() : "" %>"
							placeholder="Enter Location Name">
					</td>
				</tr>

				<tr>
					<th>Radius</th>
					<td>
						<input type="number" name="radius"
							value="<%= (bean != null) ? bean.getRadius() : "" %>"
							placeholder="Enter Radius">
					</td>
				</tr>

				<tr>
					<th>Status</th>
					<td>
						<input type="text" name="status"
							value="<%= (bean != null) ? bean.getStatus() : "" %>"
							placeholder="Enter Status (ACTIVE/INACTIVE)">
					</td>
				</tr>

				<tr>
					<th></th>
					<td>
						<input type="submit" name="operation"
							value="<%= (bean != null && bean.getGeoFenceId() > 0) ? "update" : "save" %>">
					</td>
				</tr>

			</table>

		</form>

	</div>


</body>
</html>
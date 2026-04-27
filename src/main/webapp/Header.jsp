<%@page import="in.com.practice.bean.GeoFenceBean"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	GeoFenceBean geofenceBean = (GeoFenceBean) session.getAttribute("user");
	%>

	<%-- <%
	if (geofenceBean != null) {
	%>
	<h3><%="Hii, " + geofenceBean.getGeoFenceCode()%></h3>
	<a href="GeoFenceCtl">Add GeoFence</a> |
	<a href="GeoFenceListCtl">GeoFence list</a> |
	<a href="LoginCtl?operation=logout">Logout</a> 
	<%
	} else {
	%> --%>

	<h3>Hi, Guest</h3>
	<a href="LoginCtl">Login</a> |
	<a href="UserRegistrationCtl">SignUp</a> |
	<a href="GeoFenceCtl">Add GeoFence</a> |
	<a href="GeoFenceListCtl">GeoFence list</a> |
	<a href="WelcomeCtl">Welcome</a>
	<hr>
</body>
</html>
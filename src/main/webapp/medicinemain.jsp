<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.narola.pharmacy.medicine.model.MedicineBean"%>
<%@ page import="com.narola.pharmacy.utility.Constant"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="java.util.ArrayList"%>
<%@page import="java.io.File"%>
<%@ page import="java.sql.Blob"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>All Medicines</title>
<script>
function handleChange(checkbox,medId) {
    if(checkbox.checked == true){
       load_ajax(medId,"true");
    }else{
    	load_ajax(medId,"false");
   }
}
function load_ajax(medId,action)
{
	
	var xhttp;
    xhttp = new XMLHttpRequest();
    
    xhttp.open("POST", "ManagePopularMedicineAction?action="+action+"&medId="+medId, true);
    xhttp.send(); 
    
}
</script>
<link rel="stylesheet" href="resources/css/commonLayout.css">
</head>
<body>
	<jsp:include page="sidebar.jsp"></jsp:include>

	<div class="mainArea">
		<div class="headingArea">
			<h1>Medicines</h1>
		</div>
		<div>
			<a href="${pageContext.request.contextPath}${Constant.URL_MEDICINE_ADD_FORM}">Add Medicine</a>
		</div>

		<table class="table table-borderless">
			<tr>
				<th>Picture</th>
				<th>Medicine ID</th>
				<th>Medicine Name</th>
				<th>Medicine Price</th>
				<th>Popular</th>
				<th>Edit</th>
				<th>Delete</th>
				<th>View</th>
			</tr>
			<c:forEach var="mbean" items="${MedicineList}">
				<tr>
					<c:set var="folder" scope="request" value="${mbean.getMedId()}" />

					<td><img class="imgDiv" src="${mbean.getImagesPath().get(0)}"
						width="100" height="100"></td>

					<td>${mbean.getMedId()}</td>
					<td>${mbean.getMedName()}</td>
					<td>${mbean.getMedPrice()}</td>
					<c:if test="${mbean.getPopular()=='true'}">
						<c:set var="popularStatus" value="checked"></c:set>
					</c:if>
					<c:if test="${mbean.getPopular()=='false'}">
						<c:set var="popularStatus" value=""></c:set>
					</c:if>
					<td><input type="checkbox" ${popularStatus}
						onchange='handleChange(this,${mbean.getMedId()});'></td>
					<td><a href="${pageContext.request.contextPath}${Constant.URL_MEDICINE_UPDATE_FORM}?medId=${mbean.getMedId()}"><span
							class="glyphicon glyphicon-pencil"></span></a></td>
					<td><a href="${pageContext.request.contextPath}${Constant.URL_MEDICINE_DELETE_ACTION}?medId=${mbean.getMedId()}"><span
							class="glyphicon glyphicon-trash Delete"></span></a></td>
					<td><a href="${pageContext.request.contextPath}${Constant.URL_MEDICINE_SINGLE_VIEW}?medId=${mbean.getMedId()}"><span
							class="glyphicon glyphicon-eye-open"></span></a></td>
				</tr>

			</c:forEach>

		</table>
	</div>
</body>
</html>
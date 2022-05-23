
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.io.File"%>
<%@ page import="java.sql.Blob"%>
<%@ page import="com.narola.pharmacy.test.model.TestBean"%>
<%@ page import="java.util.Base64"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>All Tests</title>
<script>
function handleChange(checkbox,testId) {
    if(checkbox.checked == true){
       load_ajax(testId,"true");
    }else{
    	load_ajax(testId,"false");
   }
}
function load_ajax(testId,action)
{
	
	var xhttp;
    xhttp = new XMLHttpRequest();
    
    xhttp.open("POST", "ManagePopularTestAction?action="+action+"&testId="+testId, true);
    xhttp.send(); 
    
}
</script>
<style>
img {
	height: 100px;
	width: 100px;
}
</style>
<link rel="stylesheet" href="resources/css/commonLayout.css">
</head>
<jsp:include page="sidebar.jsp"></jsp:include>
<body>
	<div class="mainArea">
		<div class="headingArea">
			<h1>Tests</h1>
		</div>
		<div>
			<a href="AddTestForm" class="operations">Add Test</a>
		</div>
		<table class="table table-borderless">
			<tr>
				<th>Picture</th>
				<th>Test ID</th>
				<th>Test Name</th>
				<th>Test Price</th>
				<th>Popular</th>
				<th>Edit</th>
				<th>Delete</th>
				<th>View</th>
			</tr>
			<c:forEach var="tbean" items="${TestList}">

				<tr>

					<td><img
						src="data:image/png;base64,${tbean.getBase64String()}"></td>
					<td>${tbean.getTestId().intValue()}</td>
					<td>${tbean.getTestName()}</td>
					<td>${tbean.getTestPrice()}</td>
					<c:if test="${tbean.getPopular()=='true'}">
						<c:set var="popularStatus" value="checked"></c:set>
					</c:if>
					<c:if test="${tbean.getPopular()=='false'}">
						<c:set var="popularStatus" value=""></c:set>
					</c:if>
					<td><input type="checkbox" ${popularStatus}
						onchange='handleChange(this,${tbean.getTestId()});'></td>
					<td><a class="operations"
						href="UpdateTestForm?testId=${tbean.getTestId()}"><span
							class="glyphicon glyphicon-pencil"></span></a></td>
					<td><a class="operations"
						href="RemoveTestAction?testId=${tbean.getTestId()}"><span
							class="glyphicon glyphicon-trash Delete"></span> </a></td>
					<td><a class="operations"
						href="ViewTestForm?testId=${tbean.getTestId()}"><span
							class="glyphicon glyphicon-eye-open"></span></a></td>
				</tr>

			</c:forEach>
			<c:if test="${not empty errMsg}">
				<h4 style="color: red">${errMsg}</h4>
			</c:if>
		</table>

	</div>
</body>
</html>
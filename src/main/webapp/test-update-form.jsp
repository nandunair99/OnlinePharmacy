<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.narola.pharmacy.test.model.TestBean"%>
<%@ page import="java.util.Base64"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Test Update Form</title>
<style>
input[type=submit] {
	background-color: #f44336;
	border: none;
	color: white;
	padding: 20px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 2px;
	cursor: pointer;
	border-radius: 8px;
}
</style>
<script
	src="https://cdn.ckeditor.com/ckeditor5/12.4.0/classic/ckeditor.js"></script>
<link rel="stylesheet" href="resources/css/commonLayout.css">
</head>
<body>
	<jsp:include page="sidebar.jsp"></jsp:include>
	<div class="mainArea">
		<div class="headingArea">
			<h1>Update Tests</h1>
		</div>

		<c:if test="${not empty TestBean}">
			<c:set var="tb" scope="request" value="${TestBean}" />
		</c:if>
		<c:set var="testId" scope="request" value="${tb.getTestId()}" />

		<div>
			<form method="post"
				action="${pageContext.request.contextPath}/UpdateTestAction?testId=${tb.getTestId()}"
				enctype="multipart/form-data">
				<table>
					<tr>
						<td>
							<div class="form-group">
								<label for="testIdtxt">Test ID:</label> <input type="text"
									class="form-control" name="testIdtxt" id="testIdtxt"
									value="${tb.getTestId()}" disabled>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="form-group">
								<label for="testNametxt">Test Name:</label> <input type="text"
									class="form-control" placeholder="Enter test name..."
									id="testNametxt" name="testNametxt" value="${tb.getTestName()}">
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="form-group">
								<label for="testPricetxt">Price:</label>

								<c:if test="${tb.getTestPrice()==0.0}">
									<c:set var="price" scope="page" value="" />
								</c:if>
								<c:if test="${tb.getTestPrice()!=0.0}">
									<c:set var="price" scope="page" value="${tb.getTestPrice()}" />
								</c:if>

								<input type="text" class="form-control" id="testPricetxt"
									name="testPricetxt" value="${price}">
							</div>
						</td>
					</tr>

					<tr>
						<td>
							<div class="form-group">
								<label for="testDiscounttxt">Discount:</label>

								<c:if test="${tb.getTestDiscount()==-1.0}">
									<c:set var="discount" scope="page" value="" />
								</c:if>
								<c:if test="${tb.getTestDiscount()!=-1.0}">
									<c:set var="discount" scope="page"
										value="${tb.getTestDiscount()}" />
								</c:if>

								<input type="text" class="form-control" id="testDiscounttxt"
									name="testDiscounttxt" value="${discount}">
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="form-group">
								<label for="testDesctxt">Test Description:</label>
								<textarea name="testDesctxt" id="testDesctxt">${tb.getTestDescription()}</textarea>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="form-group">
								<label for="testPreparationtxt">Test Preparation:</label>
								<textarea name="testPreparationtxt" id="testPreparationtxt">${tb.getTestPreparation()}</textarea>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="form-group">
								<label for="picturetxt">Upload Test Image:</label> <input
									type="file" class="form-control" name="picturetxt"
									id="picturetxt">
							</div>
						</td>
					</tr>
					
					<c:if test="${not empty tb}">
						<c:if test="${not empty tb.getPicStream()}">
							
							<c:set var="encode" value="${tb.getBase64String()}" />
						</c:if>
						<c:if test="${empty tb.getPicStream()}">
							<c:set var="encode" value="${imageBase64}" />
						</c:if>
						<tr>
							<td colspan=2><div class="imageContainer">
									<img src="data:image/png;base64,${encode}" height="100"
										width="100">
								</div> <input type="text" name="imageBase64" value="${encode}"
								hidden="true"></td>
						</tr>
					</c:if>
					<tr>
						<td colspan=2><c:if test="${not empty errMsg}">
								
								<h4 style="color: red">${errMsg}</h4>
							</c:if></td>
					</tr>
					<tr>
						<td colspan=2><input type="submit" value="Update Test">
						</td>
					</tr>
				</table>
			</form>
			<p>
				<a href="ShowAllTest">Go Back</a>
			</p>
		</div>

	</div>
	<script>
        ClassicEditor
            .create( document.querySelector( '#testDesctxt' ) )
            .catch( error => {
                console.error( error );
            } );
        
        ClassicEditor
        .create( document.querySelector( '#testPreparationtxt' ) )
        .catch( error => {
            console.error( error );
        } );
    </script>
</body>
</html>
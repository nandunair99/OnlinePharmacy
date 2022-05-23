<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.narola.pharmacy.test.model.TestBean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.narola.pharmacy.utility.Constant"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Test</title>
<link rel="stylesheet" href="resources/css/commonLayout.css">
<script
	src="https://cdn.ckeditor.com/ckeditor5/12.4.0/classic/ckeditor.js"></script>
</head>
<body>
	<c:import var="sidebar" url="sidebar.jsp" />
	${sidebar}
	<div class="mainArea">
		<div class="headingArea">
			<h1>Add Test</h1>
		</div>

		<c:if test="${not empty TestBean}">
			<c:set var="tb" value="${TestBean}" />
		</c:if>

		<div>
			<form method="post"
				action="${pageContext.request.contextPath}${Constant.URL_TEST_ADD_ACTION}"
				enctype="multipart/form-data">
				<table>
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
								<c:if test="${tb.getTestPrice()==-1.0}">
									<c:set var="price" value="" />
								</c:if>
								<c:if test="${tb.getTestPrice()!=-1.0}">
									<c:set var="price" value="${tb.getTestPrice()}" />
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
									<c:set var="discount" value="" />
								</c:if>
								<c:if test="${tb.getTestDiscount()!=-1.0}">
									<c:set var="discount" value="${tb.getTestDiscount()}" />
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
					<tr>
						<td colspan=2><c:if test="${not empty errMsg}">
								<h4 style="color: red">${errMsg}</h4>
							</c:if></td>
					</tr>
					<tr>
						<td colspan=2><input type="submit" value="Add Test">
						</td>
					</tr>
				</table>
			</form>
			<p>
				<a href="ShowAllTest">Go back</a>
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
<%@ page import="com.narola.pharmacy.category.CategoryDAO"%>
<%@ page import="com.narola.pharmacy.category.CategoryBean"%>
<%@ page import="com.narola.pharmacy.test.model.TestBean"%>
<%@ page import="java.io.File"%>
<%@ page import="java.util.Base64"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>View Single Test</title>

<style type=text/css>
.imageMainContainer {
	width: 300px;
	height: 300px;
	display: inline-block;
}

.imageContainer {
	border: 2px solid black;
	min-width: 10px;
	min-height: 10px;
	display: inline-block;
}
/* Control the left side */
.left {
	float: left;
	width: 50%;
	height: 100%;
}

/* Control the right side */
.right {
	width: 50%;
	height: 100%;
	float: left;
}

.headerDiv {
	height: 50px;
	width: 100%;
}

table {
	float: left
}

img {
	left: 100;
	margin-right: 100px;
	float: right;
	margin-top: 20px;
	height: 100px;
	width: 100px;
}
</style>
<link rel="stylesheet" href="resources/css/commonLayout.css">
</head>
<body>
	<jsp:include page="sidebar.jsp"></jsp:include>
	<div class="mainArea">
		<div class="headingArea">
			<h1>View Test</h1>
		</div>

		<div class="site-section">
			<div class="container">
				<div class="row">
					<div class="col-md-5 mr-auto">
						<div class="border text-center">
							<div style="text-align: center">
								<br>
								<div class="imageMainContainer"
									style="left: 0; margin-left: 70px">
									<img class="imgDiv" src="data:image/png;base64,${TestBean.getBase64String()}"
										style="height: 300px; width: 300px;">
								</div>

							</div>
						</div>
					</div>
					<div class="col-md-6">
						<h2 class="text-black">${TestBean.getTestName()}</h2>
						<p style="color: gray">Price:</p>
						<p>
							<del>
								<i style="font-size: 24px; color: black" class="fa fa-rupee">&#8377;</i>
								<c:set var="price" scope="request"
									value="${TestBean.getTestPrice()}" />
								<c:out value="${price+(price*40/100)}" />
							</del>
							<strong class="text-primary h1"> <i
								style="font-size: 24px; color: black" class="fa fa-rupee">&#8377;</i>${TestBean.getTestPrice()}</strong>
						</p>
						<p style="color: gray">Preparation</p>
						<p>${TestBean.getTestPreparation()}</p>
						<p style="color: gray">Description</p>
						<p>${TestBean.getTestDescription()}</p>
						<p>
							<a href="ShowAllTest">Go Back</a>
						</p>

					</div>
				</div>
			</div>
		</div>


	</div>
</body>
</html>
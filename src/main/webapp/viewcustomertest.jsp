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
<title>View Test</title>

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

.headerDiv {
	height: 50px;
	width: 100%;
}

a.returnbtn:link, a.returnbtn:visited {
	background-color: transparent;
	color: black;
	border: 2px solid #33B2FF;
	padding: 10px 20px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
}

a.returnbtn:hover, a.returnbtn:active {
	background-color: #33B2FF;
	color: white;
}

#orderbtn {
	background-color: transparent;
	color: black;
	border: 2px solid #33B2FF;
	padding: 10px 20px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
}

#orderbtn:hover{
	background-color: #33B2FF;
	color: white;
}
</style>
<link rel="stylesheet" href="resources/css/userSideLayout.css">
</head>
<body>
	<c:if test="${not empty ShowOrderSuccess}">
		<c:import var="orderSuccess" url="orderplaced.jsp" />
${orderSuccess}

</c:if>


	<jsp:include page="navbar.jsp"></jsp:include>
	<div class="mainArea">
		<form method="post" action="ShowSummaryForm">
			<div class="site-section">
				<div class="container">
					<div class="row">
						<div class="col-md-5 mr-auto">
							<div class="headingArea" style="text-align: center">
								<h1>View Test</h1>
							</div>
							<div class="text-center">
								<div style="text-align: center">

									<br>

									<div class="imageMainContainer"
										style="left: 0; margin-left: 70px">
										<input type="text" value="${TestBean.getTestId()}" name="testIdtxt" hidden="true">
										<img class="imgDiv"
											src="data:image/png;base64,${TestBean.getBase64String()}"
											style="height: 300px; width: 300px;">
									</div>

								</div>
							</div>
						</div>
						<div class="col-md-6">
							<h2 class="text-black">${TestBean.getTestName()}</h2>
							<p style="color: gray">Price:</p>
							<p>
								<del> ${TestBean.getTestPrice()} </del>
								<strong class="text-primary h1"> <i
									style="font-size: 24px; color: black" class="fa fa-rupee">&#8377;</i>${TestBean.getTestDiscountedPrice()}</strong>
							</p>
							<p style="color: gray">Preparation</p>
							<p>${TestBean.getTestPreparation()}</p>
							<p style="color: gray">Description</p>
							<p>${TestBean.getTestDescription()}</p>
							<p>
								<a class="returnbtn"
									href="AddCartAction?testId=${TestBean.getTestId()}">Add to
									Cart</a><input type="submit" value="Order Now" name="orderbtn" id="orderbtn"><a
									class="returnbtn" href="ShowCustomerHome">Go Back</a>
							</p>
							<c:if test="${not empty message}">
								<h4 style="color: green">${message}</h4>
							</c:if>
							<c:if test="${not empty errMsg}">
								<h4 style="color: red">${errMsg}</h4>
							</c:if>

						</div>
					</div>
				</div>
			</div>

		</form>
	</div>
</body>
</html>
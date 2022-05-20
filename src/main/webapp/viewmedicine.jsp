<%@ page import="com.narola.pharmacy.category.CategoryDAO"%>
<%@ page import="com.narola.pharmacy.category.CategoryBean"%>
<%@ page import="com.narola.pharmacy.medicine.model.MedicineBean"%>
<%@ page import="com.narola.pharmacy.utility.Constant"%>
<%@ page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link
	href="https://fonts.googleapis.com/css?family=Rubik:400,700|Crimson+Text:400,400i"
	rel="stylesheet">
<link rel="stylesheet" href="resources/fonts/icomoon/style.css">


<link rel="stylesheet" href="resources/css/magnific-popup.css">
<link rel="stylesheet" href="resources/css/jquery-ui.css">
<link rel="stylesheet" href="resources/css/owl.carousel.min.css">
<link rel="stylesheet" href="resources/css/owl.theme.default.min.css">
<link rel="stylesheet" href="resources/css/commonLayout.css">

<link rel="stylesheet" href="resources/css/aos.css">

<link rel="stylesheet" href="resources/css/style.css">
<meta charset="ISO-8859-1">
<title>View Single Medicine</title>
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script>
function hideAllImages()
{
	
	var x = document.getElementsByClassName("imageMainContainer");
	var i;
	for (i = 0; i < x.length; i++) {
	    x[i].style.display = 'none';
	}x[0].style.display = 'block';
}
function showImage(i)
{
	var x = document.getElementsByClassName("imageMainContainer");
	for (j = 0; j < x.length; j++) {
		if(i==j)
	    x[j].style.display = 'block';
		else
			x[j].style.display = 'none';
	}
	
}
function createBorder(el)
{
	$(".imageContainer").removeClass("addBorder");
	$(el).addClass("addBorder");
}
</script>
<style type=text/css>
.imageMainContainer {
	width: 300px;
	height: 300px;
	display: inline-block;
}

.imageMainContainer :hover {
	width: 300px;
	height: 300px;
	display: inline-block;
	backgound-color: blue;
}

button {
	background-color: white;
	border: none
}

.imageContainer {
	min-width: 10px;
	min-height: 10px;
	display: inline-block;
}

.addBorder {
	border: 3px solid black;
}

.headerDiv {
	height: 50px;
	width: 100%;
}

img {
	float: right;
	margin-top: 50px;
	height: 100px;
	width: 100px;
}
</style>

</head>
<body onload="hideAllImages()">
	<c:import var="sidebar" url="sidebar.jsp" />
	${sidebar}
	
	<div class="mainArea">

		<div class="headingArea">
			<h1>View Medicine</h1>
		</div>


		<div class="site-section">
			<div class="container">
				<div class="row">
					<div class="col-md-5 mr-auto">
						<div class="text-center">
							<div style="text-align: center">
								<c:set var="i" value="0" scope="page" />
								<c:forEach var="imagePath"
									items="${MedicineBean.getImagesPath()}">

									<div class="imageContainer" onclick="createBorder(this);">
										<button type="button" onclick="showImage(${i})">
											<img class="imgDiv" src="${imagePath}">
										</button>
									</div>
									<c:set var="i" value="${i+1}" scope="page" />
								</c:forEach>
								<br>
								<c:forEach var="imagePath"
									items="${MedicineBean.getImagesPath()}">
									<div class="imageMainContainer"
										style="left: 0; margin-left: 70px">
										<img class="imgDiv" style="height: 559px; width: 272px;"
											src="${imagePath}">
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<h2 class="text-black">${MedicineBean.getMedName()}</h2>
						<p style="color: gray">Description</p>
						<p>${MedicineBean.getMedDescription()}</p>
						<p style="color: gray">Category</p>
						<p><jsp:useBean id="categorydao"
								class="com.narola.pharmacy.category.CategoryDAO" />
							<c:set var="mbeanId" value="${MedicineBean.getCatId()}"
								scope="request" />
							<c:set var="cb"
								value="${categorydao.getCategoryById(MedicineBean.getCatId())}" />
							<c:out value="${cb.getCatName()}" />
						</p>
						<p class="price">
							<del>
								<i style="font-size: 24px; color: black" class="fa fa-rupee">&#8377;</i>
								${MedicineBean.getMedPrice()}
							</del>
							<strong class="text-warning h1" style="color: #33B2FF">
								<i style="font-size: 24px; color: black" class="fa fa-rupee">&#8377;</i>${MedicineBean.getMedDiscountedPrice()}</strong>
						</p>
						<p style="color: gray">Mfg Date</p>
						<p>${MedicineBean.getMedMfgDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))}</p>
						<p style="color: gray">Exp Date</p>
						<p>${MedicineBean.getMedExpDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))}</p>
						<p>
							<a href="ShowAllMedicine">Go Back</a>
						</p>

					</div>
				</div>
			</div>
		</div>

	</div>

	<script src="resources/js/jquery-3.3.1.min.js"></script>
	<script src="resources/js/jquery-ui.js"></script>
	<script src="resources/js/popper.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/owl.carousel.min.js"></script>
	<script src="resources/js/jquery.magnific-popup.min.js"></script>
	<script src="resources/js/aos.js"></script>

	<script src="resources/js/main.js"></script>
</body>
</html>
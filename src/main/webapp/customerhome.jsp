<%@page import="com.narola.pharmacy.utility.Constant"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.narola.pharmacy.test.model.TestBean"%>
<%@page import="java.io.File"%>
<%@ page import="com.narola.pharmacy.medicine.model.MedicineBean"%>
<%@ page import="com.narola.pharmacy.category.CategoryBean"%>
<%@ page import="com.narola.pharmacy.category.CategoryDAO"%>
<%@ page import="java.util.Base64"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer Home Page</title>

<link
	href="https://fonts.googleapis.com/css?family=Rubik:400,700|Crimson+Text:400,400i"
	rel="stylesheet">
<link rel="stylesheet" href="resources/fonts/icomoon/style.css">

<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/magnific-popup.css">
<link rel="stylesheet" href="resources/css/jquery-ui.css">
<link rel="stylesheet" href="resources/css/owl.carousel.min.css">
<link rel="stylesheet" href="resources/css/owl.theme.default.min.css">


<link rel="stylesheet" href="resources/css/aos.css">

<link rel="stylesheet" href="resources/css/style.css">
<link rel="stylesheet" href="resources/css/userSideLayout.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<style>
.arrowkeys {
	height: 250px;
	width: 50px;
	background-color: white;
	border: none;
}

.scrollingContainer {
	height: 250px;
	width: 90%;
	overflow: hidden;
}

img {
	height: 100px;
	width: 100px;
}

.card {
	background-color: #F2AA4CFF;
	color: black
}

.info-wrap {
	padding: 30px
}

.item:hover {
	box-shadow: 0 5px 15px rgba(0, 0, 0, 0.8);
}

#catScrollable {
	margin-top: 10px;
	height: 100px;
	display: table-cell;
	vertical-align: center
}

.item>h4>a {
	text-decoration: none;
	color: black;
}

.item>h4>a:hover {
	text-decoration: none;
	color: white;
}

.banner-wrap:hover {
	background-color: black;
}
</style>
<script>
(function(w){
	
    w.addEventListener('load', function(){
        const   btn_left = document.getElementById('medleftbtn'),
                btn_right = document.getElementById('medrightbtn'),
                content = document.getElementById('medScrollingContainer'),
        		division=document.getElementById('medScrollable');
       
       
        btn_right.addEventListener('click', () => {
        	content.scrollLeft += division.scrollWidth;
  
        });
        btn_left.addEventListener('click', () => {
        	content.scrollLeft -= division.scrollWidth;
        });
    });
})(window);

(function(w){
	
    w.addEventListener('load', function(){
        const   btn_left = document.getElementById('pmedleftbtn'),
                btn_right = document.getElementById('pmedrightbtn'),
                content = document.getElementById('pmedScrollingContainer'),
        		division=document.getElementById('pmedScrollable');
       
       
        btn_right.addEventListener('click', () => {
        	content.scrollLeft += division.scrollWidth;
  
        });
        btn_left.addEventListener('click', () => {
        	content.scrollLeft -= division.scrollWidth;
        });
    });
})(window);

(function(w){
	
    w.addEventListener('load', function(){
        const   btn_left = document.getElementById('testleftbtn'),
                btn_right = document.getElementById('testrightbtn'),
                content = document.getElementById('testScrollingContainer'),
        		division=document.getElementById('testScrollable');
       
       
        btn_right.addEventListener('click', () => {
        	content.scrollLeft += division.scrollWidth;
  
        });
        btn_left.addEventListener('click', () => {
        	content.scrollLeft -= division.scrollWidth;
        });
    });
})(window);

(function(w){
	
    w.addEventListener('load', function(){
        const   btn_left = document.getElementById('catleftbtn'),
                btn_right = document.getElementById('catrightbtn'),
                content = document.getElementById('catScrollingContainer'),
        		division=document.getElementById('catScrollable');
       
       
        btn_right.addEventListener('click', () => {
        	content.scrollLeft += division.scrollWidth;
  
        });
        btn_left.addEventListener('click', () => {
        	content.scrollLeft -= division.scrollWidth;
        });
    });
})(window);
</script>
</head>
<body>
<c:if test="${not empty ShowOrderSuccess}">
		<c:import var="orderSuccess" url="orderplaced.jsp" />
${orderSuccess}

</c:if>

	<jsp:include page="navbar.jsp"></jsp:include>
	<div class="site-section mainArea">
		<c:if test="${not empty DiscountMedicineList}">
			<div class="container">

				<div class="row">
					<div class="title-section text-center col-12">
						<h2 class="text-uppercase">Hot Deals</h2>
						<a
							href="${pageContext.request.contextPath}${Constant.URL_CUSTOMER_HOME}?query=medicine"><label
							style="float: right; margin-top: 30px;">View All</label></a>
					</div>
				</div>

				<div class="d-flex">
					<div>
						<button type="button" class="arrowkeys" id="medleftbtn">
							<i class="glyphicon glyphicon-backward"></i>
						</button>
					</div>

					<div class="scrollingContainer d-flex" id="medScrollingContainer">
						<c:forEach var="MedicineItem" items="${DiscountMedicineList}">


							<div class="col-sm-6 col-lg-4 text-center item mb-4"
								id="medScrollable">
								<span class="tag">Sale</span> <a
									href="${pageContext.request.contextPath}${Constant.URL_CUSTOMER_MEDICINE}?medId=${MedicineItem.getMedId()}">
									<img src='${MedicineItem.getImagesPath().get(0)}' alt="Image" />
								</a>
								<h3 class="text-dark">
									<a
										href="${pageContext.request.contextPath}${Constant.URL_CUSTOMER_MEDICINE}?medId=${MedicineItem.getMedId()}">${MedicineItem.getMedName()}</a>
								</h3>
								<p>
								<h3>
									<c:out value="${MedicineItem.getMedDiscount()}" />
									% OFF
								</h3>
								</p>
								<p class="price">
									<del>
										<i style="font-size: 24px; color: black" class="fa fa-rupee">&#8377;</i>
										${MedicineItem.getMedPrice()}
									</del>
									<i style="font-size: 24px; color: black" class="fa fa-rupee">&#8377;</i>${MedicineItem.getMedDiscountedPrice()}
								</p>
							</div>
						</c:forEach>
					</div>

					<div>
						<button type="button" class="arrowkeys" id="medrightbtn">
							<i class="glyphicon glyphicon-forward"></i>
						</button>
					</div>
				</div>
			</div>
		</c:if>
		<c:if test="${not empty PopularMedicineList}">
			<div class="container">

				<div class="row">
					<div class="title-section text-center col-12">
						<h2 class="text-uppercase">Popular Medicines</h2>
						<a
							href="${pageContext.request.contextPath}${Constant.URL_CUSTOMER_HOME}?query=medicine"><label
							style="float: right; margin-top: 30px;">View All</label></a>
					</div>
				</div>

				<div class="d-flex">
					<div>
						<button type="button" class="arrowkeys" id="pmedleftbtn">
							<i class="glyphicon glyphicon-backward"></i>
						</button>
					</div>

					<div class="scrollingContainer d-flex" id="pmedScrollingContainer">
						<c:forEach var="MedicineItem" items="${PopularMedicineList}">


							<div class="col-sm-6 col-lg-4 text-center item mb-4"
								id="pmedScrollable">
								<span class="tag">Sale</span> <a
									href="${pageContext.request.contextPath}${Constant.URL_CUSTOMER_MEDICINE}?medId=${MedicineItem.getMedId()}">
									<img src='${MedicineItem.getImagesPath().get(0)}' alt="Image" />
								</a>
								<h3 class="text-dark">
									<a
										href="${pageContext.request.contextPath}${Constant.URL_CUSTOMER_MEDICINE}?medId=${MedicineItem.getMedId()}">${MedicineItem.getMedName()}</a>
								</h3>
								<p>
								<h3>
									<c:out value="${MedicineItem.getMedDiscount()}" />
									% OFF
								</h3>
								</p>
								<p class="price">
									<del>
										<i style="font-size: 24px; color: black" class="fa fa-rupee">&#8377;</i>
										${MedicineItem.getMedPrice()}
									</del>
									<i style="font-size: 24px; color: black" class="fa fa-rupee">&#8377;</i>${MedicineItem.getMedDiscountedPrice()}
								</p>
							</div>
						</c:forEach>
					</div>

					<div>
						<button type="button" class="arrowkeys" id="pmedrightbtn">
							<i class="glyphicon glyphicon-forward"></i>
						</button>
					</div>
				</div>
			</div>
		</c:if>


		<c:if test="${not empty PopularTestList}">
			<div class="container">

				<div class="row">
					<div class="title-section text-center col-12">
						<h2 class="text-uppercase">Popular Tests</h2>
						<a
							href="${pageContext.request.contextPath}${Constant.URL_CUSTOMER_HOME}?query=test"><label
							style="float: right; margin-top: 30px;">View All</label></a>
					</div>
				</div>

				<div class="d-flex">
					<div>
						<button type="button" class="arrowkeys" id="testleftbtn">
							<i class="glyphicon glyphicon-backward"></i>
						</button>
					</div>


					<div class="scrollingContainer d-flex" id="testScrollingContainer">
						<c:forEach var="TestItem" items="${PopularTestList}">

							<div class="col-sm-6 col-lg-4 text-center item mb-4"
								id="testScrollable">
								<a
									href="${pageContext.request.contextPath}${Constant.URL_CUSTOMER_TEST}?testId=${TestItem.getTestId()}">
									<img src="data:image/png;base64,${TestItem.getBase64String()}">
								</a>
								<h3 class="text-dark">
									<a
										href="${pageContext.request.contextPath}${Constant.URL_CUSTOMER_TEST}?testId=${TestItem.getTestId()}">${TestItem.getTestName()}</a>
								</h3>
								<p>
								<h3>
									<c:out value="${TestItem.getTestDiscount()}" />
									% OFF
								</h3>
								</p>
								<p class="price">
									<del>
										<i style="font-size: 24px; color: black" class="fa fa-rupee">&#8377;</i>
										${TestItem.getTestPrice()}
									</del>
									<i style="font-size: 24px; color: black" class="fa fa-rupee">&#8377;</i>
									<c:out value="${TestItem.getTestDiscountedPrice()}" />
								</p>
							</div>
						</c:forEach>
					</div>

					<div>
						<button type="button" class="arrowkeys" id="testrightbtn">
							<i class="glyphicon glyphicon-forward"></i>
						</button>
					</div>
				</div>
			</div>
		</c:if>

		<c:if test="${not empty PopularCategoryList}">
			<div class="container">

				<div class="row">
					<div class="title-section text-center col-12">
						<h2 class="text-uppercase">Popular Categories</h2>
						<a
							href="${pageContext.request.contextPath}${Constant.URL_CUSTOMER_HOME}?query=category"><label
							style="float: right; margin-top: 30px;">View All</label></a>
					</div>
				</div>

				<div class="d-flex">
					<div>
						<button type="button" class="arrowkeys" id="catleftbtn"
							style="height: 100px">
							<i class="glyphicon glyphicon-backward"></i>
						</button>
					</div>


					<div class="scrollingContainer d-flex" id="catScrollingContainer">
						<c:forEach var="CategoryItem" items="${PopularCategoryList}">
							<div class="col-sm-6 col-lg-4 text-center mb-4 mb-lg-0"
								id="catScrollable" style="">

								<div class="banner-wrap h-100 item">
									<h4>
										<a
											href="${pageContext.request.contextPath}${Constant.URL_CUSTOMER_HOME}?query=${CategoryItem.getCatId()}"
											class="h-100"> ${CategoryItem.getCatName()} </a>
									</h4>
								</div>
							</div>
						</c:forEach>
					</div>

					<div>
						<button type="button" class="arrowkeys" id="catrightbtn"
							style="height: 100px">
							<i class="glyphicon glyphicon-forward"></i>
						</button>
					</div>
				</div>
			</div>
		</c:if>

		<div class="container">
			<c:if test="${not empty MedicineList}">
				<div class="row">
					<div class="title-section text-center col-12">
						<h2 class="text-uppercase">Medicines</h2>
					</div>
				</div>
			</c:if>
			<div class="row">
				<c:forEach var="MedicineItem" items="${MedicineList}">

					<div class="col-sm-6 col-lg-4 text-center item mb-4"
						style="padding: 30px;">
						<span class="tag">Sale</span> <a
							href="${pageContext.request.contextPath}${Constant.URL_CUSTOMER_MEDICINE}?medId=${MedicineItem.getMedId()}">
							<img src="${MedicineItem.getImagesPath().get(0)}" alt="Image">
						</a>
						<h3 class="text-dark">
							<a
								href="${pageContext.request.contextPath}${Constant.URL_CUSTOMER_MEDICINE}?medId=${MedicineItem.getMedId()}">${MedicineItem.getMedName()}</a>
						</h3>
						<p class="price">
							<del>
								<i style="font-size: 24px; color: black" class="fa fa-rupee">&#8377;</i>
								${MedicineItem.getMedPrice()}
							</del>
							<i style="font-size: 24px; color: black" class="fa fa-rupee">&#8377;</i>${MedicineItem.getMedDiscountedPrice()}
						</p>
					</div>
				</c:forEach>

			</div>
		</div>
		<div class="container">
			<c:if test="${not empty TestList}">
				<div class="row">
					<div class="title-section text-center col-12">
						<h2 class="text-uppercase">Tests</h2>
					</div>
				</div>
			</c:if>
			<div class="row">
				<c:forEach var="TestItem" items="${TestList}">

					<div class="col-sm-6 col-lg-4 text-center item mb-4"
						style="padding: 30px;">
						<a
							href="${pageContext.request.contextPath}${Constant.URL_CUSTOMER_TEST}?testId=${TestItem.getTestId()}">
							<img src="data:image/png;base64,${TestItem.getBase64String()}">
						</a>
						<h3 class="text-dark">
							<a
								href="${pageContext.request.contextPath}${Constant.URL_CUSTOMER_TEST}?testId=${TestItem.getTestId()}">${TestItem.getTestName()}</a>
						</h3>
						<p class="price">
									<del>
										<i style="font-size: 24px; color: black" class="fa fa-rupee">&#8377;</i>
										${TestItem.getTestPrice()}
									</del>
									<i style="font-size: 24px; color: black" class="fa fa-rupee">&#8377;</i>
									${TestItem.getTestDiscountedPrice()}
								</p>
					</div>
				</c:forEach>

			</div>
		</div>
		<div class="container">
			<c:if test="${not empty CategoryList2}">
				<div class="row">
					<div class="title-section text-center col-12">
						<h2 class="text-uppercase">Categories</h2>
					</div>
				</div>
			</c:if>
			<div class="row">
				<c:forEach var="CategoryItem" items="${CategoryList2}">
					<div class="col-sm-6 col-lg-4 text-center mb-4 mb-lg-0"
						id="catScrollable"
						style="margin-top: 10px; height: 100px; display: table-cell; vertical-align: center;">

						<div class="banner-wrap h-100 item">
							<h4>
								<a
									href="${pageContext.request.contextPath}${Constant.URL_CUSTOMER_HOME}?query=${CategoryItem.getCatId()}"
									class="h-100"> ${CategoryItem.getCatName()} </a>
							</h4>
						</div>
					</div>
				</c:forEach>

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
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Đặc Sản Việt - Tinh Hoa Quà Việt</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<link href="https://fonts.googleapis.com/css2?family=Dancing+Script:wght@700&family=Roboto:wght@300;400;700&display=swap" rel="stylesheet">

<style>
:root {
	--primary-color: #b71c1c; /* Đỏ đô - Màu chủ đạo */
	--secondary-color: #ffb300; /* Vàng nghệ - Màu nhấn */
	--text-color: #333;
	--bg-color: #fff8e1; /* Màu kem nhạt - Nền */
}

body {
	background-color: var(--bg-color);
	font-family: 'Roboto', sans-serif;
}

/* 1. HEADER & NAV */
.top-nav {
	background-color: var(--primary-color);
	padding: 15px 0;
	box-shadow: 0 4px 10px rgba(0,0,0,0.15);
	color: white;
	position: sticky;
	top: 0;
	z-index: 1000;
}

.brand-logo {
	font-family: 'Dancing Script', cursive;
	font-size: 2rem;
	font-weight: 700;
	color: var(--secondary-color);
	text-shadow: 1px 1px 2px rgba(0,0,0,0.3);
}

.user-controls a {
	color: white;
	text-decoration: none;
	margin-left: 15px;
	font-weight: 500;
	transition: 0.3s;
}

.user-controls a:hover {
	color: var(--secondary-color);
}

/* 2. HERO BANNER */
.hero-section {
	/* Ảnh nền giả lập đồ ăn */
	background: linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)), 
				url('https://images.unsplash.com/photo-1504674900247-0877df9cc836?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80'); 
	background-size: cover;
	background-position: center;
	border-radius: 0 0 20px 20px;
	padding: 100px 20px;
	text-align: center;
	color: white;
	margin-bottom: 40px;
}

.hero-title {
	font-family: 'Dancing Script', cursive;
	font-size: 3.5rem;
	margin-bottom: 10px;
	color: var(--secondary-color);
}

.hero-subtitle {
	font-size: 1.2rem;
	margin-bottom: 30px;
	font-weight: 300;
}

.btn-shop {
	background-color: var(--secondary-color);
	color: #000;
	padding: 12px 35px;
	border-radius: 50px;
	font-weight: 700;
	text-transform: uppercase;
	border: none;
	transition: transform 0.2s;
}

.btn-shop:hover {
	background-color: #ffa000;
	transform: scale(1.05);
}

/* 3. KHU VỰC ADMIN */
.admin-panel {
	background: white;
	border-left: 5px solid var(--primary-color);
	padding: 20px;
	margin-bottom: 40px;
	box-shadow: 0 5px 15px rgba(0,0,0,0.05);
	display: flex;
	justify-content: space-between;
	align-items: center;
	border-radius: 8px;
}

/* 4. DANH MỤC NỔI BẬT */
.category-card {
	background: white;
	border-radius: 15px;
	overflow: hidden;
	box-shadow: 0 5px 15px rgba(0,0,0,0.08);
	transition: 0.3s;
	text-align: center;
	padding: 20px;
	height: 100%;
	border: 1px solid #eee;
}

.category-card:hover {
	transform: translateY(-5px);
	border-color: var(--secondary-color);
}

.cat-icon {
	width: 70px;
	height: 70px;
	background: #fff3e0;
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	margin: 0 auto 15px;
	color: var(--primary-color);
	font-size: 1.8rem;
}

.cat-title {
	font-weight: 700;
	color: var(--text-color);
}

/* 5. SẢN PHẨM DEMO */
.product-demo img {
	border-radius: 10px;
	width: 100%;
	height: 180px;
	object-fit: cover;
}
.section-title {
	text-align: center;
	margin-bottom: 40px;
	color: var(--primary-color);
	font-weight: 700;
	text-transform: uppercase;
	position: relative;
}
.section-title::after {
	content: "";
	display: block;
	width: 60px;
	height: 3px;
	background: var(--secondary-color);
	margin: 10px auto 0;
}
</style>
</head>
<body>

	<div class="top-nav">
		<div class="container d-flex justify-content-between align-items-center">
			<div class="brand-logo">
				<i class="fa-solid fa-leaf me-2"></i> Đặc Sản Việt
			</div>
			
			<div class="user-controls">
				<c:choose>
					<c:when test="${sessionScope.account == null}">
						<a href="${pageContext.request.contextPath}/login">
							<i class="fa-regular fa-user me-1"></i> Đăng nhập
						</a>
						<span class="mx-2">|</span>
						<a href="${pageContext.request.contextPath}/register">
							Đăng ký
						</a>
					</c:when>
					
					<c:otherwise>
						<span>Xin chào, <strong>${sessionScope.account.fullName}</strong></span>
						<a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light btn-sm ms-3 px-3 rounded-pill">
							Thoát <i class="fa-solid fa-right-from-bracket ms-1"></i>
						</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>

	<div class="hero-section">
		<div class="container">
			<h1 class="hero-title">Mang Hương Vị Quê Hương Về Nhà</h1>
			<p class="hero-subtitle">Chuyên cung cấp đặc sản 3 miền: Tươi ngon - An toàn - Đậm đà bản sắc</p>
			<a href="#products" class="btn btn-shop shadow">
				<i class="fa-solid fa-basket-shopping me-2"></i> Mua Ngay
			</a>
		</div>
	</div>

	<div class="container">

		<c:if test="${sessionScope.account != null && sessionScope.account.role == 3}">
			<div class="admin-panel">
				<div>
					<h5 class="text-danger fw-bold mb-1"><i class="fa-solid fa-user-gear me-2"></i>Trang Quản Trị Viên</h5>
					<small class="text-muted">Quản lý sản phẩm, đơn hàng và danh mục tại đây.</small>
				</div>
				<a href="${pageContext.request.contextPath}/admin/home" class="btn btn-danger">
					<i class="fa-solid fa-gauge-high me-2"></i> Vào Dashboard
				</a>
			</div>
		</c:if>

		<h3 class="section-title">Danh Mục Nổi Bật</h3>
		<div class="row mb-5">
			<div class="col-md-3 col-6 mb-3">
				<div class="category-card">
					<div class="cat-icon"><i class="fa-solid fa-bowl-rice"></i></div>
					<h6 class="cat-title">Gạo & Nông Sản</h6>
				</div>
			</div>
			<div class="col-md-3 col-6 mb-3">
				<div class="category-card">
					<div class="cat-icon"><i class="fa-solid fa-fish-fins"></i></div>
					<h6 class="cat-title">Hải Sản Khô</h6>
				</div>
			</div>
			<div class="col-md-3 col-6 mb-3">
				<div class="category-card">
					<div class="cat-icon"><i class="fa-solid fa-wine-bottle"></i></div>
					<h6 class="cat-title">Rượu & Đồ Uống</h6>
				</div>
			</div>
			<div class="col-md-3 col-6 mb-3">
				<div class="category-card">
					<div class="cat-icon"><i class="fa-solid fa-cookie-bite"></i></div>
					<h6 class="cat-title">Bánh Kẹo Vùng Miền</h6>
				</div>
			</div>
		</div>

		<h3 class="section-title" id="products">Sản Phẩm Bán Chạy</h3>
		<div class="row mb-5">
			<div class="col-md-4 mb-4">
				<div class="card h-100 border-0 shadow-sm product-demo">
					<img src="https://images.unsplash.com/photo-1599321955726-90471f1b9527?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80" class="card-img-top" alt="Nem Chua">
					<div class="card-body text-center">
						<h5 class="card-title fw-bold">Nem Chua Thanh Hóa</h5>
						<p class="text-danger fw-bold">65.000đ <span class="text-muted text-decoration-line-through fw-normal small">80.000đ</span></p>
						<button class="btn btn-outline-danger w-100 btn-sm">Thêm vào giỏ</button>
					</div>
				</div>
			</div>
			<div class="col-md-4 mb-4">
				<div class="card h-100 border-0 shadow-sm product-demo">
					<img src="https://images.unsplash.com/photo-1600452411394-4ec59c524548?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80" class="card-img-top" alt="Hat Dieu">
					<div class="card-body text-center">
						<h5 class="card-title fw-bold">Hạt Điều Bình Phước</h5>
						<p class="text-danger fw-bold">250.000đ</p>
						<button class="btn btn-outline-danger w-100 btn-sm">Thêm vào giỏ</button>
					</div>
				</div>
			</div>
			<div class="col-md-4 mb-4">
				<div class="card h-100 border-0 shadow-sm product-demo">
					<img src="https://images.unsplash.com/photo-1563227812-0ea4c22e6cc8?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80" class="card-img-top" alt="Tra">
					<div class="card-body text-center">
						<h5 class="card-title fw-bold">Trà Tân Cương Thái Nguyên</h5>
						<p class="text-danger fw-bold">180.000đ</p>
						<button class="btn btn-outline-danger w-100 btn-sm">Thêm vào giỏ</button>
					</div>
				</div>
			</div>
		</div>

	</div>
	
	<footer class="bg-dark text-white text-center py-3 mt-5">
		<p class="mb-0 small">&copy; 2025 Đặc Sản Việt - Mang Hương Vị Quê Hương Đến Mọi Nhà. All rights reserved.</p>
	</footer>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
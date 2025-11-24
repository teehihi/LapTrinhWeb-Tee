<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Trang Người Dùng</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
:root {
	--primary-color: #b71c1c;
	--secondary-color: #ffb300;
	--bg-color: #fff8e1;
}

body {
	background-color: var(--bg-color);
	font-family: 'Roboto', sans-serif;
}

.hero {
	background: linear-gradient(120deg, #b71c1c, #f44336);
	color: white;
	padding: 40px 0;
	border-radius: 0 0 30px 30px;
	box-shadow: 0 5px 20px rgba(0, 0, 0, 0.2);
}

.module-card {
	background: white;
	border-radius: 16px;
	box-shadow: 0 10px 25px rgba(0, 0, 0, 0.08);
	padding: 25px;
	text-align: center;
	transition: transform 0.2s;
	height: 100%;
	border: 1px solid #ffe0b2;
}

.module-card:hover {
	transform: translateY(-5px);
}

.module-icon {
	font-size: 2.5rem;
	color: var(--primary-color);
	margin-bottom: 15px;
}

.module-card p {
	color: #666;
	min-height: 60px;
}

.module-card .btn {
	border-radius: 50px;
	font-weight: 600;
}

.section-title {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 20px;
}

.section-title h3 {
	font-weight: 700;
	color: var(--primary-color);
}

.category-card,
.product-card {
	background: white;
	border-radius: 16px;
	box-shadow: 0 10px 25px rgba(0, 0, 0, 0.08);
	overflow: hidden;
	transition: transform 0.2s ease;
	height: 100%;
	border: 1px solid #ffe0b2;
}

.category-card:hover,
.product-card:hover {
	transform: translateY(-6px);
}

.category-card img,
.product-card img {
	width: 100%;
	height: 180px;
	object-fit: cover;
}

.category-card .card-body,
.product-card .card-body {
	padding: 18px;
}

.tag {
	display: inline-block;
	padding: 4px 12px;
	background: #fff3e0;
	border-radius: 20px;
	font-size: 0.85rem;
	color: #d84315;
}
</style>
</head>
<body>
	<header class="hero mb-5">
		<div class="container d-flex justify-content-between align-items-center flex-wrap gap-3">
			<div>
				<h1 class="h3 fw-bold mb-2">Xin chào, <c:out value="${sessionScope.account.fullName}" /></h1>
				<p class="mb-0">Khám phá các danh mục đặc sản mới nhất</p>
			</div>
			<a href="${pageContext.request.contextPath}/logout"
				class="btn btn-outline-light px-4">Đăng xuất</a>
		</div>
	</header>

	<div class="container">
		<div class="row g-4 my-4">
			<div class="col-md-6">
				<div class="module-card">
					<i class="fa-solid fa-layer-group module-icon"></i>
					<h4 class="fw-bold mb-2">Quản lý danh mục</h4>
					<p>Theo dõi và cập nhật danh sách danh mục hiện có.</p>
					<div class="d-flex gap-2 justify-content-center flex-wrap">
						<a href="${pageContext.request.contextPath}/user/categories"
							class="btn btn-outline-danger px-4">Xem danh mục</a> <a
							href="${pageContext.request.contextPath}/user/category/list"
							class="btn btn-danger px-4 text-white">Quản lý danh mục</a>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="module-card">
					<i class="fa-solid fa-boxes-stacked module-icon"></i>
					<h4 class="fw-bold mb-2">Quản lý sản phẩm</h4>
					<p>Tổng quan các sản phẩm nổi bật trong hệ thống.</p>
					<div class="d-flex gap-2 justify-content-center flex-wrap">
						<a href="${pageContext.request.contextPath}/user/products"
							class="btn btn-outline-danger px-4">Xem sản phẩm</a> <a
							href="${pageContext.request.contextPath}/user/product/list"
							class="btn btn-danger px-4 text-white">Quản lý sản phẩm</a>
					</div>
				</div>
			</div>
		</div>

		<section id="category-section" class="my-5">
			<div class="section-title">
				<h3>Danh mục hiện có</h3>
				<span class="text-muted">Tổng cộng: ${fn:length(cateList)} danh mục</span>
			</div>
			<div class="row g-4">
				<c:forEach var="cate" items="${cateList}">
					<div class="col-lg-4 col-md-6">
						<a href="${pageContext.request.contextPath}/user/products?cid=${cate.id}"
							class="text-decoration-none text-dark">
							<div class="category-card">
								<c:choose>
									<c:when test="${not empty cate.image}">
										<img src="${pageContext.request.contextPath}/${cate.image}" alt="${cate.cateName}" />
									</c:when>
									<c:otherwise>
										<img src="https://via.placeholder.com/400x180?text=Category" alt="placeholder" />
									</c:otherwise>
								</c:choose>
								<div class="card-body">
									<h5 class="fw-bold mb-1">${cate.cateName}</h5>
									<span class="badge bg-light text-dark border">Nhấp để xem sản phẩm</span>
								</div>
							</div>
						</a>
					</div>
				</c:forEach>
			</div>

			<c:if test="${empty cateList}">
				<div class="alert alert-warning text-center mt-4">Chưa có danh mục nào.</div>
			</c:if>
		</section>

		<section id="product-section" class="my-5">
			<div class="section-title">
				<h3>Sản phẩm nổi bật</h3>
				<span class="text-muted">Hiển thị tối đa 6 sản phẩm gần đây</span>
			</div>
			<div class="row g-4">
				<c:forEach var="product" items="${productList}" varStatus="status">
					<c:if test="${status.index < 6}">
						<div class="col-lg-4 col-md-6">
							<div class="product-card">
								<c:choose>
									<c:when test="${not empty product.image}">
										<img src="${pageContext.request.contextPath}/${product.image}" alt="${product.name}" />
									</c:when>
									<c:otherwise>
										<img src="https://via.placeholder.com/400x180?text=Product" alt="placeholder" />
									</c:otherwise>
								</c:choose>
								<div class="card-body">
									<h5 class="fw-bold mb-2">${product.name}</h5>
									<span class="tag">Danh mục #${product.categoryId}</span>
								</div>
							</div>
						</div>
					</c:if>
				</c:forEach>
			</div>

			<c:if test="${empty productList}">
				<div class="alert alert-info text-center mt-4">Chưa có sản phẩm nào.</div>
			</c:if>
		</section>
	</div>
</body>
</html>


<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Danh mục sản phẩm</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<style>
:root {
	--primary-color: #b71c1c;
	--secondary-color: #ffb300;
	--bg-color: #fff8e1;
}

body {
	background-color: var(--bg-color);
	font-family: 'Roboto', sans-serif;
	min-height: 100vh;
	padding: 40px 0;
}

.page-wrapper {
	background: white;
	border-radius: 18px;
	box-shadow: 0 15px 30px rgba(0, 0, 0, 0.08);
	width: 92vw;
	max-width: 1200px;
	margin: 0 auto;
	padding: 40px;
	border-top: 6px solid var(--primary-color);
}

.page-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	gap: 20px;
	margin-bottom: 30px;
}

.page-header h2 {
	font-weight: 700;
	color: var(--primary-color);
	margin-bottom: 0;
}

.category-card {
	background: #fffdf7;
	border: 1px solid #ffe0b2;
	border-radius: 16px;
	box-shadow: 0 8px 20px rgba(0, 0, 0, 0.05);
	overflow: hidden;
	transition: transform 0.2s ease;
	height: 100%;
	display: flex;
	flex-direction: column;
}

.category-card:hover {
	transform: translateY(-6px);
}

.category-card img {
	width: 100%;
	height: 190px;
	object-fit: cover;
}

.category-card .card-body {
	padding: 18px;
	flex-grow: 1;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
}

.category-card h5 {
	font-weight: 700;
	color: #4e342e;
}
</style>
</head>
<body>
	<div class="page-wrapper">
		<div class="page-header">
			<div>
				<h2>
					<i class="fa-solid fa-layer-group me-2"></i>Danh mục sản phẩm
				</h2>
				<p class="text-muted mb-0">Nhấp vào danh mục để xem các sản phẩm bên trong</p>
			</div>
			<div class="d-flex gap-2 flex-wrap">
				<a href="${pageContext.request.contextPath}/user/home" class="btn btn-outline-secondary"><i
					class="fa-solid fa-arrow-left"></i> Về trang chủ</a> <a
					href="${pageContext.request.contextPath}/user/products"
					class="btn btn-outline-danger">Xem tất cả sản phẩm</a> <a
					href="${pageContext.request.contextPath}/user/category/list"
					class="btn btn-danger">Quản lý danh mục của tôi</a>
			</div>
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
									<img src="https://via.placeholder.com/500x190?text=Category" alt="placeholder" />
								</c:otherwise>
							</c:choose>
							<div class="card-body">
								<div>
									<h5 class="mb-2">${cate.cateName}</h5>
									<c:if test="${not empty cate.ownerName}">
										<small class="text-muted">Tạo bởi: ${cate.ownerName}</small>
									</c:if>
								</div>
								<div class="d-flex justify-content-between align-items-center mt-3">
									<span class="badge bg-warning text-dark">Xem sản phẩm</span>
									<i class="fa-solid fa-arrow-right text-danger"></i>
								</div>
							</div>
						</div>
					</a>
				</div>
			</c:forEach>
		</div>

		<c:if test="${empty cateList}">
			<p class="text-center text-muted mt-4">Chưa có danh mục nào.</p>
		</c:if>
	</div>
</body>
</html>


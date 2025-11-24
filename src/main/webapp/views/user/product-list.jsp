<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<fmt:setLocale value="vi_VN" />
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Danh sách sản phẩm</title>
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
	flex-wrap: wrap;
	gap: 15px;
	margin-bottom: 25px;
}

.page-header h2 {
	font-weight: 700;
	color: var(--primary-color);
}

.product-card {
	border: 1px solid #ffe0b2;
	border-radius: 14px;
	overflow: hidden;
	box-shadow: 0 8px 20px rgba(0, 0, 0, 0.05);
	height: 100%;
	display: flex;
	flex-direction: column;
	background: #fffdf7;
}

.product-card img {
	width: 100%;
	height: 200px;
	object-fit: cover;
}

.product-card .card-body {
	padding: 18px;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	flex-grow: 1;
}
</style>
</head>
<body>
	<div class="page-wrapper">
		<div class="page-header">
			<div>
				<h2>
					<i class="fa-solid fa-box-open me-2"></i>
					<c:choose>
						<c:when test="${selectedCategory != null}">
							Sản phẩm: ${selectedCategory.cateName}
						</c:when>
						<c:otherwise>
							Tất cả sản phẩm
						</c:otherwise>
					</c:choose>
				</h2>
				<p class="text-muted mb-0">${fn:length(productList)} sản phẩm được tìm thấy.</p>
			</div>
			<div class="d-flex gap-2 flex-wrap">
				<a href="${pageContext.request.contextPath}/user/categories" class="btn btn-outline-secondary"><i
					class="fa-solid fa-arrow-left"></i> Về danh mục</a> <a
					href="${pageContext.request.contextPath}/user/products" class="btn btn-outline-danger">Xem tất cả</a>
				<a href="${pageContext.request.contextPath}/user/product/list" class="btn btn-danger">Quản lý sản phẩm</a>
			</div>
		</div>

		<form class="row g-2 mb-4" method="get" action="${pageContext.request.contextPath}/user/products">
			<div class="col-md-6">
				<select class="form-select form-select-lg" name="cid" onchange="this.form.submit()">
					<option value="">-- Lọc theo danh mục --</option>
					<c:forEach var="cate" items="${cateList}">
						<option value="${cate.id}" ${selectedCategory != null && selectedCategory.id == cate.id ? 'selected' : ''}>
							${cate.cateName}
						</option>
					</c:forEach>
				</select>
			</div>
		</form>

		<div class="row g-4">
			<c:forEach var="product" items="${productList}">
				<div class="col-lg-4 col-md-6">
					<div class="product-card">
						<c:choose>
							<c:when test="${not empty product.image}">
								<img src="${pageContext.request.contextPath}/${product.image}" alt="${product.name}" />
							</c:when>
							<c:otherwise>
								<img src="https://via.placeholder.com/500x200?text=Product" alt="placeholder" />
							</c:otherwise>
						</c:choose>
						<div class="card-body">
							<h5 class="fw-bold">${product.name}</h5>
							<p class="text-danger fw-bold mb-2">
								<fmt:formatNumber value="${product.price}" type="currency" currencySymbol="đ" />
							</p>
							<span class="badge bg-light text-dark border">Danh mục ID: ${product.categoryId}</span>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>

		<c:if test="${empty productList}">
			<p class="text-center text-muted mt-4">Không tìm thấy sản phẩm trong danh mục này.</p>
		</c:if>
	</div>
</body>
</html>


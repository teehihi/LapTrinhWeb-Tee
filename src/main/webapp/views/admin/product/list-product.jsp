<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<fmt:setLocale value="vi_VN" />

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Quản Lý Sản Phẩm</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<style>
:root {
	--primary-color: #b71c1c;
	--secondary-color: #ff6f00;
}

body {
	background-color: #fff8e1;
	font-family: 'Segoe UI', sans-serif;
	padding: 40px 0;
	min-height: 100vh;
	display: flex;
	justify-content: center;
}

.table-container {
	background: white;
	border-radius: 10px;
	padding: 30px;
	width: 95vw;
	max-width: 1300px;
	border-top: 4px solid var(--secondary-color);
	box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
}

h2 {
	color: var(--secondary-color);
	font-weight: 700;
}

.table thead {
	background-color: #fff3e0;
	color: #e65100;
}

.table img {
	width: 70px;
	height: 70px;
	object-fit: cover;
	border-radius: 5px;
}

.price-tag {
	color: #d32f2f;
	font-weight: 700;
}

.btn-add {
	background-color: var(--secondary-color);
	color: white;
	border: none;
	font-weight: 600;
}

.btn-add:hover {
	background-color: #e65100;
	color: white;
}

.action-btn {
	text-decoration: none;
	margin: 0 5px;
	font-size: 1.1rem;
}

.edit-btn {
	color: #1976d2;
}

.delete-btn {
	color: #d32f2f;
}
</style>
</head>
<body>
	<c:set var="productBase" value="${empty productBasePath ? '/admin/product' : productBasePath}" />
	<c:set var="dashboardPath" value="${empty productDashboardPath ? '/admin/home' : productDashboardPath}" />
	<c:set var="categoryListHref" value="${empty categoryListPath ? '/admin/category/list' : categoryListPath}" />
	<c:set var="manageProducts" value="${canManageProducts == null ? true : canManageProducts}" />
	<div class="table-container">
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h2>
				<i class="fa-solid fa-boxes-packing me-2"></i>Danh Sách Sản Phẩm
			</h2>
			<div>
				<c:choose>
					<c:when test="${not empty param.cid}">
						<a href="${pageContext.request.contextPath}${categoryListHref}"
							class="btn btn-outline-secondary me-2"><i
							class="fa-solid fa-arrow-left"></i> Về Danh Mục</a>
					</c:when>
					<c:otherwise>
						<a href="${pageContext.request.contextPath}${dashboardPath}"
							class="btn btn-outline-secondary me-2"><i
							class="fa-solid fa-arrow-left"></i> Về Dashboard</a>
					</c:otherwise>
				</c:choose>
				<c:if test="${manageProducts}">
					<a href="${pageContext.request.contextPath}${productBase}/add"
						class="btn btn-add"><i class="fa-solid fa-plus"></i> Thêm Sản
						Phẩm</a>
				</c:if>
			</div>
		</div>

		<div class="table-responsive">
			<table class="table table-hover align-middle">
				<thead>
					<tr>
						<th>STT</th>
						<th>Hình Ảnh</th>
						<th>Tên Sản Phẩm</th>
						<th>Giá Bán</th>
						<th>Danh Mục</th>
						<th class="text-center">Hành Động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${productList}" var="p" varStatus="stt">
						<tr>
							<td>${stt.index + 1}</td>
							<td><c:if test="${not empty p.image}">
									<img src="${pageContext.request.contextPath}/${p.image}">
								</c:if></td>
							<td class="fw-bold text-dark">${p.name}</td>
							<td class="price-tag"><fmt:formatNumber value="${p.price}"
									type="currency" currencySymbol="đ" /></td>
							<td><span class="badge bg-light text-dark border">ID:
									${p.categoryId}</span></td>
							<td class="text-center">
								<c:choose>
								<c:when test="${manageProducts}">
										<a href="${pageContext.request.contextPath}${productBase}/edit?id=${p.id}&cid=${param.cid}"
											class="action-btn edit-btn" title="Sửa"><i class="fa-solid fa-pen-to-square"></i></a>
										<a href="${pageContext.request.contextPath}${productBase}/delete?id=${p.id}"
											class="action-btn delete-btn" title="Xóa"
											onclick="return confirm('Xóa sản phẩm này?');"><i class="fa-solid fa-trash-can"></i></a>
									</c:when>
									<c:otherwise>
										<span class="text-muted">Chỉ xem</span>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:if test="${empty productList}">
				<p class="text-center text-muted p-3">Kho hàng đang trống.</p>
			</c:if>
		</div>
	</div>
</body>
</html>
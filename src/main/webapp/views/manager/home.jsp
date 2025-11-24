<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Manager Dashboard</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<style>
:root {
	--primary-color: #b71c1c;
	--secondary-color: #ffb300;
}

body {
	background-color: #fff8e1;
	font-family: 'Roboto', sans-serif;
	min-height: 100vh;
	padding: 40px 0;
}

.dashboard-wrapper {
	background: white;
	border-radius: 16px;
	box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
	width: 90vw;
	max-width: 1100px;
	margin: 0 auto;
	padding: 40px;
	border-top: 6px solid var(--primary-color);
}

.dash-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 30px;
}

.dash-header h2 {
	font-weight: 700;
	color: var(--primary-color);
}

.module-card {
	border: 1px solid #ffe0b2;
	border-radius: 12px;
	padding: 30px;
	height: 100%;
	background: #fffdf7;
	box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
}

.module-card .icon {
	font-size: 2.8rem;
	color: var(--primary-color);
}

.module-card h4 {
	margin-top: 15px;
	font-weight: 700;
}

.module-card p {
	color: #666;
	min-height: 60px;
}

.module-card .badge {
	background: var(--secondary-color);
}

.quick-list li {
	border: 1px solid #fce4ec;
	padding: 12px 16px;
	border-radius: 10px;
	margin-bottom: 10px;
	background: #fff;
	display: flex;
	justify-content: space-between;
	align-items: center;
}
</style>
</head>
<body>
	<div class="dashboard-wrapper">
		<div class="dash-header">
			<div>
				<h2>
					<i class="fa-solid fa-user-tie me-2"></i>Chào, <c:out value="${managerName}" />
				</h2>
				<p class="text-muted mb-0">Quản lý hiệu quả danh mục & sản phẩm của bạn</p>
			</div>
			<a href="${pageContext.request.contextPath}/logout" class="btn btn-danger px-4">Đăng xuất</a>
		</div>

		<div class="row g-4 mb-4">
			<div class="col-md-6">
				<div class="module-card">
					<div class="d-flex justify-content-between align-items-center">
						<i class="fa-solid fa-layer-group icon"></i>
						<span class="badge rounded-pill"> ${categoryCount} danh mục </span>
					</div>
					<h4>Quản lý danh mục</h4>
					<p>Kiểm soát danh mục do bạn tạo, cập nhật hình ảnh và thông tin đồng bộ.</p>
					<a href="${categoryLink}" class="btn btn-outline-danger px-4 mt-3">Vào danh sách</a>
				</div>
			</div>

			<div class="col-md-6">
				<div class="module-card">
					<div class="d-flex justify-content-between align-items-center">
						<i class="fa-solid fa-boxes-stacked icon"></i>
						<span class="badge rounded-pill bg-warning text-dark">${productCount} sản phẩm</span>
					</div>
					<h4>Quản lý sản phẩm</h4>
					<p>Xem nhanh các sản phẩm thuộc danh mục của bạn để phối hợp điều chỉnh.</p>
					<a href="${productLink}" class="btn btn-outline-danger px-4 mt-3">Xem sản phẩm</a>
				</div>
			</div>
		</div>

		<div class="border rounded-4 p-4 bg-white">
			<div class="d-flex justify-content-between align-items-center mb-3">
				<h5 class="mb-0 text-danger fw-bold">
					<i class="fa-solid fa-list-check me-2"></i>Danh mục gần đây
				</h5>
				<a href="${categoryLink}" class="btn btn-sm btn-outline-secondary">Xem tất cả</a>
			</div>
			<c:choose>
				<c:when test="${empty cateList}">
					<p class="text-muted mb-0">Bạn chưa tạo danh mục nào.</p>
				</c:when>
				<c:otherwise>
					<ul class="list-unstyled quick-list mb-0">
						<c:forEach var="cate" items="${cateList}" varStatus="loop">
							<c:if test="${loop.index < 5}">
								<li>
									<span class="fw-semibold">${cate.cateName}</span>
									<a href="${categoryLink}" class="btn btn-sm btn-outline-danger">Quản lý</a>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>


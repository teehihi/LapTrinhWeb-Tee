<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Trang Quản Trị - Đặc Sản Việt</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link
	href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap"
	rel="stylesheet">

<style>
:root {
	--primary-color: #b71c1c; /* Đỏ đô */
	--secondary-color: #ffb300; /* Vàng */
}

body {
	background-color: #fff8e1; /* Màu kem nhạt */
	font-family: 'Roboto', sans-serif;
	min-height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 40px 0;
}

.admin-container {
	background: white;
	border-radius: 12px;
	box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
	padding: 40px;
	width: 90vw;
	max-width: 900px;
	border-top: 5px solid var(--primary-color);
}

h2 {
	color: var(--primary-color);
	font-weight: 700;
	text-transform: uppercase;
	letter-spacing: 1px;
}

.card {
	border: 1px solid #eee;
	border-radius: 10px;
	transition: all 0.3s;
	height: 100%;
}

.card:hover {
	transform: translateY(-5px);
	box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
	border-color: var(--secondary-color);
}

.card-icon {
	font-size: 3rem;
	margin-bottom: 15px;
	color: var(--primary-color);
}

.btn-admin {
	background-color: var(--primary-color);
	color: white;
	border: none;
	padding: 10px 20px;
	border-radius: 50px;
	font-weight: 600;
	transition: 0.3s;
}

.btn-admin:hover {
	background-color: #d32f2f;
	color: white;
}

.btn-secondary {
	background-color: #666;
	border: none;
	border-radius: 50px;
	padding: 10px 25px;
}

.btn-secondary:hover {
	background-color: #444;
}
</style>
</head>

<body>
	<div class="admin-container">

		<h2 class="text-center mb-5">
			<i class="fa-solid fa-user-gear me-2"></i>Hệ Thống Quản Trị
		</h2>

		<div class="row g-4">
			<div class="col-md-6">
				<div class="card p-3">
					<div class="card-body text-center">
						<i class="fa-solid fa-layer-group card-icon"></i>
						<h4 class="fw-bold mb-2">Quản Lý Danh Mục</h4>
						<p class="text-muted mb-4">Phân loại đặc sản vùng miền, nhóm
							hàng.</p>
						<a href="${pageContext.request.contextPath}/admin/category/list"
							class="btn btn-admin"> Truy cập <i
							class="fa-solid fa-arrow-right ms-1"></i>
						</a>
					</div>
				</div>
			</div>

			<div class="col-md-6">
				<div class="card p-3">
					<div class="card-body text-center">
						<i class="fa-solid fa-box-open card-icon" style="color: #ff8f00;"></i>
						<h4 class="fw-bold mb-2">Quản Lý Sản Phẩm</h4>
						<p class="text-muted mb-4">Cập nhật hàng hóa, giá cả, hình
							ảnh.</p>
						<a href="${pageContext.request.contextPath}/admin/product/list"
							class="btn btn-admin" style="background-color: #ff8f00;">
							Truy cập <i class="fa-solid fa-arrow-right ms-1"></i>
						</a>
					</div>
				</div>
			</div>
		</div>

		<div class="text-center mt-5">
			<a href="${pageContext.request.contextPath}/waiting"
				class="btn btn-secondary"> <i class="fa-solid fa-house me-1"></i>
				Về Trang Chủ Shop
			</a>
		</div>
	</div>

</body>
</html>
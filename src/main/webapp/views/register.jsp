<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Đăng Ký - Đặc Sản Việt</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link
	href="https://fonts.googleapis.com/css2?family=Dancing+Script:wght@700&family=Roboto:wght@300;400;700&display=swap"
	rel="stylesheet">

<style>
:root {
	--primary-color: #b71c1c;
	--secondary-color: #ffb300;
}

body {
	background: linear-gradient(rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0.6)),
		url('https://images.unsplash.com/photo-1504674900247-0877df9cc836?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80');
	background-size: cover;
	background-position: center;
	min-height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 20px;
	font-family: 'Roboto', sans-serif;
}

.register-container {
	background: rgba(255, 255, 255, 0.95);
	border-radius: 15px;
	box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5);
	padding: 40px;
	width: 100%;
	max-width: 600px;
	border-top: 5px solid var(--secondary-color);
}

.register-container h2 {
	color: var(--primary-color);
	margin-bottom: 5px;
	font-family: 'Dancing Script', cursive;
	font-size: 2.5rem;
	text-align: center;
}

.sub-title {
	text-align: center;
	color: #666;
	margin-bottom: 25px;
}

.input-group-text {
	background-color: #fff3e0;
	border: 1px solid #ced4da;
	border-right: none;
	color: var(--primary-color);
}

.form-control {
	border-left: none;
	padding: 10px;
}

.form-control:focus {
	box-shadow: none;
	border-color: var(--secondary-color);
}

.input-group:focus-within .input-group-text, .input-group:focus-within .form-control
	{
	border-color: var(--secondary-color);
}

.btn-primary {
	background-color: var(--secondary-color);
	color: #000;
	border: none;
	padding: 12px;
	font-weight: 700;
	font-size: 16px;
	margin-top: 15px;
	transition: all 0.3s;
	border-radius: 8px;
	text-transform: uppercase;
}

.btn-primary:hover {
	background-color: #ffa000;
	transform: translateY(-2px);
}

.section-label {
	font-size: 0.85rem;
	font-weight: 700;
	color: var(--primary-color);
	margin-bottom: 15px;
	text-transform: uppercase;
	border-bottom: 2px solid #eee;
	padding-bottom: 5px;
	display: inline-block;
}

a {
	color: var(--primary-color);
	text-decoration: none;
	font-weight: 600;
}

a:hover {
	text-decoration: underline;
}
</style>
</head>
<body>
	<div class="register-container">
		<h2>Đăng Ký Thành Viên</h2>
		<p class="sub-title">Tích điểm đổi quà - Nhận ngàn ưu đãi</p>

		<c:if test="${not empty alert}">
			<div class="alert alert-danger text-center py-2" role="alert">
				<i class="fa-solid fa-circle-exclamation me-1"></i> ${alert}
			</div>
		</c:if>

		<form action="${pageContext.request.contextPath}/register"
			method="post">

			<div class="section-label">
				Thông tin tài khoản <span class="text-danger">*</span>
			</div>
			<div class="row">
				<div class="col-md-6 mb-3">
					<div class="input-group">
						<span class="input-group-text"><i class="fa fa-user"></i></span> <input
							type="text" class="form-control" name="username"
							placeholder="Tên đăng nhập" required>
					</div>
				</div>
				<div class="col-md-6 mb-3">
					<div class="input-group">
						<span class="input-group-text"><i class="fa fa-envelope"></i></span>
						<input type="email" class="form-control" name="email"
							placeholder="Email" required>
					</div>
				</div>
			</div>
			<div class="mb-3">
				<div class="input-group">
					<span class="input-group-text"><i class="fa fa-lock"></i></span> <input
						type="password" class="form-control" name="password"
						placeholder="Mật khẩu" required>
				</div>
			</div>

			<div class="section-label mt-3">Thông tin giao hàng (Tùy chọn)</div>
			<div class="row">
				<div class="col-md-6 mb-3">
					<div class="input-group">
						<span class="input-group-text"><i class="fa fa-id-card"></i></span>
						<input type="text" class="form-control" name="fullname"
							placeholder="Họ và tên">
					</div>
				</div>
				<div class="col-md-6 mb-3">
					<div class="input-group">
						<span class="input-group-text"><i class="fa fa-phone"></i></span>
						<input type="text" class="form-control" name="phone"
							placeholder="Số điện thoại">
					</div>
				</div>
			</div>

			<button type="submit" class="btn btn-primary w-100">Tạo Tài
				Khoản</button>

			<div class="mt-4 text-center">
				<p class="mb-0 text-muted small">
					Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng
						nhập</a>
				</p>
			</div>
		</form>
	</div>
</body>
</html>
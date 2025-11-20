<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Đăng Nhập - Đặc Sản Việt</title>

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
	--primary-color: #b71c1c; /* Đỏ đô */
	--secondary-color: #ffb300; /* Vàng */
}

body {
	/* Ảnh nền đồ ăn mờ */
	background: linear-gradient(rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0.6)),
		url('https://images.unsplash.com/photo-1555939594-58d7cb561ad1?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80');
	background-size: cover;
	background-position: center;
	min-height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
	font-family: 'Roboto', sans-serif;
}

.login-container {
	background: rgba(255, 255, 255, 0.95); /* Nền trắng hơi trong suốt */
	padding: 30px 30px 40px;
	border-radius: 15px;
	box-shadow: 0 10px 25px rgba(0, 0, 0, 0.5);
	width: 90%;
	max-width: 420px;
	border-top: 5px solid var(--primary-color);
	text-align: center; /* Căn giữa nội dung container */
}

/* CSS CHO LOGO MỚI */
.logo-img {
	max-width: 180px; /* Điều chỉnh kích thước logo */
	height: auto;
	margin-bottom: 5px;
	display: inline-block;
}

h2 {
	color: var(--primary-color);
	margin-bottom: 10px;
	font-family: 'Dancing Script', cursive;
	font-size: 2.5rem;
	font-weight: 700;
}

.sub-title {
	color: #666;
	font-size: 0.9rem;
	margin-bottom: 30px;
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
	background-color: var(--primary-color);
	border: none;
	width: 100%;
	margin-top: 20px;
	padding: 12px;
	font-weight: 700;
	text-transform: uppercase;
	transition: all 0.3s;
}

.btn-primary:hover {
	background-color: #d32f2f;
	transform: translateY(-2px);
	box-shadow: 0 5px 15px rgba(183, 28, 28, 0.3);
}

a {
	text-decoration: none;
	color: var(--primary-color);
	font-weight: 500;
}

a:hover {
	text-decoration: underline;
	color: #d32f2f;
}

/* Căn chỉnh text-start cho các input để form không bị căn giữa */
.form-wrapper {
	text-align: left;
}
</style>
</head>
<body>
	<div class="login-container">

		<img src="https://files.catbox.moe/c373f9.webp" alt="Logo Đặc Sản Việt"
			class="logo-img">

		<h2>Đăng Nhập</h2>
		<p class="sub-title">Chào mừng bạn đến với Đặc Sản Việt</p>

		<form action="${pageContext.request.contextPath}/login" method="post"
			class="form-wrapper">

			<c:if test="${not empty alert}">
				<div
					class="alert alert-warning py-2 text-center border-0 bg-warning-subtle text-warning-emphasis"
					role="alert">
					<i class="fa-solid fa-circle-exclamation me-1"></i> ${alert}
				</div>
			</c:if>

			<div class="mb-3">
				<div class="input-group">
					<span class="input-group-text"><i class="fa fa-user"></i></span> <input
						type="text" placeholder="Tài khoản" name="username"
						class="form-control" required>
				</div>
			</div>

			<div class="mb-3">
				<div class="input-group">
					<span class="input-group-text"><i class="fa fa-lock"></i></span> <input
						type="password" placeholder="Mật khẩu" name="password"
						class="form-control" required>
				</div>
			</div>

			<div class="d-flex justify-content-between align-items-center mb-3">
				<div class="form-check">
					<input type="checkbox" name="remember" class="form-check-input"
						id="rememberMe"> <label
						class="form-check-label text-muted small" for="rememberMe">Ghi
						nhớ tôi</label>
				</div>
				<a href="${pageContext.request.contextPath}/forgot" class="small">Quên
					mật khẩu?</a>
			</div>

			<button type="submit" class="btn btn-primary">Đăng Nhập Ngay</button>

			<div class="text-center mt-4">
				<p class="text-muted small mb-0">
					Chưa có tài khoản? <a
						href="${pageContext.request.contextPath}/register" class="fw-bold">Đăng
						ký tại đây</a>
				</p>
			</div>
		</form>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
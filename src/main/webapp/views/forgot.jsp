<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Khôi Phục Mật Khẩu</title>
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
		url('https://images.unsplash.com/photo-1563227812-0ea4c22e6cc8?ixlib=rb-4.0.3&auto=format&fit=crop&w=1920&q=80');
	background-size: cover;
	min-height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
	font-family: 'Roboto', sans-serif;
}

.forgot-container {
	background: rgba(255, 255, 255, 0.95);
	padding: 40px;
	width: 100%;
	max-width: 400px;
	border-radius: 15px;
	box-shadow: 0 10px 25px rgba(0, 0, 0, 0.5);
	text-align: center;
	border-top: 5px solid var(--primary-color);
}

h3 {
	color: var(--primary-color);
	font-family: 'Dancing Script', cursive;
	font-size: 2.2rem;
	margin-bottom: 10px;
}

.btn-primary {
	background-color: var(--primary-color);
	border: none;
	padding: 12px;
	font-weight: 700;
	width: 100%;
	margin-top: 10px;
	text-transform: uppercase;
}

.btn-primary:hover {
	background-color: #d32f2f;
}

.input-group-text {
	color: var(--primary-color);
	background-color: #fff3e0;
	border: 1px solid #ced4da;
	border-right: none;
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
</style>
</head>
<body>
	<div class="forgot-container">
		<div class="text-center mb-3 text-warning" style="font-size: 3rem;">
			<i class="fa-solid fa-lock-open"></i>
		</div>
		<h3>Quên Mật Khẩu?</h3>
		<p class="text-muted small mb-4">Đừng lo! Nhập email hoặc tên đăng
			nhập để lấy lại mật khẩu.</p>

		<c:if test="${not empty alert}">
			<div
				class="alert alert-warning py-2 small border-0 bg-warning-subtle text-warning-emphasis">${alert}</div>
		</c:if>
		<c:if test="${not empty success}">
			<div
				class="alert alert-success py-2 small border-0 bg-success-subtle text-success-emphasis">${success}</div>
		</c:if>

		<form method="post" action="${pageContext.request.contextPath}/forgot">
			<div class="mb-3 text-start">
				<div class="input-group">
					<span class="input-group-text"><i
						class="fa-solid fa-envelope"></i></span> <input type="text"
						class="form-control" name="keyword"
						placeholder="Email hoặc Username" required>
				</div>
			</div>
			<button class="btn btn-primary">Gửi Yêu Cầu</button>
			<a href="${pageContext.request.contextPath}/login"
				class="btn btn-link mt-3 text-decoration-none text-muted"> <i
				class="fa-solid fa-arrow-left me-1"></i> Quay lại đăng nhập
			</a>
		</form>
	</div>
</body>
</html>
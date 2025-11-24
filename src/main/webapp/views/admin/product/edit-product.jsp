<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Cập Nhật Sản Phẩm</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
:root {
	--primary-color: #b71c1c;
	--secondary-color: #ff6f00;
}

body {
	background-color: #fff8e1;
	height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
	font-family: 'Segoe UI', sans-serif;
}

.form-container {
	background: white;
	padding: 30px 40px;
	border-radius: 10px;
	width: 600px;
	box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
	border-top: 4px solid var(--secondary-color);
}

h3 {
	color: var(--secondary-color);
	font-weight: 700;
	text-align: center;
	margin-bottom: 25px;
}

.form-label {
	font-weight: 600;
	color: #555;
}

.btn-submit {
	background-color: var(--secondary-color);
	color: white;
	border: none;
	padding: 10px 30px;
	font-weight: 600;
}

.btn-submit:hover {
	background-color: #e65100;
	color: white;
}

#preview-img {
	max-width: 100px;
	border-radius: 5px;
	margin-top: 10px;
	border: 1px solid #ddd;
}
</style>
</head>
<body>
	<div class="form-container">
		<h3>Cập Nhật Sản Phẩm</h3>

		<c:set var="productBase" value="${empty productBasePath ? '/admin/product' : productBasePath}" />
		<form action="${pageContext.request.contextPath}${productBase}/edit"
			method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${product.id}"> <input
				type="hidden" name="oldImage" value="${product.image}">

			<div class="row mb-3">
				<div class="col-md-8">
					<label class="form-label">Tên đặc sản</label> <input type="text"
						class="form-control" name="name" value="${product.name}" required>
				</div>
				<div class="col-md-4">
					<label class="form-label">Giá (VNĐ)</label> <input type="number"
						class="form-control" name="price" value="${product.price}"
						required>
				</div>
			</div>

			<div class="mb-3">
				<label class="form-label">Danh mục</label> <select
					class="form-select" name="categoryId" required>
					<c:forEach items="${cateList}" var="c">
						<option value="${c.id}"
							${c.id == product.categoryId ? 'selected' : ''}>${c.cateName}</option>
					</c:forEach>
				</select>
			</div>

			<div class="mb-4">
				<label class="form-label">Hình ảnh</label> <input type="file"
					class="form-control" id="imgInput" name="image">
				<c:if test="${not empty product.image}">
					<img src="${pageContext.request.contextPath}/${product.image}"
						id="preview-img">
				</c:if>
			</div>

			<div class="d-flex justify-content-end gap-2">
				<a href="${pageContext.request.contextPath}${productBase}/list"
					class="btn btn-secondary">Hủy</a>
				<button type="submit" class="btn btn-submit">Lưu Thay Đổi</button>
			</div>
		</form>
	</div>
</body>
</html>
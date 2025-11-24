<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Quản Lý Danh Mục</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<style>
:root {
	--primary-color: #b71c1c;
}

body {
	background-color: #fff8e1;
	font-family: 'Segoe UI', sans-serif;
	padding: 40px 0;
}

.table-container {
	background: white;
	border-radius: 10px;
	box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
	padding: 30px;
	width: 90vw;
	max-width: 1200px;
	margin: 0 auto;
	border-top: 4px solid var(--primary-color);
}

h2 {
	color: var(--primary-color);
	font-weight: 700;
}

.btn-add {
	background-color: var(--primary-color);
	color: white;
	font-weight: 600;
	border: none;
}

.btn-add:hover {
	background-color: #d32f2f;
	color: white;
}

.table thead {
	background-color: #f8f9fa;
	color: var(--primary-color);
}

.table img {
	width: 80px;
	height: 80px;
	object-fit: cover;
	border-radius: 8px;
	border: 1px solid #eee;
}

.action-link {
	text-decoration: none;
	font-weight: 600;
	margin: 0 5px;
}

.edit-link {
	color: #ff8f00;
}

.delete-link {
	color: #d32f2f;
}
</style>
</head>
<body>
	<div class="table-container">
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h2>
				<i class="fa-solid fa-layer-group me-2"></i>Danh Mục Sản Phẩm
			</h2>
			<div>
				<a href="${pageContext.request.contextPath}/waiting"
					class="btn btn-outline-secondary me-2"> <i
					class="fa-solid fa-arrow-left"></i> Quay lại
				</a> <a href="${pageContext.request.contextPath}${categoryBasePath}/add"
					class="btn btn-add"> <i class="fa-solid fa-plus me-1"></i> Thêm
					Mới
				</a>
			</div>
		</div>

		<div class="table-responsive">
			<table class="table table-hover align-middle">
				<thead>
					<tr>
						<th>ID</th>
						<th>Hình Ảnh</th>
						<th>Tên Danh Mục</th>
						<c:if test="${showOwnerColumn}">
							<th>Người Tạo</th>
						</c:if>
						<th class="text-center" style="width: 220px;">Thao Tác</th>
					</tr>
				</thead>
				<tbody>
    <c:forEach items="${cateList}" var="cate">
        <tr>
            <td>${cate.id}</td>
            <td>
                <c:if test="${not empty cate.image}">
                    <img src="${pageContext.request.contextPath}/${cate.image}" alt="Img" />
                </c:if> 
                <c:if test="${empty cate.image}">
                    <span class="text-muted">No Image</span>
                </c:if>
            </td>
            <td class="fw-bold text-dark">${cate.cateName}</td>
			<c:if test="${showOwnerColumn}">
				<td>${empty cate.ownerName ? '---' : cate.ownerName}</td>
			</c:if>

            <td class="text-end">
 
                <%-- 1. NÚT SỬA --%> 
                <a href="${pageContext.request.contextPath}${categoryBasePath}/edit?id=${cate.id}"
                   class="btn btn-sm btn-warning text-white me-2"
                   title="Sửa danh mục"> 
                    <i class="fa-solid fa-pen-to-square"></i>
                </a> 
                
                <%-- 2. NÚT XÓA (Nút cuối cùng không cần me-2) --%> 
                <a href="${pageContext.request.contextPath}${categoryBasePath}/delete?id=${cate.id}"
                   class="btn btn-sm btn-danger me-2"
                   onclick="return confirm('Bạn có chắc chắn muốn xóa danh mục này?');"
                   title="Xóa danh mục"> 
                    <i class="fa-solid fa-trash"></i>
                </a> 
                <%-- 3. NÚT XEM SP --%>
                <a href="${pageContext.request.contextPath}${productListPath}?cid=${cate.id}"
                   class="btn btn-sm btn-info text-white me-2"
                   title="Xem các sản phẩm"> 
                    <i class="fa-solid fa-eye"></i>
                </a>
                
            </td>
        </tr>
    </c:forEach>
</tbody>
			</table>
			<c:if test="${empty cateList}">
				<p class="text-center text-muted mt-3">Chưa có dữ liệu.</p>
			</c:if>
		</div>
	</div>
</body>
</html>
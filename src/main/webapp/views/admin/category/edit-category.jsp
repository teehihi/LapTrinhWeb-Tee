<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Sửa Danh Mục</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    :root { --primary-color: #b71c1c; }
    body { 
        background-color: #fff8e1; 
        height: 100vh; 
        display: flex; 
        justify-content: center; 
        align-items: center; 
        font-family: 'Segoe UI', sans-serif; 
    }
    
    /* SỬA LẠI TÊN CLASS CHO KHỚP VỚI HTML */
    .form-container { 
        background: white; 
        padding: 40px; 
        border-radius: 10px; 
        box-shadow: 0 5px 20px rgba(0,0,0,0.1); 
        width: 450px; /* Giới hạn chiều rộng form */
        border-top: 4px solid var(--primary-color);
    }

    h3 { 
        color: var(--primary-color); 
        font-weight: 700; 
        text-align: center; 
        margin-bottom: 25px; 
    }
    
    /* THÊM CSS ĐỂ GIỚI HẠN ẢNH KHÔNG BỊ TRÀN */
    .current-img {
        width: 100%;           /* Chiều rộng bằng 100% khung chứa */
        height: 200px;         /* Chiều cao cố định */
        object-fit: cover;     /* Cắt ảnh vừa vặn, không bị méo */
        border-radius: 8px;
        border: 1px solid #ddd;
        margin-bottom: 15px;
    }

    .btn-primary { background-color: var(--primary-color); border: none; width: 100%; padding: 10px; font-weight: 600; }
    .btn-primary:hover { background-color: #d32f2f; }
    
    .btn-secondary { width: 100%; margin-top: 10px; background-color: #f1f1f1; color: #333; border: none; font-weight: 500; text-decoration: none; display: block; text-align: center; }
    .btn-secondary:hover { background-color: #e0e0e0; color: #000; }
</style>
</head>
<body>
    <div class="form-container">
    	<c:set var="formBase" value="${empty categoryBasePath ? '/admin/category' : categoryBasePath}" />
        <h3>Sửa Danh Mục</h3>
        
        <form action="${pageContext.request.contextPath}${formBase}/edit" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${category.id}">

            <div class="mb-3">
                <label class="form-label fw-bold">Tên danh mục:</label> 
                <input type="text" class="form-control" name="name" value="${category.cateName}" required />
            </div>

            <div class="mb-3">
                <label class="form-label d-block fw-bold">Ảnh hiện tại:</label>
                <c:if test="${not empty category.image}">
                    <img src="${pageContext.request.contextPath}/${category.image}" class="current-img" alt="Old Image" />
                </c:if>
            </div>

            <div class="mb-4">
                <label class="form-label fw-bold">Chọn ảnh mới (nếu muốn thay đổi):</label> 
                <input type="file" class="form-control" name="icon" />
            </div>

            <div>
                <button type="submit" class="btn btn-primary">Cập nhật</button>
                <a href="${pageContext.request.contextPath}${formBase}/list" class="btn btn-secondary">Hủy</a>
            </div>
        </form>
    </div>
</body>
</html>
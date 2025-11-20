<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Thêm Sản Phẩm</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    :root { --secondary-color: #ff6f00; } /* Màu cam đậm */

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
        box-shadow: 0 5px 20px rgba(0,0,0,0.1); 
        border-top: 4px solid var(--secondary-color);
        max-height: 95vh; /* Đề phòng màn hình nhỏ */
        overflow-y: auto; /* Cho phép cuộn nếu form dài */
    }

    /* SỬA MÀU TIÊU ĐỀ */
    h3 { 
        color: var(--secondary-color); 
        font-weight: 700; 
        text-align: center; 
        margin-bottom: 25px; 
    }

    .form-label { font-weight: 600; color: #555; }

    /* CSS CHO ẢNH PREVIEW */
    #imagePreview { 
        display: none; 
        margin-top: 10px; 
        width: 100%;           /* Chiều rộng tối đa theo khung */
        max-width: 200px;      /* Không cho quá to */
        height: auto;
        border-radius: 8px; 
        border: 1px solid #ddd;
        object-fit: contain;
    }

    /* Nút Lưu màu Cam */
    .btn-submit { background-color: var(--secondary-color); color: white; border: none; padding: 10px 30px; font-weight: 600; }
    .btn-submit:hover { background-color: #e65100; color: white; }

    /* Nút Hủy màu Xám */
    .btn-secondary { background-color: #666; border: none; padding: 10px 20px; }
    .btn-secondary:hover { background-color: #444; }
</style>
</head>
<body>
    <div class="form-container">
        <h3>Thêm Sản Phẩm Mới</h3>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger text-center">${errorMessage}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/admin/product/add" method="post" enctype="multipart/form-data">
            
            <div class="mb-3">
                <label class="form-label">Tên sản phẩm</label>
                <input type="text" class="form-control" name="name" required placeholder="Ví dụ: Kẹo dừa Bến Tre">
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Giá bán (VNĐ)</label>
                    <input type="number" step="0.01" class="form-control" name="price" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Danh mục</label>
                    <select class="form-select" name="categoryId" required>
                        <option value="" disabled selected>-- Chọn danh mục --</option>
                        <c:forEach items="${cateList}" var="c">
                            <option value="${c.id}">${c.cateName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="mb-4">
                <label class="form-label">Ảnh sản phẩm</label>
                <input type="file" class="form-control" id="imageInput" name="image" accept="image/*">
                <div class="text-center">
                    <img id="imagePreview" src="#" alt="Preview Image" />
                </div>
            </div>

            <div class="d-flex justify-content-end gap-2">
                <a href="${pageContext.request.contextPath}/admin/product/list" class="btn btn-secondary">Hủy</a>
                <button type="submit" class="btn btn-submit">Lưu Sản Phẩm</button>
            </div>
        </form>
    </div>

    <script>
        const imageInput = document.getElementById('imageInput');
        const imagePreview = document.getElementById('imagePreview');

        imageInput.onchange = evt => {
            const [file] = imageInput.files;
            if (file) {
                imagePreview.src = URL.createObjectURL(file);
                imagePreview.style.display = 'block'; // Hiển thị ảnh khi đã chọn
            }
        };
    </script>
</body>
</html>
# 🛒 Đặc Sản Việt - LapTrinhWebJPA

## 👑 Giới Thiệu Dự Án
**Đặc Sản Việt** là một hệ thống thương mại điện tử (E-commerce) được xây dựng bằng **Java (Jakarta EE)** kết hợp với **Hibernate (JPA)**. Dự án tập trung vào việc bán các sản phẩm đặc sản vùng miền Việt Nam.  
Điểm đặc biệt là hệ thống sử dụng cơ chế **JPA (Java Persistence API)** để mapping dữ liệu, giúp thao tác với **SQL Server** trở nên mượt mà và hướng đối tượng hơn.

### 🎯 Mục tiêu chính của dự án:
- Áp dụng kiến trúc **MVC (Model-View-Controller)** chuẩn trong lập trình Web Java.
- Làm chủ công nghệ **Hibernate ORM** để xử lý dữ liệu phức tạp.
- Xây dựng hệ thống phân quyền chặt chẽ: **User - Manager - Admin**.

---

## 💻 Chức Năng & Phân Quyền

### 🧍‍♂️ Vai trò User (Khách hàng & Người bán nhỏ)
- **Mua sắm:** Xem danh sách sản phẩm, xem chi tiết theo danh mục.
- **Tài khoản:** Đăng ký, Đăng nhập (có Remember Me 🍪), Quên mật khẩu.
- **Quản lý cá nhân:** - Tự tạo danh mục sản phẩm riêng (My Categories).
  - Đăng bán sản phẩm của chính mình (My Products).
  - *Logic này cho phép User hoạt động như một người bán hàng (Seller).*

### 🧑‍💼 Vai trò Manager (Quản lý)
- **Dashboard riêng:** Thống kê sơ bộ số lượng.
- **Quản lý tài nguyên:** Chỉ được phép Sửa/Xóa các danh mục và sản phẩm do **chính mình tạo ra**.
- Không thể can thiệp vào dữ liệu của người khác.

### 👮‍♂️ Vai trò Admin (Quản trị viên)
- **Quyền lực tối cao:** Xem, Sửa, Xóa tất cả dữ liệu trong hệ thống.
- **Dashboard tổng:** Thống kê toàn bộ hệ thống.
- Quản lý người dùng và phân quyền (thông qua Database).

---

## ✨ Công Nghệ Sử Dụng

| Lớp (Layer) | Công nghệ / Thư viện |
|---|---|
| **Backend** | Java 21, Jakarta Servlet 6.0 |
| **ORM / Database** | Hibernate Core 7.1.8, Microsoft SQL Server |
| **Frontend** | JSP, JSTL, Bootstrap 5.3, FontAwesome 6.5 |
| **Server** | Apache Tomcat 10.1 (Hỗ trợ Jakarta EE) |
| **Build Tool** | Maven |
| **Tiện ích** | Lombok, Filter (Security) |

---

## 🗺️ Cấu Trúc Dự Án

```bash
📁 LapTrinhWebJPA/
├── 📁 src/main/java/
│   ├── 📁 config/             # Cấu hình JPA (EntityManager), Hằng số (Constant)
│   ├── 📁 controller/         # Servlet điều hướng (Auth, Product, Category, Home)
│   ├── 📁 dao/                # Data Access Object (Giao tiếp DB qua JPA)
│   ├── 📁 entity/             # Các class ánh xạ bảng (User, Product, Category)
│   ├── 📁 filter/             # Kiểm soát quyền truy cập (RoleAuthorization)
│   ├── 📁 model/              # DTO (Data Transfer Object)
│   └── 📁 service/            # Xử lý nghiệp vụ logic (Business Logic Layer)
│
├── 📁 src/main/resources/
│   └── 📁 META-INF/
│       └── persistence.xml    # ⚙️ File cấu hình kết nối SQL Server quan trọng nhất
│
└── 📁 src/main/webapp/
    ├── 📁 views/              # Giao diện JSP (chia theo admin, user, manager)
    ├── 📁 uploads/            # Thư mục lưu ảnh tạm
    └── 📁 WEB-INF/            # Cấu hình web.xml
```

# ⚙️ Cách Cài Đặt & Chạy

## 🔧 Yêu cầu

-   Java **JDK 21** trở lên
-   Apache **Tomcat 10.1** (bắt buộc vì dùng \*jakarta.\*\*\*)
-   SQL Server đã bật **TCP/IP port 1433**

------------------------------------------------------------------------

# 🚀 Các bước thực hiện

## 1️⃣ Tạo Database

-   Mở SQL Server, tạo database tên **LapTrinhWebJPA**
-   Không cần tạo bảng --- Hibernate sẽ tự sinh bảng (hbm2ddl.auto =
    `update`)

------------------------------------------------------------------------

## 2️⃣ Cấu hình kết nối

Mở file:

    src/main/resources/META-INF/persistence.xml

Sửa user/password:

``` xml
<property name="jakarta.persistence.jdbc.user" value="sa" />
<property name="jakarta.persistence.jdbc.password" value="YOUR_PASSWORD" />
```

------------------------------------------------------------------------

## 3️⃣ Chạy dự án

-   Import vào Eclipse dạng **Maven Project**
-   Chuột phải Project → **Run As → Run on Server**
-   Chọn **Tomcat 10.1**\
-   Done! 🥳

------------------------------------------------------------------------

# 💡 Lưu Ý Quan Trọng

### 📁 Upload Ảnh

Tomcat lưu ảnh vào thư mục **getRealPath()** → dọn server sẽ mất ảnh.\
Muốn lưu vĩnh viễn → dùng **đường dẫn tuyệt đối**.

### 👑 Tài khoản Admin

-   Đăng ký tài khoản bình thường
-   Vào Database sửa cột **Role = 3** để thành Admin

------------------------------------------------------------------------

# 🧑‍💻 Tác Giả

**Tee -- Developer & Designer**

GitHub: https://github.com/teehihi

🔗 **Know more about Tee:** [linktr.ee/nkqt.tee](https://linktr.ee/nkqt.tee)

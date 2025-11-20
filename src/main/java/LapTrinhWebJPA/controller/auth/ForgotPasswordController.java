package LapTrinhWebJPA.controller.auth;

import java.io.IOException;

// Import Model và Service mới
import LapTrinhWebJPA.model.UserModel;
import LapTrinhWebJPA.service.UserService;
import LapTrinhWebJPA.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/forgot")
public class ForgotPasswordController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("views/forgot.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Thiết lập encoding
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		String keyword = req.getParameter("keyword");
		String alertMsg = "";
		String successMsg = "";

		if (keyword == null || keyword.isEmpty()) {
			alertMsg = "Vui lòng nhập email hoặc tên đăng nhập";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher("views/forgot.jsp").forward(req, resp);
			return;
		}

		UserService service = new UserServiceImpl();
		// Hàm này giờ trả về UserModel
		UserModel user = service.findByUsernameOrEmail(keyword);

		if (user == null) {
			alertMsg = "Không tìm thấy tài khoản phù hợp";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher("views/forgot.jsp").forward(req, resp);
		} else {
			// Code demo: hiển thị mật khẩu ngay giao diện
			// Thực tế nên gửi email reset password
			successMsg = "Mật khẩu của bạn là: " + user.getPassWord();

			req.setAttribute("success", successMsg);
			req.getRequestDispatcher("views/forgot.jsp").forward(req, resp);
		}
	}
}
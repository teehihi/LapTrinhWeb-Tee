package LapTrinhWebJPA.controller.auth;

import java.io.IOException;

// Giả sử Constant nằm ở package config
import LapTrinhWebJPA.config.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/logout")
public class LogoutController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 1. XÓA SESSION
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		// 2. XÓA COOKIE REMEMBER ME
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(Constant.COOKIE_REMEMBER)) {
					cookie.setMaxAge(0); // Set thời gian sống = 0 để xóa
					cookie.setPath("/"); // Đảm bảo path trùng với lúc tạo
					resp.addCookie(cookie);
				}
			}
		}

		// 3. Chuyển về trang login
		resp.sendRedirect(req.getContextPath() + "/login");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
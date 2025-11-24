package LapTrinhWebJPA.controller.auth;

import java.io.IOException;

import LapTrinhWebJPA.config.Constant;
import LapTrinhWebJPA.model.UserModel;
import LapTrinhWebJPA.service.UserService;
import LapTrinhWebJPA.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute(Constant.SESSION_ACCOUNT) != null) {
			UserModel existing = (UserModel) session.getAttribute(Constant.SESSION_ACCOUNT);
			resp.sendRedirect(req.getContextPath() + resolveHomePath(existing.getRole()));
			return;
		}

		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(Constant.COOKIE_REMEMBER)) {
					UserModel rememberedUser = new UserServiceImpl().get(cookie.getValue());
					if (rememberedUser != null) {
						session = req.getSession(true);
						session.setAttribute(Constant.SESSION_ACCOUNT, rememberedUser);
						resp.sendRedirect(req.getContextPath() + resolveHomePath(rememberedUser.getRole()));
						return;
					}
				}
			}
		}
		req.getRequestDispatcher("views/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		boolean isRememberMe = "on".equals(req.getParameter("remember"));
		String alertMsg = "";

		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			alertMsg = "Tài khoản hoặc mật khẩu không được rỗng";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher("views/login.jsp").forward(req, resp);
			return;
		}

		UserService service = new UserServiceImpl();
		// Trả về UserModel
		UserModel user = service.login(username, password);

		if (user != null) {
			HttpSession session = req.getSession(true);
			session.setAttribute(Constant.SESSION_ACCOUNT, user);

			if (isRememberMe) {
				saveRememberMe(resp, username);
			}
			resp.sendRedirect(req.getContextPath() + resolveHomePath(user.getRole()));
		} else {
			alertMsg = "Tài khoản hoặc mật khẩu không đúng";
			req.setAttribute("alert", alertMsg);
			req.getRequestDispatcher("views/login.jsp").forward(req, resp);
		}
	}

	private void saveRememberMe(HttpServletResponse response, String username) {
		Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);
		cookie.setMaxAge(30 * 60);
		response.addCookie(cookie);
	}

	private String resolveHomePath(int role) {
		return switch (role) {
		case Constant.ROLE_MANAGER -> "/manager/home";
		case Constant.ROLE_ADMIN -> "/admin/home";
		default -> "/user/home";
		};
	}
}
package LapTrinhWebJPA.controller.auth;

import java.io.IOException;

import LapTrinhWebJPA.config.Constant;
import LapTrinhWebJPA.model.UserModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/waiting")
public class WaitingController extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		if (session != null && session.getAttribute(Constant.SESSION_ACCOUNT) != null) {
			UserModel u = (UserModel) session.getAttribute(Constant.SESSION_ACCOUNT);
			String target = switch (u.getRole()) {
			case Constant.ROLE_MANAGER -> "/manager/home";
			case Constant.ROLE_ADMIN -> "/admin/home";
			default -> "/user/home";
			};
			resp.sendRedirect(req.getContextPath() + target);
		} else {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
	}
}
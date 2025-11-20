package LapTrinhWebJPA.controller.auth;

import java.io.IOException;

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

		if (session != null && session.getAttribute("account") != null) {
			UserModel u = (UserModel) session.getAttribute("account");
			req.setAttribute("username", u.getUserName());
			resp.sendRedirect(req.getContextPath() + "/views/home.jsp");
//			if (u.getRole() == 1) {
//				resp.sendRedirect(req.getContextPath() + "/views/admin/index.jsp");
//			} else {
//				resp.sendRedirect(req.getContextPath() + "/views/home.jsp");
//			}
		} else {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
	}
}
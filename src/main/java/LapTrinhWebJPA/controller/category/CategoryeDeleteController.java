package LapTrinhWebJPA.controller.category;

import java.io.IOException;

import LapTrinhWebJPA.config.Constant;
import LapTrinhWebJPA.model.UserModel;
import LapTrinhWebJPA.service.CategoryService;
import LapTrinhWebJPA.service.impl.CategoryServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/admin/category/delete", "/manager/category/delete", "/user/category/delete" })
public class CategoryeDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CategoryService cateService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		if (id == null) {
			resp.sendRedirect(req.getContextPath() + resolveBasePath(req) + "/list");
			return;
		}

		boolean managerArea = req.getServletPath().startsWith("/manager") || req.getServletPath().startsWith("/user");
		boolean deleted = true;
		if (managerArea) {
			HttpSession session = req.getSession(false);
			UserModel currentUser = session != null ? (UserModel) session.getAttribute(Constant.SESSION_ACCOUNT) : null;
			if (currentUser == null) {
				resp.sendRedirect(req.getContextPath() + "/login");
				return;
			}
			deleted = cateService.deleteByOwner(Integer.parseInt(id), currentUser.getId());
		} else {
			cateService.delete(Integer.parseInt(id));
		}

		String redirect = req.getContextPath() + resolveBasePath(req) + "/list";
		if (!deleted) {
			redirect += "?error=notfound";
		}
		resp.sendRedirect(redirect);
	}

	private String resolveBasePath(HttpServletRequest req) {
		String path = req.getServletPath();
		if (path.startsWith("/manager")) {
			return "/manager/category";
		}
		if (path.startsWith("/user")) {
			return "/user/category";
		}
		return "/admin/category";
	}
}
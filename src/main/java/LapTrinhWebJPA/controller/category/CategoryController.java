package LapTrinhWebJPA.controller.category;

import java.io.IOException;
import java.util.List;

import LapTrinhWebJPA.model.CategoryModel;
import LapTrinhWebJPA.service.CategoryService;
import LapTrinhWebJPA.service.impl.CategoryServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import LapTrinhWebJPA.config.Constant;
import LapTrinhWebJPA.model.UserModel;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/admin/category/list", "/manager/category/list", "/user/category/list" })
public class CategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CategoryService cateService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();
		boolean isManagerArea = servletPath.startsWith("/manager");
		boolean isUserArea = servletPath.startsWith("/user");
		boolean isAdminArea = servletPath.startsWith("/admin");

		List<CategoryModel> cateList;
		if (isManagerArea || isUserArea) {
			HttpSession session = req.getSession(false);
			UserModel currentUser = session != null ? (UserModel) session.getAttribute(Constant.SESSION_ACCOUNT) : null;
			if (currentUser == null) {
				resp.sendRedirect(req.getContextPath() + "/login");
				return;
			}
			cateList = cateService.getByOwner(currentUser.getId());
		} else {
			cateList = cateService.getAll();
		}

		String categoryBasePath = isAdminArea ? "/admin/category" : isManagerArea ? "/manager/category" : "/user/category";
		String productListPath = isAdminArea ? "/admin/product/list"
				: isManagerArea ? "/manager/product/list" : "/user/product/list";

		req.setAttribute("categoryBasePath", categoryBasePath);
		req.setAttribute("productListPath", productListPath);
		req.setAttribute("showOwnerColumn", isAdminArea);
		req.setAttribute("cateList", cateList);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/category/list-category.jsp");
		dispatcher.forward(req, resp);
	}
}
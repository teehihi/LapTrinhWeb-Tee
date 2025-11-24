package LapTrinhWebJPA.controller.home;

import java.io.IOException;
import java.util.List;

import LapTrinhWebJPA.config.Constant;
import LapTrinhWebJPA.model.CategoryModel;
import LapTrinhWebJPA.model.ProductModel;
import LapTrinhWebJPA.model.UserModel;
import LapTrinhWebJPA.service.CategoryService;
import LapTrinhWebJPA.service.ProductService;
import LapTrinhWebJPA.service.impl.CategoryServiceImpl;
import LapTrinhWebJPA.service.impl.ProductServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/manager/home")
public class ManagerHomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final CategoryService categoryService = new CategoryServiceImpl();
	private final ProductService productService = new ProductServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		UserModel currentUser = session != null ? (UserModel) session.getAttribute(Constant.SESSION_ACCOUNT) : null;
		if (currentUser == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		List<CategoryModel> categories = categoryService.getByOwner(currentUser.getId());
		List<ProductModel> products = productService.getByOwnerId(currentUser.getId());

		req.setAttribute("managerName", currentUser.getFullName());
		req.setAttribute("cateList", categories);
		req.setAttribute("categoryCount", categories.size());
		req.setAttribute("productCount", products.size());
		req.setAttribute("categoryLink", req.getContextPath() + "/manager/category/list");
		req.setAttribute("productLink", req.getContextPath() + "/manager/product/list");

		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/manager/home.jsp");
		dispatcher.forward(req, resp);
	}
}


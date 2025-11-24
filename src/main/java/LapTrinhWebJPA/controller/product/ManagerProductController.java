package LapTrinhWebJPA.controller.product;

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

@WebServlet(urlPatterns = { "/manager/product/list" })
public class ManagerProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ProductService productService = new ProductServiceImpl();
	private final CategoryService categoryService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		UserModel currentUser = session != null ? (UserModel) session.getAttribute(Constant.SESSION_ACCOUNT) : null;
		if (currentUser == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		List<ProductModel> productList = loadProductsForManager(req, currentUser);

		req.setAttribute("productList", productList);
		req.setAttribute("productBasePath", "/manager/product");
		req.setAttribute("productDashboardPath", "/manager/home");
		req.setAttribute("categoryListPath", "/manager/category/list");
		req.setAttribute("canManageProducts", false);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/product/list-product.jsp");
		dispatcher.forward(req, resp);
	}

	private List<ProductModel> loadProductsForManager(HttpServletRequest req, UserModel currentUser) {
		String categoryIdParam = req.getParameter("cid");
		if (categoryIdParam != null) {
			try {
				int categoryId = Integer.parseInt(categoryIdParam);
				CategoryModel ownedCategory = categoryService.getOwnedCategory(categoryId, currentUser.getId());
				if (ownedCategory != null) {
					return productService.getByCategoryId(categoryId);
				}
			} catch (NumberFormatException ignored) {
			}
		}
		return productService.getByOwnerId(currentUser.getId());
	}
}


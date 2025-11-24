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

@WebServlet(urlPatterns = { "/user/product/list" })
public class UserProductManageController extends HttpServlet {
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

		List<ProductModel> productList = loadProducts(req, currentUser);

		req.setAttribute("productList", productList);
		req.setAttribute("productBasePath", "/user/product");
		req.setAttribute("productDashboardPath", "/user/home");
		req.setAttribute("categoryListPath", "/user/category/list");
		req.setAttribute("canManageProducts", true);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/product/list-product.jsp");
		dispatcher.forward(req, resp);
	}

	private List<ProductModel> loadProducts(HttpServletRequest req, UserModel currentUser) {
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


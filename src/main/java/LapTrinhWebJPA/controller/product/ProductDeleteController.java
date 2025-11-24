package LapTrinhWebJPA.controller.product;

import java.io.File;
import java.io.IOException;

import LapTrinhWebJPA.config.Constant;
import LapTrinhWebJPA.model.CategoryModel;
import LapTrinhWebJPA.model.ProductModel;
import LapTrinhWebJPA.model.UserModel;
import LapTrinhWebJPA.service.CategoryService;
import LapTrinhWebJPA.service.ProductService;
import LapTrinhWebJPA.service.impl.CategoryServiceImpl;
import LapTrinhWebJPA.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/admin/product/delete", "/user/product/delete" })
public class ProductDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ProductService productService = new ProductServiceImpl();
	CategoryService categoryService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String idStr = req.getParameter("id");
			int id = Integer.parseInt(idStr);
			UserModel currentUser = null;
			if (isUserArea(req)) {
				currentUser = getCurrentUser(req, resp);
				if (currentUser == null) {
					return;
				}
			}

			// Lấy model để lấy đường dẫn ảnh
			ProductModel product = productService.getById(id);
			if (isUserArea(req)) {
				if (!ownsProduct(product, currentUser)) {
					resp.sendRedirect(req.getContextPath() + resolveBasePath(req) + "/list");
					return;
				}
			}

			if (product != null && product.getImage() != null && !product.getImage().isEmpty()) {
				String appPath = getServletContext().getRealPath("");
				String absoluteFilePath = appPath + File.separator + product.getImage();
				File imageFile = new File(absoluteFilePath);
				if (imageFile.exists()) {
					imageFile.delete();
				}
			}
			productService.delete(id);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		resp.sendRedirect(req.getContextPath() + resolveBasePath(req) + "/list");
	}

	private boolean isUserArea(HttpServletRequest req) {
		return req.getServletPath().startsWith("/user");
	}

	private String resolveBasePath(HttpServletRequest req) {
		return isUserArea(req) ? "/user/product" : "/admin/product";
	}

	private UserModel getCurrentUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession(false);
		UserModel currentUser = session != null ? (UserModel) session.getAttribute(Constant.SESSION_ACCOUNT) : null;
		if (currentUser == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
		}
		return currentUser;
	}

	private boolean ownsProduct(ProductModel product, UserModel user) {
		if (product == null || user == null) {
			return false;
		}
		CategoryModel category = categoryService.get(product.getCategoryId());
		return category != null && category.getOwnerId() != null && category.getOwnerId() == user.getId();
	}
}
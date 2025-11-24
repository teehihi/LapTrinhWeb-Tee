package LapTrinhWebJPA.controller.product;

import java.io.File;
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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet(urlPatterns = { "/admin/product/add", "/user/product/add" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class ProductAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ProductService productService = new ProductServiceImpl();
	CategoryService cateService = new CategoryServiceImpl();

	private String extractFormValue(HttpServletRequest request, String fieldName) throws IOException, ServletException {
		Part part = request.getPart(fieldName);
		if (part != null) {
			return new String(part.getInputStream().readAllBytes(), "UTF-8");
		}
		return null;
	}

	private String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				String fileName = content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
				if (!fileName.isEmpty()) {
					return fileName.substring(fileName.lastIndexOf(File.separator) + 1);
				}
			}
		}
		return null;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<CategoryModel> cateList;
		if (isUserArea(req)) {
			UserModel currentUser = getCurrentUser(req, resp);
			if (currentUser == null) {
				return;
			}
			cateList = cateService.getByOwner(currentUser.getId());
		} else {
			cateList = cateService.getAll();
		}
		req.setAttribute("cateList", cateList);
		req.setAttribute("productBasePath", resolveBasePath(req));
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/product/add-product.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");

		ProductModel product = new ProductModel();
		UserModel currentUser = null;
		if (isUserArea(req)) {
			currentUser = getCurrentUser(req, resp);
			if (currentUser == null) {
				return;
			}
		}

		try {
			String productName = extractFormValue(req, "name");
			if (productName != null)
				product.setName(productName);

			String priceStr = extractFormValue(req, "price");
			if (priceStr != null && !priceStr.isEmpty())
				product.setPrice(Double.parseDouble(priceStr));

			String categoryIdStr = extractFormValue(req, "categoryId");
			if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
				int categoryId = Integer.parseInt(categoryIdStr);
				if (isUserArea(req)) {
					CategoryModel owned = cateService.getOwnedCategory(categoryId, currentUser.getId());
					if (owned == null) {
						handleError(req, resp, "Danh mục không hợp lệ", currentUser);
						return;
					}
				}
				product.setCategoryId(categoryId);
			}

			Part imagePart = req.getPart("image");
			String relativeUploadDir = "/uploads/product";
			String absoluteUploadPath = getServletContext().getRealPath(relativeUploadDir);
			File uploadPath = new File(absoluteUploadPath);

			if (!uploadPath.exists())
				uploadPath.mkdirs();

			if (imagePart != null && imagePart.getSize() > 0) {
				String originalFileName = getFileName(imagePart);
				if (originalFileName != null && !originalFileName.isEmpty()) {
					int index = originalFileName.lastIndexOf(".");
					String ext = (index > 0) ? originalFileName.substring(index + 1) : "";
					String fileName = System.currentTimeMillis() + (ext.isEmpty() ? "" : "." + ext);
					File file = new File(absoluteUploadPath + File.separator + fileName);
					imagePart.write(file.getAbsolutePath());
					product.setImage("uploads/product/" + fileName);
				}
			}

			productService.insert(product);
			resp.sendRedirect(req.getContextPath() + resolveBasePath(req) + "/list");

		} catch (Exception e) {
			e.printStackTrace();
			UserModel currentUserFinal = currentUser;
			handleError(req, resp, "Lỗi: " + e.getMessage(), currentUserFinal);
		}
	}

	private void handleError(HttpServletRequest req, HttpServletResponse resp, String message, UserModel currentUser)
			throws ServletException, IOException {
		req.setAttribute("errorMessage", message);
		List<CategoryModel> cateList = isUserArea(req) && currentUser != null ? cateService.getByOwner(currentUser.getId())
				: cateService.getAll();
		req.setAttribute("cateList", cateList);
		req.setAttribute("productBasePath", resolveBasePath(req));
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/product/add-product.jsp");
		dispatcher.forward(req, resp);
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
}
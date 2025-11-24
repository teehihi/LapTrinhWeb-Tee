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

@WebServlet(urlPatterns = { "/admin/product/edit", "/user/product/edit" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class ProductEditController extends HttpServlet {
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
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			ProductModel product = productService.getById(id);
			UserModel currentUser = null;
			if (isUserArea(req)) {
				currentUser = getCurrentUser(req, resp);
				if (currentUser == null) {
					return;
				}
				if (!ownsProduct(product, currentUser)) {
					resp.sendRedirect(req.getContextPath() + resolveBasePath(req) + "/list");
					return;
				}
			}
			req.setAttribute("product", product);

			List<CategoryModel> cateList = isUserArea(req) && currentUser != null
					? cateService.getByOwner(currentUser.getId())
					: cateService.getAll();
			req.setAttribute("cateList", cateList);
			req.setAttribute("productBasePath", resolveBasePath(req));

			String cid = req.getParameter("cid");
			if (cid != null && !cid.isEmpty()) {
				req.setAttribute("current_cid", cid);
			}

			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/product/edit-product.jsp");
			dispatcher.forward(req, resp);

		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(req.getContextPath() + "/admin/product/list");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");

		ProductModel product = new ProductModel();

		try {
			UserModel currentUser = null;
			if (isUserArea(req)) {
				currentUser = getCurrentUser(req, resp);
				if (currentUser == null) {
					return;
				}
			}

			product.setId(Integer.parseInt(extractFormValue(req, "id")));
			product.setName(extractFormValue(req, "name"));
			product.setPrice(Double.parseDouble(extractFormValue(req, "price")));
			int categoryId = Integer.parseInt(extractFormValue(req, "categoryId"));
			if (isUserArea(req)) {
				CategoryModel owned = cateService.getOwnedCategory(categoryId, currentUser.getId());
				if (owned == null) {
					resp.sendRedirect(req.getContextPath() + resolveBasePath(req) + "/list?error=category");
					return;
				}
			}
			product.setCategoryId(categoryId);

			if (isUserArea(req)) {
				ProductModel existing = productService.getById(product.getId());
				if (!ownsProduct(existing, currentUser)) {
					resp.sendRedirect(req.getContextPath() + resolveBasePath(req) + "/list");
					return;
				}
			}

			String oldImage = extractFormValue(req, "oldImage");
			Part imagePart = req.getPart("image");

			if (imagePart != null && imagePart.getSize() > 0) {
				String relativeUploadDir = "/uploads/product";
				String absoluteUploadPath = getServletContext().getRealPath(relativeUploadDir);
				File uploadPath = new File(absoluteUploadPath);
				if (!uploadPath.exists())
					uploadPath.mkdirs();

				String originalFileName = getFileName(imagePart);
				if (originalFileName != null && !originalFileName.isEmpty()) {
					String fileName = System.currentTimeMillis() + "_" + originalFileName;
					File file = new File(absoluteUploadPath + File.separator + fileName);
					imagePart.write(file.getAbsolutePath());
					product.setImage("uploads/product/" + fileName);

					if (oldImage != null && !oldImage.isEmpty()) {
						String appPath = getServletContext().getRealPath("");
						File oldImageFile = new File(appPath + File.separator + oldImage);
						if (oldImageFile.exists())
							oldImageFile.delete();
					}
				} else {
					product.setImage(oldImage);
				}
			} else {
				product.setImage(oldImage);
			}

			productService.update(product);
			resp.sendRedirect(req.getContextPath() + resolveBasePath(req) + "/list");

		} catch (Exception e) {
			e.printStackTrace();
		}
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
		CategoryModel category = cateService.get(product.getCategoryId());
		return category != null && category.getOwnerId() != null && category.getOwnerId() == user.getId();
	}
}
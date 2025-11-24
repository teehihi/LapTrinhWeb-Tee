package LapTrinhWebJPA.controller.category;

import java.io.File;
import java.io.IOException;

import LapTrinhWebJPA.config.Constant;
import LapTrinhWebJPA.model.CategoryModel;
import LapTrinhWebJPA.model.UserModel;
import LapTrinhWebJPA.service.CategoryService;
import LapTrinhWebJPA.service.impl.CategoryServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet(urlPatterns = { "/admin/category/edit", "/manager/category/edit", "/user/category/edit" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class CategoryeEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
					return fileName;
				}
			}
		}
		return null;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String id = req.getParameter("id");
		if (id == null) {
			resp.sendRedirect(req.getContextPath() + resolveBasePath(req) + "/list");
			return;
		}

		boolean restrictedArea = isOwnershipArea(req);
		CategoryModel category;
		if (restrictedArea) {
			UserModel currentUser = getCurrentUser(req, resp);
			if (currentUser == null) {
				return;
			}
			category = cateService.getOwnedCategory(Integer.parseInt(id), currentUser.getId());
		} else {
			category = cateService.get(Integer.parseInt(id));
		}

		if (category == null) {
			resp.sendRedirect(req.getContextPath() + resolveBasePath(req) + "/list");
			return;
		}

		req.setAttribute("category", category);
		req.setAttribute("categoryBasePath", resolveBasePath(req));
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/category/edit-category.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");

		CategoryModel category = new CategoryModel();

		try {
			String idStr = extractFormValue(req, "id");
			String categoryName = extractFormValue(req, "name");

			if (idStr != null)
				category.setId(Integer.parseInt(idStr));
			if (categoryName != null)
				category.setCateName(categoryName);

			boolean managerArea = isOwnershipArea(req);
			UserModel currentUser = null;
			CategoryModel existingCategory;
			if (managerArea) {
				currentUser = getCurrentUser(req, resp);
				if (currentUser == null) {
					return;
				}
				existingCategory = cateService.getOwnedCategory(category.getId(), currentUser.getId());
			} else {
				existingCategory = cateService.get(category.getId());
			}

			if (existingCategory == null) {
				resp.sendRedirect(req.getContextPath() + resolveBasePath(req) + "/list");
				return;
			}

			category.setImage(existingCategory.getImage());

			Part iconPart = req.getPart("icon");
			String relativeUploadDir = "/uploads/category";
			String absoluteUploadPath = getServletContext().getRealPath(relativeUploadDir);
			File uploadPath = new File(absoluteUploadPath);

			if (!uploadPath.exists())
				uploadPath.mkdirs();

			if (iconPart != null && iconPart.getSize() > 0 && getFileName(iconPart) != null) {
				String originalFileName = getFileName(iconPart);

				if (existingCategory.getImage() != null && !existingCategory.getImage().isEmpty()) {
					File oldFile = new File(getServletContext().getRealPath("/") + existingCategory.getImage());
					if (oldFile.exists())
						oldFile.delete();
				}

				int index = originalFileName.lastIndexOf(".");
				String ext = (index > 0) ? originalFileName.substring(index + 1) : "";
				String fileName = System.currentTimeMillis() + (ext.isEmpty() ? "" : "." + ext);

				File file = new File(absoluteUploadPath + File.separator + fileName);
				iconPart.write(file.getAbsolutePath());
				category.setImage("uploads/category/" + fileName);
			}

			boolean updated;
			if (managerArea) {
				category.setOwnerId(currentUser.getId());
				updated = cateService.updateOwnedCategory(category, currentUser.getId());
			} else {
				updated = true;
				cateService.edit(category);
			}

			if (!updated) {
				resp.sendRedirect(req.getContextPath() + resolveBasePath(req) + "/list?error=notfound");
				return;
			}

			resp.sendRedirect(req.getContextPath() + resolveBasePath(req) + "/list");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isOwnershipArea(HttpServletRequest req) {
		return req.getServletPath().startsWith("/manager") || req.getServletPath().startsWith("/user");
	}

	private String resolveBasePath(HttpServletRequest req) {
		if (req.getServletPath().startsWith("/manager")) {
			return "/manager/category";
		}
		if (req.getServletPath().startsWith("/user")) {
			return "/user/category";
		}
		return "/admin/category";
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
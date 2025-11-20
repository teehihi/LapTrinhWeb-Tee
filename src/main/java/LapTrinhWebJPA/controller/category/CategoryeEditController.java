package LapTrinhWebJPA.controller.category;

import java.io.File;
import java.io.IOException;

import LapTrinhWebJPA.model.CategoryModel;
import LapTrinhWebJPA.service.CategoryService;
import LapTrinhWebJPA.service.impl.CategoryServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet(urlPatterns = { "/admin/category/edit" })
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
		// Lấy Model
		CategoryModel category = cateService.get(Integer.parseInt(id));
		req.setAttribute("category", category);
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

			CategoryModel existingCategory = cateService.get(category.getId());
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

			cateService.edit(category);
			resp.sendRedirect(req.getContextPath() + "/admin/category/list");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
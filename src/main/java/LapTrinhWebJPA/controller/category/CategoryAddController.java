package LapTrinhWebJPA.controller.category;

import java.io.File;
import java.io.IOException;

// Import mới
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

@WebServlet(urlPatterns = { "/admin/category/add" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class CategoryAddController extends HttpServlet {
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
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/category/add-category.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");

		// Dùng Model mới
		CategoryModel category = new CategoryModel();

		try {
			String categoryName = extractFormValue(req, "name");
			if (categoryName != null) {
				category.setCateName(categoryName);
			}

			Part iconPart = req.getPart("icon");
			String relativeUploadDir = "/uploads/category";
			String absoluteUploadPath = getServletContext().getRealPath(relativeUploadDir);
			File uploadPath = new File(absoluteUploadPath);

			if (!uploadPath.exists()) {
				uploadPath.mkdirs();
			}

			if (iconPart != null && iconPart.getSize() > 0) {
				String originalFileName = getFileName(iconPart);
				if (originalFileName != null && !originalFileName.isEmpty()) {
					int index = originalFileName.lastIndexOf(".");
					String ext = (index > 0) ? originalFileName.substring(index + 1) : "";
					String fileName = System.currentTimeMillis() + (ext.isEmpty() ? "" : "." + ext);
					File file = new File(absoluteUploadPath + File.separator + fileName);
					iconPart.write(file.getAbsolutePath());

					// Lưu đường dẫn
					category.setImage("uploads/category/" + fileName);
				}
			}

			cateService.insert(category);
			resp.sendRedirect(req.getContextPath() + "/admin/category/list");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
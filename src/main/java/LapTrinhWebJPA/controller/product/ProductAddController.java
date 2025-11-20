package LapTrinhWebJPA.controller.product;

import java.io.File;
import java.io.IOException;
import java.util.List;

// Import Model
import LapTrinhWebJPA.model.CategoryModel;
import LapTrinhWebJPA.model.ProductModel;
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
import jakarta.servlet.http.Part;

@WebServlet(urlPatterns = { "/admin/product/add" })
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
		List<CategoryModel> cateList = cateService.getAll();
		req.setAttribute("cateList", cateList);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/product/add-product.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");

		ProductModel product = new ProductModel();

		try {
			String productName = extractFormValue(req, "name");
			if (productName != null)
				product.setName(productName);

			String priceStr = extractFormValue(req, "price");
			if (priceStr != null && !priceStr.isEmpty())
				product.setPrice(Double.parseDouble(priceStr));

			String categoryIdStr = extractFormValue(req, "categoryId");
			if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
				product.setCategoryId(Integer.parseInt(categoryIdStr));
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
			resp.sendRedirect(req.getContextPath() + "/admin/product/list");

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("errorMessage", "Lỗi: " + e.getMessage());
			req.setAttribute("cateList", cateService.getAll());
			RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/product/add-product.jsp");
			dispatcher.forward(req, resp);
		}
	}
}
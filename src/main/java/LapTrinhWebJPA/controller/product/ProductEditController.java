package LapTrinhWebJPA.controller.product;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

@WebServlet(urlPatterns = { "/admin/product/edit" })
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
			req.setAttribute("product", product);

			List<CategoryModel> cateList = cateService.getAll();
			req.setAttribute("cateList", cateList);

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
			product.setId(Integer.parseInt(extractFormValue(req, "id")));
			product.setName(extractFormValue(req, "name"));
			product.setPrice(Double.parseDouble(extractFormValue(req, "price")));
			product.setCategoryId(Integer.parseInt(extractFormValue(req, "categoryId")));

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
			resp.sendRedirect(req.getContextPath() + "/admin/product/list");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
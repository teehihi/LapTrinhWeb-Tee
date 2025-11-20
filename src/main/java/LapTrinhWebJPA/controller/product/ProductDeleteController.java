package LapTrinhWebJPA.controller.product;

import java.io.File;
import java.io.IOException;

import LapTrinhWebJPA.model.ProductModel;
import LapTrinhWebJPA.service.ProductService;
import LapTrinhWebJPA.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/admin/product/delete" })
public class ProductDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ProductService productService = new ProductServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String idStr = req.getParameter("id");
			int id = Integer.parseInt(idStr);

			// Lấy model để lấy đường dẫn ảnh
			ProductModel product = productService.getById(id);

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
		resp.sendRedirect(req.getContextPath() + "/admin/product/list");
	}
}
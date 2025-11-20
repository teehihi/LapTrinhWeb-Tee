package LapTrinhWebJPA.controller.product;

import java.io.IOException;
import java.util.List;

import LapTrinhWebJPA.model.ProductModel;
import LapTrinhWebJPA.service.ProductService;
import LapTrinhWebJPA.service.impl.ProductServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/admin/product/list" })
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ProductService productService = new ProductServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String categoryIdParam = req.getParameter("cid");
		List<ProductModel> productList;

		if (categoryIdParam != null) {
			try {
				int categoryId = Integer.parseInt(categoryIdParam);
				productList = productService.getByCategoryId(categoryId);
			} catch (NumberFormatException e) {
				productList = productService.getAll();
			}
		} else {
			productList = productService.getAll();
		}

		req.setAttribute("productList", productList);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/product/list-product.jsp");
		dispatcher.forward(req, resp);
	}
}
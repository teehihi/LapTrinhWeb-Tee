package LapTrinhWebJPA.controller.product;

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
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/user/products")
public class UserProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final CategoryService categoryService = new CategoryServiceImpl();
	private final ProductService productService = new ProductServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<CategoryModel> categories = categoryService.getAll();
		req.setAttribute("cateList", categories);

		String categoryIdParam = req.getParameter("cid");
		CategoryModel selectedCategory = null;
		List<ProductModel> products;

		if (categoryIdParam != null) {
			try {
				int categoryId = Integer.parseInt(categoryIdParam);
				selectedCategory = categoryService.get(categoryId);
				if (selectedCategory != null) {
					products = productService.getByCategoryId(categoryId);
				} else {
					products = productService.getAll();
				}
			} catch (NumberFormatException e) {
				products = productService.getAll();
			}
		} else {
			products = productService.getAll();
		}

		req.setAttribute("selectedCategory", selectedCategory);
		req.setAttribute("productList", products);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/product-list.jsp");
		dispatcher.forward(req, resp);
	}
}


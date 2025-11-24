package LapTrinhWebJPA.controller.home;

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

@WebServlet(urlPatterns = "/user/home")
public class UserHomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final CategoryService categoryService = new CategoryServiceImpl();
	private final ProductService productService = new ProductServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<CategoryModel> categories = categoryService.getAll();
		List<ProductModel> products = productService.getAll();

		req.setAttribute("cateList", categories);
		req.setAttribute("productList", products);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/home.jsp");
		dispatcher.forward(req, resp);
	}
}


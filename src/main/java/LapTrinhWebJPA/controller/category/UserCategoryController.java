package LapTrinhWebJPA.controller.category;

import java.io.IOException;
import java.util.List;

import LapTrinhWebJPA.model.CategoryModel;
import LapTrinhWebJPA.service.CategoryService;
import LapTrinhWebJPA.service.impl.CategoryServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/user/categories")
public class UserCategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final CategoryService categoryService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<CategoryModel> categories = categoryService.getAll();
		req.setAttribute("cateList", categories);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/user/category-list.jsp");
		dispatcher.forward(req, resp);
	}
}


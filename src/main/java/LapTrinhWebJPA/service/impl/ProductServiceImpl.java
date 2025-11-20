package LapTrinhWebJPA.service.impl;

import java.util.List;

import LapTrinhWebJPA.dao.ProductDAO;
import LapTrinhWebJPA.dao.impl.ProductDAOImpl;
import LapTrinhWebJPA.model.ProductModel;
import LapTrinhWebJPA.service.ProductService;

public class ProductServiceImpl implements ProductService {

	ProductDAO productDAO = new ProductDAOImpl();

	@Override
	public List<ProductModel> getAll() {
		return productDAO.getAll();
	}

	@Override
	public List<ProductModel> getByCategoryId(int categoryId) {
		return productDAO.getByCategoryId(categoryId);
	}

	@Override
	public void insert(ProductModel product) {
		productDAO.insert(product);
	}

	@Override
	public ProductModel getById(int id) {
		return productDAO.getById(id);
	}

	@Override
	public void update(ProductModel product) {
		productDAO.update(product);
	}

	@Override
	public void delete(int id) {
		productDAO.delete(id);
	}
}
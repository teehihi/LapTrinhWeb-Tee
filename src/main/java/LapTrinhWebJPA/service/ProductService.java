package LapTrinhWebJPA.service;

import java.util.List;

import LapTrinhWebJPA.model.ProductModel;

public interface ProductService {
	List<ProductModel> getAll();

	List<ProductModel> getByCategoryId(int categoryId);

	void insert(ProductModel product);

	ProductModel getById(int id);

	void update(ProductModel product);

	void delete(int id);
}
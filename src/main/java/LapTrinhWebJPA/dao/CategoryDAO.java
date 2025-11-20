package LapTrinhWebJPA.dao;

import java.util.List;

import LapTrinhWebJPA.model.CategoryModel;

public interface CategoryDAO {
	void insert(CategoryModel category);

	void edit(CategoryModel category);

	void delete(int id);

	CategoryModel get(int id);

	CategoryModel get(String cateName);

	List<CategoryModel> getAll();

	List<CategoryModel> search(String keyword);
}
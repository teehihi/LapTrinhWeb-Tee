package LapTrinhWebJPA.service;

import java.util.List;

import LapTrinhWebJPA.model.CategoryModel;

public interface CategoryService {
	List<CategoryModel> getAll();

	CategoryModel get(int id);

	CategoryModel get(String name);

	void insert(CategoryModel category);

	void edit(CategoryModel category);

	void delete(int id);

	List<CategoryModel> search(String keyword);

	List<CategoryModel> getByOwner(int ownerId);

	CategoryModel getOwnedCategory(int id, int ownerId);

	boolean deleteByOwner(int id, int ownerId);

	boolean updateOwnedCategory(CategoryModel category, int ownerId);
}
package LapTrinhWebJPA.service.impl;

import java.io.File;
import java.util.List;

import LapTrinhWebJPA.dao.CategoryDAO;
import LapTrinhWebJPA.dao.impl.CategoryDAOImpl;
import LapTrinhWebJPA.model.CategoryModel;
import LapTrinhWebJPA.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {

	// Khởi tạo DAO
	CategoryDAO categoryDao = new CategoryDAOImpl();

	@Override
	public void insert(CategoryModel category) {
		categoryDao.insert(category);
	}

	@Override
	public void edit(CategoryModel newCategory) {
		// Lấy category cũ từ database (trả về Model)
		CategoryModel oldCategory = categoryDao.get(newCategory.getId());

		if (oldCategory != null) {
			// Cập nhật tên
			oldCategory.setCateName(newCategory.getCateName());

			// Kiểm tra và cập nhật ảnh
			if (newCategory.getImage() != null) {
				// XÓA ẢNH CŨ (Giữ nguyên logic của bạn)
				String fileName = oldCategory.getImage();
				// Lưu ý: Đường dẫn này nên cấu hình trong web.xml hoặc config file để linh hoạt
				// hơn
				final String dir = "E:\\upload";
				File file = new File(dir + File.separator + fileName); // Dùng File.separator cho an toàn hệ điều hành
				if (file.exists()) {
					file.delete();
				}
				// Set ảnh mới
				oldCategory.setImage(newCategory.getImage());
			}

			// Gọi DAO để update xuống DB
			categoryDao.edit(oldCategory);
		}
	}

	@Override
	public void delete(int id) {

		categoryDao.delete(id);
	}

	@Override
	public CategoryModel get(int id) {
		return categoryDao.get(id);
	}

	@Override
	public CategoryModel get(String name) {
		return categoryDao.get(name);
	}

	@Override
	public List<CategoryModel> getAll() {
		return categoryDao.getAll();
	}

	@Override
	public List<CategoryModel> search(String keyword) {
		return categoryDao.search(keyword);
	}
}
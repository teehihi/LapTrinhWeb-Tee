package LapTrinhWebJPA.dao.impl;

import java.util.ArrayList;
import java.util.List;

import LapTrinhWebJPA.config.JPAConfig;
import LapTrinhWebJPA.dao.ProductDAO;
import LapTrinhWebJPA.entity.Category;
import LapTrinhWebJPA.entity.Product;
import LapTrinhWebJPA.model.ProductModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class ProductDAOImpl implements ProductDAO {

	@Override
	public List<ProductModel> getAll() {
		EntityManager enma = JPAConfig.getEntityManager();
		List<ProductModel> listModel = new ArrayList<>();
		try {
			TypedQuery<Product> query = enma.createQuery("SELECT p FROM Product p", Product.class);
			List<Product> listEntity = query.getResultList();
			for (Product entity : listEntity) {
				listModel.add(new ProductModel(entity.getProductId(), entity.getProductName(), entity.getPrice(),
						entity.getImage(), entity.getCategory() != null ? entity.getCategory().getCategoryId() : 0));
			}
		} finally {
			enma.close();
		}
		return listModel;
	}

	@Override
	public List<ProductModel> getByCategoryId(int categoryId) {
		EntityManager enma = JPAConfig.getEntityManager();
		List<ProductModel> listModel = new ArrayList<>();
		try {
			TypedQuery<Product> query = enma.createQuery("SELECT p FROM Product p WHERE p.category.categoryId = :id",
					Product.class);
			query.setParameter("id", categoryId);
			List<Product> listEntity = query.getResultList();
			for (Product entity : listEntity) {
				listModel.add(new ProductModel(entity.getProductId(), entity.getProductName(), entity.getPrice(),
						entity.getImage(), entity.getCategory().getCategoryId()));
			}
		} finally {
			enma.close();
		}
		return listModel;
	}

	@Override
	public void insert(ProductModel model) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			Product entity = new Product();
			entity.setProductName(model.getName());
			entity.setPrice(model.getPrice());
			entity.setImage(model.getImage());
			entity.setDescription("Mô tả sản phẩm");

			Category cate = enma.find(Category.class, model.getCategoryId());
			entity.setCategory(cate);

			enma.persist(entity);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
		} finally {
			enma.close();
		}
	}

	@Override
	public ProductModel getById(int id) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			Product entity = enma.find(Product.class, id);
			if (entity != null) {
				return new ProductModel(entity.getProductId(), entity.getProductName(), entity.getPrice(),
						entity.getImage(), entity.getCategory() != null ? entity.getCategory().getCategoryId() : 0);
			}
		} finally {
			enma.close();
		}
		return null;
	}

	@Override
	public void update(ProductModel model) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			Product entity = enma.find(Product.class, model.getId());
			if (entity != null) {
				entity.setProductName(model.getName());
				entity.setPrice(model.getPrice());
				entity.setImage(model.getImage());

				if (entity.getCategory() == null || entity.getCategory().getCategoryId() != model.getCategoryId()) {
					Category cate = enma.find(Category.class, model.getCategoryId());
					entity.setCategory(cate);
				}
				enma.merge(entity);
			}
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
		} finally {
			enma.close();
		}
	}

	@Override
	public void delete(int id) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			Product entity = enma.find(Product.class, id);
			if (entity != null)
				enma.remove(entity);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
		} finally {
			enma.close();
		}
	}
}
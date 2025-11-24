package LapTrinhWebJPA.dao.impl;

import java.util.ArrayList;
import java.util.List;

import LapTrinhWebJPA.config.JPAConfig;
import LapTrinhWebJPA.dao.CategoryDAO;
import LapTrinhWebJPA.entity.Category;
import LapTrinhWebJPA.model.CategoryModel;
import LapTrinhWebJPA.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class CategoryDAOImpl implements CategoryDAO {

	@Override
	public void insert(CategoryModel model) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			Category entity = new Category();
			entity.setCategoryName(model.getCateName());
			entity.setIcon(model.getImage());
			if (model.getOwnerId() != null) {
				User owner = enma.find(User.class, model.getOwnerId());
				entity.setOwner(owner);
			}
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
	public void edit(CategoryModel model) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			Category entity = enma.find(Category.class, model.getId());
			if (entity != null) {
				entity.setCategoryName(model.getCateName());
				entity.setIcon(model.getImage());
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
			Category entity = enma.find(Category.class, id);
			if (entity != null) {
				enma.remove(entity);
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
	public CategoryModel get(int id) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			Category entity = enma.find(Category.class, id);
			if (entity != null) {
				return mapToModel(entity);
			}
		} finally {
			enma.close();
		}
		return null;
	}

	@Override
	public List<CategoryModel> getAll() {
		EntityManager enma = JPAConfig.getEntityManager();
		List<CategoryModel> list = new ArrayList<>();
		try {
			TypedQuery<Category> query = enma.createQuery("SELECT c FROM Category c", Category.class);
			List<Category> result = query.getResultList();
			for (Category entity : result) {
				list.add(mapToModel(entity));
			}
		} finally {
			enma.close();
		}
		return list;
	}

	@Override
	public CategoryModel get(String cateName) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			TypedQuery<Category> query = enma.createQuery("SELECT c FROM Category c WHERE c.categoryName = :name",
					Category.class);
			query.setParameter("name", cateName);
			Category entity = query.getSingleResult();
			return mapToModel(entity);
		} catch (NoResultException e) {
			return null;
		} finally {
			enma.close();
		}
	}

	@Override
	public List<CategoryModel> search(String keyword) {
		EntityManager enma = JPAConfig.getEntityManager();
		List<CategoryModel> list = new ArrayList<>();
		try {
			TypedQuery<Category> query = enma.createQuery("SELECT c FROM Category c WHERE c.categoryName LIKE :key",
					Category.class);
			query.setParameter("key", "%" + keyword + "%");
			List<Category> result = query.getResultList();
			for (Category entity : result) {
				list.add(mapToModel(entity));
			}
		} finally {
			enma.close();
		}
		return list;
	}

	@Override
	public List<CategoryModel> getByOwnerId(int ownerId) {
		EntityManager enma = JPAConfig.getEntityManager();
		List<CategoryModel> list = new ArrayList<>();
		try {
			TypedQuery<Category> query = enma.createQuery("SELECT c FROM Category c WHERE c.owner.userId = :ownerId",
					Category.class);
			query.setParameter("ownerId", ownerId);
			List<Category> result = query.getResultList();
			for (Category entity : result) {
				list.add(mapToModel(entity));
			}
		} finally {
			enma.close();
		}
		return list;
	}

	@Override
	public CategoryModel getOwnedCategory(int id, int ownerId) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			TypedQuery<Category> query = enma.createQuery(
					"SELECT c FROM Category c WHERE c.categoryId = :id AND c.owner.userId = :ownerId", Category.class);
			query.setParameter("id", id);
			query.setParameter("ownerId", ownerId);
			Category entity = query.getSingleResult();
			return mapToModel(entity);
		} catch (NoResultException e) {
			return null;
		} finally {
			enma.close();
		}
	}

	@Override
	public boolean deleteByOwner(int id, int ownerId) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			TypedQuery<Category> query = enma.createQuery(
					"SELECT c FROM Category c WHERE c.categoryId = :id AND c.owner.userId = :ownerId", Category.class);
			query.setParameter("id", id);
			query.setParameter("ownerId", ownerId);
			Category entity = query.getSingleResult();
			enma.remove(entity);
			trans.commit();
			return true;
		} catch (NoResultException e) {
			trans.rollback();
			return false;
		} catch (Exception e) {
			trans.rollback();
			throw e;
		} finally {
			enma.close();
		}
	}

	@Override
	public boolean updateOwnedCategory(CategoryModel model, int ownerId) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			TypedQuery<Category> query = enma.createQuery(
					"SELECT c FROM Category c WHERE c.categoryId = :id AND c.owner.userId = :ownerId", Category.class);
			query.setParameter("id", model.getId());
			query.setParameter("ownerId", ownerId);
			Category entity = query.getSingleResult();
			entity.setCategoryName(model.getCateName());
			entity.setIcon(model.getImage());
			enma.merge(entity);
			trans.commit();
			return true;
		} catch (NoResultException e) {
			trans.rollback();
			return false;
		} catch (Exception e) {
			trans.rollback();
			throw e;
		} finally {
			enma.close();
		}
	}

	private CategoryModel mapToModel(Category entity) {
		Integer ownerId = entity.getOwner() != null ? entity.getOwner().getUserId() : null;
		String ownerName = entity.getOwner() != null ? entity.getOwner().getFullname() : null;
		return new CategoryModel(entity.getCategoryId(), entity.getCategoryName(), entity.getIcon(), ownerId, ownerName);
	}
}
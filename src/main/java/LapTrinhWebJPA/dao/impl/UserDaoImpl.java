package LapTrinhWebJPA.dao.impl;

import java.util.List;

import LapTrinhWebJPA.config.JPAConfig;
import LapTrinhWebJPA.dao.UserDAO;
import LapTrinhWebJPA.entity.User;
import LapTrinhWebJPA.model.UserModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class UserDaoImpl implements UserDAO {

	@Override
	public UserModel get(String username) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT u FROM User u WHERE u.username = :user";
			TypedQuery<User> query = enma.createQuery(jpql, User.class);
			query.setParameter("user", username);
			User entity = query.getSingleResult();
			// Map đủ trường
			return new UserModel(entity.getUserId(), entity.getUsername(), entity.getPassword(), entity.getEmail(),
					entity.getFullname(), entity.getPhone(), entity.getRole(), entity.getCreateDate());
		} catch (NoResultException e) {
			return null;
		} finally {
			enma.close();
		}
	}

	@Override
	public void insert(UserModel model) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			User entity = new User();
			entity.setUsername(model.getUserName());
			entity.setPassword(model.getPassWord());
			entity.setEmail(model.getEmail());

			// Map các trường mới và mặc định
			entity.setFullname(model.getFullName());
			entity.setPhone(model.getPhone());
			entity.setRole(model.getRole());
			entity.setCreateDate(model.getCreateDate());

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
	public boolean checkExistEmail(String email) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			TypedQuery<Long> query = enma.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class);
			query.setParameter("email", email);
			return query.getSingleResult() > 0;
		} finally {
			enma.close();
		}
	}

	@Override
	public boolean checkExistUsername(String username) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			TypedQuery<Long> query = enma.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :user",
					Long.class);
			query.setParameter("user", username);
			return query.getSingleResult() > 0;
		} finally {
			enma.close();
		}
	}

	@Override
	public UserModel findByUsernameOrEmail(String keyword) {
		EntityManager enma = JPAConfig.getEntityManager();
		try {
			String jpql = "SELECT u FROM User u WHERE u.username = :key OR u.email = :key";
			TypedQuery<User> query = enma.createQuery(jpql, User.class);
			query.setParameter("key", keyword);
			List<User> list = query.getResultList();
			if (!list.isEmpty()) {
				User entity = list.get(0);
				return new UserModel(entity.getUserId(), entity.getUsername(), entity.getPassword(), entity.getEmail(),
						entity.getFullname(), entity.getPhone(), entity.getRole(), entity.getCreateDate());
			}
		} finally {
			enma.close();
		}
		return null;
	}
}
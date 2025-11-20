package LapTrinhWebJPA.dao;

import LapTrinhWebJPA.model.UserModel;

public interface UserDAO {
	UserModel get(String username);

	void insert(UserModel user);

	boolean checkExistEmail(String email);

	boolean checkExistUsername(String username);

	UserModel findByUsernameOrEmail(String keyword);
}
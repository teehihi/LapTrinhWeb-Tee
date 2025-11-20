package LapTrinhWebJPA.service;

import LapTrinhWebJPA.model.UserModel;

public interface UserService {
	UserModel login(String username, String password);

	UserModel get(String username);

	void insert(UserModel user);

	boolean register(String username, String password, String email, String fullname, String phone);

	boolean checkExistEmail(String email);

	boolean checkExistUsername(String username);

	UserModel findByUsernameOrEmail(String keyword);
}
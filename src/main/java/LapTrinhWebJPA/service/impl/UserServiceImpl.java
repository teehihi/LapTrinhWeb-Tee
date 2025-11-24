package LapTrinhWebJPA.service.impl;

import java.sql.Date;

import LapTrinhWebJPA.config.Constant;
import LapTrinhWebJPA.dao.UserDAO;
import LapTrinhWebJPA.dao.impl.UserDaoImpl;
import LapTrinhWebJPA.model.UserModel;
import LapTrinhWebJPA.service.UserService;

public class UserServiceImpl implements UserService {

	UserDAO userDao = new UserDaoImpl();

	@Override
	public UserModel login(String username, String password) {
		UserModel user = this.get(username);
		if (user != null && password.equals(user.getPassWord())) {
			return user;
		}
		return null;
	}

	@Override
	public UserModel get(String username) {
		return userDao.get(username);
	}

	@Override
	public boolean register(String username, String password, String email, String fullname, String phone) {
		if (userDao.checkExistUsername(username))
			return false;
		if (userDao.checkExistEmail(email))
			return false;

		UserModel newUser = new UserModel();
		newUser.setUserName(username);
		newUser.setPassWord(password);
		newUser.setEmail(email);

		// --- XỬ LÝ DEFAULT VALUES (UX Logic) ---

		// 1. Nếu không nhập Fullname -> Lấy Username làm Fullname
		if (fullname == null || fullname.trim().isEmpty()) {
			newUser.setFullName(username);
		} else {
			newUser.setFullName(fullname);
		}

		// 2. Nếu không nhập Phone -> Để rỗng (tránh null)
		if (phone == null) {
			newUser.setPhone("");
		} else {
			newUser.setPhone(phone);
		}

		// 3. Set mặc định Role = 1 (User) và Ngày tạo = Hiện tại
		newUser.setRole(Constant.ROLE_USER);
		newUser.setCreateDate(new Date(System.currentTimeMillis()));

		userDao.insert(newUser);
		return true;
	}

	@Override
	public boolean checkExistEmail(String email) {
		return userDao.checkExistEmail(email);
	}

	@Override
	public boolean checkExistUsername(String username) {
		return userDao.checkExistUsername(username);
	}

	@Override
	public void insert(UserModel user) {
		userDao.insert(user);
	}

	@Override
	public UserModel findByUsernameOrEmail(String keyword) {
		return userDao.findByUsernameOrEmail(keyword);
	}
}
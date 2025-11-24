package LapTrinhWebJPA.filter;

import java.io.IOException;

import LapTrinhWebJPA.config.Constant;
import LapTrinhWebJPA.model.UserModel;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/user/*", "/manager/*", "/admin/*" })
public class RoleAuthorizationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);

		if (session == null || session.getAttribute(Constant.SESSION_ACCOUNT) == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		UserModel account = (UserModel) session.getAttribute(Constant.SESSION_ACCOUNT);
		String servletPath = req.getServletPath();

		if (!isAuthorized(account.getRole(), servletPath)) {
			resp.sendRedirect(req.getContextPath() + "/waiting");
			return;
		}

		chain.doFilter(request, response);
	}

	private boolean isAuthorized(int role, String path) {
		if (path.startsWith("/admin")) {
			return role == Constant.ROLE_ADMIN;
		}
		if (path.startsWith("/manager")) {
			return role == Constant.ROLE_MANAGER || role == Constant.ROLE_ADMIN;
		}
		if (path.startsWith("/user")) {
			return role == Constant.ROLE_USER || role == Constant.ROLE_ADMIN;
		}
		return true;
	}
}


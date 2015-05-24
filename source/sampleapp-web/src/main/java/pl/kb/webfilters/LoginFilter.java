package pl.kb.webfilters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.entity.Role.UserRoleName;
import pl.kb.controllers.security.SysAuthController;

@WebFilter("/html/sys/secured/*")
public class LoginFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		final SysAuthController loginBean = (SysAuthController) ((HttpServletRequest) request)
				.getSession().getAttribute("sysAuth");

		// For the first application request there is no loginBean in the
		// session so user needs to log in
		// For other requests loginBean is present but we need to check if user
		// has logged in successfully
		if (loginBean == null || !loginBean.isLoggedIn()) {
			String contextPath = ((HttpServletRequest) request)
					.getContextPath();
			((HttpServletResponse) response).sendRedirect(contextPath
					+ "/html/sys/login.xhtml");
		} else if (loginBean != null
				&& !loginBean.isUserInRole(UserRoleName.ADMIN.name())) {
			String contextPath = ((HttpServletRequest) request)
					.getContextPath();
			((HttpServletResponse) response).sendRedirect(contextPath
					+ "/html/sys/permission_error.xhtml");
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		// Nothing to do
	}

	public void destroy() {
		// Nothing to do
	}
}

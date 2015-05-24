package pl.kb.controllers.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import model.entity.Role.UserRoleName;
import model.security.UserDTO;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.kb.utils.ExceptionHandler;
import service.security.UserAuthenticationServiceLocal;

@ManagedBean(name = "sysAuth")
@SessionScoped
public class SysAuthController implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory
			.getLogger(SysAuthController.class);
	private UserDTO user;
	private String username;
	private String password;

	@ManagedProperty(value = "#{navigationBean}")
	private NavigationController navigationBean;

	@EJB
	private UserAuthenticationServiceLocal userService;

	public String doLogin() {
		user = userService.authenticate(username, password);
		password = null;
		if (user != null) {
			return navigationBean.toHome();
		}

		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Login failed. Try again.", "ERROR MSG");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		return navigationBean.toLogin();
	}

	public String doLogout() {
		user = null;
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Logout success!", "INFO MSG");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		return navigationBean.toLogin();
	}

	public boolean isUserInRole(final String role) {
		try {
			final UserRoleName roleName = UserRoleName.valueOf(role);
			return user != null && user.getRoles().contains(roleName);
		} catch (final Exception ex) {
			ExceptionHandler.handleException(ex, "Brak roli o nazwie " + role,
					LOG);
			return false;
		}
	}

	public String createAccount() {
		final Map<String, Object> options = new HashMap<>();
		options.put("modal", false);
		options.put("draggable", false);

		RequestContext.getCurrentInstance().openDialog(
				"/html/sys/createAccountPanel", options, null);
		return null;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNavigationBean(NavigationController navigationBean) {
		this.navigationBean = navigationBean;
	}

	public boolean isLoggedIn() {
		return user != null;
	}

}

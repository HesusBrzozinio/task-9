package pl.kb.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import model.entity.Role.UserRoleName;
import model.entity.User.UserState;
import model.security.UserDTO;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.kb.controllers.security.NavigationController;
import pl.kb.utils.ExceptionHandler;
import service.RoleServiceLocal;
import service.UserServiceLocal;

@ManagedBean(name = "userManagement")
@SessionScoped
public class UserManagementController implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory
			.getLogger(UserManagementController.class);
	private List<UserDTO> allUsers;
	private List<UserRoleName> allRoles;
	private UserDTO actualUser;
	private UserState actualUserState;
	private List<UserRoleName> actualUserRoles;

	@EJB
	private UserServiceLocal userService;

	@EJB
	private RoleServiceLocal roleService;

	@ManagedProperty(value = "#{navigationBean}")
	private NavigationController navigationBean;

	@PostConstruct
	private void init() {
		allUsers = userService.getAllUsers();
		allRoles = roleService.getAllRoleNames();
		actualUser = new UserDTO();
	}

	/**
	 * Show modal panel for user edition
	 */
	public void editUserData() {
		LOG.info("{}", "edit invoked");
		RequestContext.getCurrentInstance().openDialog(
				"/html/sys/secured/editUserPanel");
	}

	/**
	 * Save edited user data and close modal edit panel.
	 */
	public void closeEditPanel() {
		actualUser.setState(actualUserState);
		actualUser.setRoles(actualUserRoles);
		userService.updateUser(actualUser);
		RequestContext.getCurrentInstance().closeDialog(null);
		LOG.info("{}", "edit panel closed");
	}

	/**
	 * Invoked by AJAX when user close modal panel
	 */
	public void onUserModified(final SelectEvent event) {
		LOG.info("{}", "onModified invoked");
		init();
	}

	public String createAccount() {
		try {
			userService.createUser(actualUser);
			init();
		} catch (final Exception ex) {
			ExceptionHandler
					.handleException(ex, "account creation failed", LOG);
			final FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Account creation failed! Please try again.",
					"Account creation failed! Please try again.");
			throw new ValidatorException(message);
		}

		RequestContext.getCurrentInstance().closeDialog(null);
		LOG.info("{}", "account created");
		return null;
	}

	public boolean inState(final UserDTO user, final String state) {
		try {
			final UserState stateName = UserState.valueOf(state);
			return user != null && user.getState() == stateName;
		} catch (final Exception ex) {
			ExceptionHandler.handleException(ex,
					"Brak stanu o nazwie " + state, LOG);
			return false;
		}
	}

	public String activate() {
		actualUser.setState(UserState.ACTIVE);
		return null;
	}

	public String deactivate() {
		actualUser.setState(UserState.BLOCKED);
		return null;
	}

	public String deleteUser() {
		try {
			LOG.info("deleting user {}", actualUser);
			userService.deleteUser(actualUser);
			allUsers.remove(actualUser);
		} catch (final Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Delete failed. Try again.", "ERROR MSG");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return null;
	}

	public void validateSamePassword(final FacesContext context,
			final UIComponent toValidate, final Object value) {

		final String confirmPassword = (String) value;
		if (!confirmPassword.equals(actualUser.getPassword())) {
			final FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Passwords do not match!",
					"Passwords do not match!");
			throw new ValidatorException(message);
		}
	}

	public void validateUserExists(final FacesContext context,
			final UIComponent toValidate, final Object value) {

		final String username = (String) value;
		if (userService.exists(username)) {
			final FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"User with this login exists! Try with different one.",
					"User with this login exists! Try with different one.");
			throw new ValidatorException(message);
		}
	}

	public List<UserDTO> getAllUsers() {
		return allUsers;
	}

	public void setActualUser(final UserDTO user) {
		this.actualUser = user;
		this.actualUserRoles = user.getRoles();
		this.actualUserState = user.getState();
	}

	public UserDTO getActualUser() {
		return actualUser;
	}

	public UserState[] getAllStates() {
		return UserState.values();
	}

	public UserState getActualUserState() {
		return actualUserState;
	}

	public void setActualUserState(UserState actualUserState) {
		this.actualUserState = actualUserState;
	}

	public List<UserRoleName> getActualUserRoles() {
		return actualUserRoles;
	}

	public void setActualUserRoles(List<UserRoleName> actualUserRoles) {
		this.actualUserRoles = actualUserRoles;
	}

	public List<UserRoleName> getAllRoles() {
		return allRoles;
	}

	public void setNavigationBean(NavigationController navigationBean) {
		this.navigationBean = navigationBean;
	}
}

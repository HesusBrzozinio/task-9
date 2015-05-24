package pl.kb.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;

public class ExceptionHandler {

	private ExceptionHandler() {
	}

	public static void handleException(final Exception ex,
			final String message, final Logger logger) {
		logger.error(message, ex);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				message, "ERROR MSG");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}

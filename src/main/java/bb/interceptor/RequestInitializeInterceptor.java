package bb.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import bb.domain.Campaign;
import bb.domain.User;
import bb.service.UserService;

public class RequestInitializeInterceptor extends HandlerInterceptorAdapter {

	// Obtain a suitable logger.
	private static Log log = LogFactory
			.getLog(RequestInitializeInterceptor.class);

	@Autowired
	UserService userService;

	/**
	 * In this case intercept the request BEFORE it reaches the controller
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try {

			log.debug("Intercepting: " + request.getRequestURI());

			// Do some changes to the incoming request object
			updateRequest(request);

			return true;
		} catch (SystemException e) {
			log.info("request update failed");
			return false;
		}
	}

	/**
	 * The data added to the request would most likely come from a database
	 */
	private void updateRequest(HttpServletRequest request) {

		log.info("Updating request object");

		User user = userService.getUserFromSession();

		if (user != null) {
			log.debug("a felhasznalo be van jelentkezve");
			request.setAttribute("user", user);

			//List<Campaign> campaigns = null;// campaignService.findAllByCompany(user.getCompany());

			//request.setAttribute("campaigns", campaigns);

		}
	}

	/** This could be any exception */
	private class SystemException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		// Blank
	}

}

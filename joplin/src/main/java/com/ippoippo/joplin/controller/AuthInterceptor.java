package com.ippoippo.joplin.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

import com.ippoippo.joplin.util.UserCookieForTemporaryGenerator;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	UserCookieForTemporaryGenerator userCookieForTemporaryGenerator;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String requestUri = request.getRequestURI();

		if (requestUri.equals(request.getContextPath()+"/")) {
			return true;

		} else if (requestUri.startsWith(request.getContextPath()+"/signin")) {
			return true;

		} else if (requestUri.startsWith(request.getContextPath()+"/admin")) {
			if (requestUri.equals(request.getContextPath()+"/admin/")
					|| requestUri.equals(request.getContextPath()+"/admin/login")) {
				return true;
			}
			HttpSession session = request.getSession(false);
			if (session != null && session.getAttribute(AdminController.SESSION_KEY_AUTH) != null) {
				return true;
			}

			logger.info("Access without session as admin: " + request.getRequestURI());
			new RedirectView("/admin/", true).render(null, request, response);
			return false;

		} else {
			String userId = userCookieForTemporaryGenerator.getUserId(request);
			if (userId == null) {
				logger.info("Access without session: " + request.getRequestURI());
				new RedirectView("/", true).render(null, request, response);
				return false;
			}
		}
		return true;
	}
}

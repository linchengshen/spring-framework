package com.gitlab.springmvc.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.io.PrintWriter;

@org.springframework.stereotype.Controller(value = "/")
public class RootController implements Controller {
	@Override
	public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {

		try (PrintWriter writer = response.getWriter()) {
			writer.write("RootController");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}
}

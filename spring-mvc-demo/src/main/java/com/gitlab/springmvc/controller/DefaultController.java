package com.gitlab.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import java.io.IOException;
import java.io.PrintWriter;

@Controller("/*")
public class DefaultController implements HttpRequestHandler {

	@Autowired
	private Validator validator;

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try (PrintWriter writer = response.getWriter()) {
			writer.write("DefaultController");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

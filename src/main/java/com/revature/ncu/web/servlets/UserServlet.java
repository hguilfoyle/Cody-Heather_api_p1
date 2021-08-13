package com.revature.ncu.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ncu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;

public class UserServlet extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(UserServlet.class);
    private final UserService userService;
    private final ObjectMapper mapper;

    public UserServlet(UserService userService, ObjectMapper mapper){
        this.userService = userService;
        this.mapper = mapper;
    }
}

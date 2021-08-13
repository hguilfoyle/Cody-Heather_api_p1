package com.revature.ncu.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.revature.ncu.datasources.repositories.UserRepository;
import com.revature.ncu.datasources.utils.MongoClientFactory;
import com.revature.ncu.services.UserService;
import com.revature.ncu.util.PasswordUtils;
import com.revature.ncu.web.servlets.AuthServlet;
import com.revature.ncu.web.servlets.UserServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce){

        MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
        PasswordUtils passwordUtils = new PasswordUtils();
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

        UserRepository userRepo = new UserRepository(mongoClient);
        UserService userService = new UserService(userRepo, passwordUtils);

        UserServlet userServlet = new UserServlet(userService, mapper);
        AuthServlet authServlet = new AuthServlet(userService, mapper);

        ServletContext servletContext = sce.getServletContext();
        servletContext.addServlet("UserServlet", userServlet).addMapping("/users/*");
        servletContext.addServlet("AuthServlet", authServlet).addMapping("/auth");

        configureLogback(servletContext);

    }
}

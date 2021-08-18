package com.revature.ncu.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ncu.services.CourseService;
import com.revature.ncu.services.UserCoursesService;
import com.revature.ncu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


// Student course management
// TODO methods for viewing(enrolled courses and open courses)/enrolling/withdrawing
public class StudentCourseServlet extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(UserServlet.class);

    private final UserService userService;
    private final CourseService courseService;
    private final UserCoursesService userCoursesService;

    private final ObjectMapper mapper;

    public StudentCourseServlet(UserService userService, CourseService courseService,
                                UserCoursesService userCoursesService, ObjectMapper mapper){
        this.userService = userService;
        this.mapper = mapper;
        this.courseService = courseService;
        this. userCoursesService = userCoursesService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Student Courses</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>This will be where students manage their courses!</h1>");
        out.println("</body>");
        out.println("</html>");
    }
}

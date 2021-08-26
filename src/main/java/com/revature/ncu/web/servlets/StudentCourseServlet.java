package com.revature.ncu.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ncu.datasources.documents.Course;
import com.revature.ncu.services.CourseService;
import com.revature.ncu.services.UserCoursesService;
import com.revature.ncu.services.UserService;
import com.revature.ncu.util.exceptions.InvalidEntryException;
import com.revature.ncu.util.exceptions.InvalidRequestException;
import com.revature.ncu.util.exceptions.ResourcePersistenceException;
import com.revature.ncu.web.dtos.ErrorResponse;
import com.revature.ncu.web.dtos.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getAttribute("filtered"));
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        // Get the principal information from the request, if it exists.
        Principal requestingUser = (Principal) req.getAttribute("principal");

        if (requestingUser == null) {
            String msg = "No session found, please login.";
            logger.info(msg);
            resp.setStatus(401);
            ErrorResponse errResp = new ErrorResponse(401, msg);
            respWriter.write(mapper.writeValueAsString(errResp));
            return;
        }else if(requestingUser.isFaculty())
        {
            String msg = "Hey, you're faculty, go to your dashboard.";
            logger.info(msg);
            resp.setStatus(401);
            ErrorResponse errResp = new ErrorResponse(401, msg);
            respWriter.write(mapper.writeValueAsString(errResp));
            return;
        }
           String act = req.getParameter("action");
        try{
            if(act.equals("view")) {
                List catalog = courseService.getCourses();
                String payload = mapper.writeValueAsString(catalog);  //maps the principal value to a string
                respWriter.write(payload);      //returning the username and ID to the web as a string value
                resp.setStatus(200);            //200: Success
            }
            if(act.equals("schedule")) {
                List schedule = userCoursesService.getCourses(requestingUser.getUsername());
                String payload = mapper.writeValueAsString(schedule);  //maps the principal value to a string
                respWriter.write(payload);      //returning the username and ID to the web as a string value
                resp.setStatus(200);            //200: Success
            }
        }catch (InvalidRequestException | InvalidEntryException ie) {
            ie.printStackTrace();
            resp.setStatus(400); // client's fault
            ErrorResponse errResp = new ErrorResponse(400, ie.getMessage());
            respWriter.write(mapper.writeValueAsString(errResp));
        } catch (ResourcePersistenceException rpe) {
            resp.setStatus(409);   //409 conflict: user/email already exists
            ErrorResponse errResp = new ErrorResponse(409, rpe.getMessage());
            respWriter.write(mapper.writeValueAsString(errResp));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);    // server made an oopsie woopsie
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(req.getAttribute("filtered"));
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        // Get the principal information from the request, if it exists.
        Principal requestingUser = (Principal) req.getAttribute("principal");

        if (requestingUser == null) {
            String msg = "No session found, please login.";
            logger.info(msg);
            resp.setStatus(401);
            ErrorResponse errResp = new ErrorResponse(401, msg);
            respWriter.write(mapper.writeValueAsString(errResp));
            return;
        }else if(requestingUser.isFaculty())
        {
            String msg = "Hey, you're faculty, go to your dashboard.";
            logger.info(msg);
            resp.setStatus(401);
            ErrorResponse errResp = new ErrorResponse(401, msg);
            respWriter.write(mapper.writeValueAsString(errResp));
            return;
        }

        try{
            Course course = mapper.readValue(req.getInputStream(), Course.class);
            userCoursesService.joinCourse(course.getCourseAbbreviation(), requestingUser.getUsername());
            String payload = "Course joined";  //maps the principal value to a string
            respWriter.write(payload);      //returning the username and ID to the web as a string value
            resp.setStatus(201);            //201: Created

        }catch (InvalidRequestException | InvalidEntryException ie) {
            ie.printStackTrace();
            resp.setStatus(400); // client's fault
            ErrorResponse errResp = new ErrorResponse(400, ie.getMessage());
            respWriter.write(mapper.writeValueAsString(errResp));
        } catch (ResourcePersistenceException rpe) {
            resp.setStatus(409);   //409 conflict: user/email already exists
            ErrorResponse errResp = new ErrorResponse(409, rpe.getMessage());
            respWriter.write(mapper.writeValueAsString(errResp));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);    // server made an oopsie woopsie
        }


    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(req.getAttribute("filtered"));
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        // Get the principal information from the request, if it exists.
        Principal requestingUser = (Principal) req.getAttribute("principal");

        if (requestingUser == null) {
            String msg = "No session found, please login.";
            logger.info(msg);
            resp.setStatus(401);
            ErrorResponse errResp = new ErrorResponse(401, msg);
            respWriter.write(mapper.writeValueAsString(errResp));
            return;
        }else if(requestingUser.isFaculty())
        {
            String msg = "Hey, you're faculty, go to your dashboard.";
            logger.info(msg);
            resp.setStatus(401);
            ErrorResponse errResp = new ErrorResponse(401, msg);
            respWriter.write(mapper.writeValueAsString(errResp));
            return;
        }

        try{
            // Get abbreviation from frontend and map it to remove
            Course remove = mapper.readValue(req.getInputStream(), Course.class);
            String removeAbv = remove.getCourseAbbreviation();
            userCoursesService.leaveCourse(requestingUser.getUsername(), removeAbv);
            courseService.removeStudent(requestingUser.getUsername(), removeAbv);
            // Inform user of successful action
            String payload = "Successfully withdrew from course!";
            respWriter.write(payload);
            resp.setStatus(204);            //204: No Content so it went bye-bye

        }catch (InvalidRequestException | InvalidEntryException ie) {
            ie.printStackTrace();
            resp.setStatus(400); // client's fault
            ErrorResponse errResp = new ErrorResponse(400, ie.getMessage());
            respWriter.write(mapper.writeValueAsString(errResp));
        } catch (ResourcePersistenceException rpe) {
            resp.setStatus(409);   //409 conflict: user/email already exists
            ErrorResponse errResp = new ErrorResponse(409, rpe.getMessage());
            respWriter.write(mapper.writeValueAsString(errResp));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);    // server made an oopsie woopsie
        }
    }

}

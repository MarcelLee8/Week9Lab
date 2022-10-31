package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Role;
import models.User;
import services.RoleService;
import services.UserService;
import exceptions.InvalidInputException;

/**
 *
 * @author marce
 */
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RoleService rService = new RoleService();
        UserService uService = new UserService();

        String action = request.getParameter("action");
        String message = null;
        User user = null;
        String emailAddress = null;
        List<User> users = null;
        List<Role> roles = null;

        try {
            roles = rService.getAll();
            users = uService.getAll();

            request.setAttribute("roles", roles);
            request.setAttribute("users", users);

            switch (action) {
                case "edit":
                    message = "edit";
                    request.setAttribute("message", message);
                    emailAddress = request.getParameter("emailAddress");
                    user = uService.get(emailAddress);
                    request.setAttribute("user", user);
                    break;
                case "delete":
                    emailAddress = request.getParameter("emailAddress");
                    uService.deleteUser(emailAddress);
                    break;
            }

        } catch (NullPointerException e)    {

        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName())
                    .log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");

        } finally {

            try {    
                users = uService.getAll();
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            request.setAttribute("users", users);

            String isEmpty = ((users.isEmpty()) ? "true" : "false");
            request.setAttribute("isEmpty", isEmpty);

            getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService us = new UserService();
        RoleService rs = new RoleService();

        List<User> users = null;
        List<Role> roles = null;
        String action = request.getParameter("action");
        String message = null;
        String errorMsg = null;
        Role role = null;
        User user = null;

        try {
            String email = request.getParameter("email");
            String fname = request.getParameter("fname");
            String lname = request.getParameter("lname");
            String pword = request.getParameter("pword");
            String roleID = request.getParameter("roles");

            if (roleID.equals("system admin")) {
                role = new Role(1, "system admin");
            }
            else {
                role = new Role(2, "regular user");
            }

            user = new User(email, fname, lname, pword, role);

            if (email.isEmpty() || fname.isEmpty() || lname.isEmpty()
                    || pword.isEmpty()) {
                throw new InvalidInputException();
            }

            switch (action) {
                case "add":
                    us.addUser(user);
                    break;
                case "update":
                    us.updateUser(user);
                    break;
            }
        } catch (SQLException ex) {
            errorMsg = "User already exists.";
            request.setAttribute("errorMsg", errorMsg);
            request.setAttribute("user", user);

        } catch (InvalidInputException e) {
            errorMsg = "All fields are required.";
            if (action.equals("add"))
                message = null;
            else
                message = "edit";

            request.setAttribute("message", message);
            request.setAttribute("errorMsg", errorMsg);
            request.setAttribute("user", user);

        } catch (Exception ex) {            
            Logger.getLogger(UserServlet.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {

            try {
                users = us.getAll();
                roles = rs.getAll();
                String isEmpty = ((users.isEmpty()) ? "true" : "false");

                request.setAttribute("isEmpty", isEmpty);
                request.setAttribute("users", users);
                request.setAttribute("roles", roles);

                getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
                        .forward(request, response);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName())
                        .log(Level.SEVERE, null, ex);
            }   
        }
    }
}
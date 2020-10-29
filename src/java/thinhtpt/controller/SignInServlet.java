/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import thinhtpt.tblroles.TblRolesDao;
import thinhtpt.tblusers.TblUsersDao;
import thinhtpt.tblusers.TblUsersDto;

/**
 *
 * @author ThinhTPT
 */
@WebServlet(name = "SignInServlet", urlPatterns = {"/SignInServlet"})
public class SignInServlet extends HttpServlet {

    private final String ERROR_PAGE = "signInFail";
    private final String USER_HOME_PAGE = "searchPage";
    private final String ADMIN_HOME_PAGE = "adminPage";

    private final Logger LOGGER = Logger.getLogger(SignInServlet.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        String username = request.getParameter("txtUserId");
        String password = request.getParameter("txtPassword");

        String url = ERROR_PAGE;
        boolean isSuccess = false;

        try {
            TblUsersDao userDao = new TblUsersDao();
            TblUsersDto user = userDao.checkLogin(username, password);
            if (user != null) {
                TblRolesDao roleDao = new TblRolesDao();
                String roleName = roleDao.getRoleNameById(user.getRoleId());
                if (roleName.equalsIgnoreCase("Admin")) {
                    url = ADMIN_HOME_PAGE;
                } else if (roleName.equalsIgnoreCase("User")) {
                    url = USER_HOME_PAGE;
                } else {
                    url = ERROR_PAGE;
                }
                isSuccess = true;
                HttpSession session = request.getSession();
                session.setAttribute("USER_ID", user.getUserId());
                session.setAttribute("USER_NAME", user.getUsername());
                session.setAttribute("USER_FULLNAME", user.getFullName());
                session.setAttribute("USER_ROLE", roleName);
            }
        } catch (SQLException | NamingException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            if (isSuccess) {
                response.sendRedirect(url);
            } else {
                request.setAttribute("ERR_MSG", "Email or password is incorrect");
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

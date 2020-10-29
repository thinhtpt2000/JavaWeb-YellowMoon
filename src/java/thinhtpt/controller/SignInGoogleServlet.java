/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Collections;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
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
@WebServlet(name = "SignInGoogleServlet", urlPatterns = {"/SignInGoogleServlet"})
public class SignInGoogleServlet extends HttpServlet {

    private final String USER_HOME_PAGE = "searchPage";
    private final String ADMIN_HOME_PAGE = "adminPage";

    private final Logger LOGGER = Logger.getLogger(SignInGoogleServlet.class);

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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String url = null;

        boolean isSuccess = false;

        try {
            ServletContext context = request.getServletContext();
            String clientId = context.getInitParameter("client-id");

            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
            GoogleIdTokenVerifier idTokenVerifier
                    = new GoogleIdTokenVerifier.Builder(httpTransport, jacksonFactory)
                            .setAudience(Collections.singletonList(clientId))
                            .build();

            String tokenString = request.getParameter("token_id");
            if (tokenString != null) {
                GoogleIdToken tokenId = idTokenVerifier.verify(tokenString);
                if (tokenId != null) {
                    GoogleIdToken.Payload payload = tokenId.getPayload();

                    // Get user identifier
                    String googleId = payload.getSubject();

                    // Get profile information
                    String email = payload.getEmail();
                    String name = (String) payload.get("name");

                    TblUsersDao userDao = new TblUsersDao();
                    TblUsersDto userDto = userDao.checkExistEmail(email);
                    TblRolesDao roleDao = new TblRolesDao();
                    int roleId = roleDao.getRoleByName("User");
                    if (userDto == null) {
                        // Add new
                        userDto = new TblUsersDto(email, name, roleId, googleId);
                        int userId = userDao.addNewGoogleAccount(userDto);
                        userDto.setUserId(userId);
                    } else if (userDto.getGoogleId() == null || userDto.getGoogleId().equals("")) {
                        // Update Google ID
                        userDao.updateGoogleIdForAccount(googleId, userDto.getUserId());
                    }
                    String roleName = roleDao.getRoleNameById(userDto.getRoleId());
                    if (roleName.equalsIgnoreCase("Admin")) {
                        url = ADMIN_HOME_PAGE;
                    } else if (roleName.equalsIgnoreCase("User")) {
                        url = USER_HOME_PAGE;
                    }
                    isSuccess = true;
                    HttpSession session = request.getSession();
                    session.setAttribute("USER_ID", userDto.getUserId());
                    session.setAttribute("USER_NAME", userDto.getUsername());
                    session.setAttribute("USER_FULLNAME", userDto.getFullName());
                    session.setAttribute("USER_ROLE", roleName);
                }
            }
        } catch (SQLException | NamingException | GeneralSecurityException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            JsonObject objResponse = new JsonObject();
            objResponse.addProperty("status", isSuccess);
            if (isSuccess) {
                objResponse.addProperty("redirect_url", url);
            } else {
                objResponse.addProperty("error_message", "Error while login to your Google Acount");
            }
            out.print(objResponse);
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

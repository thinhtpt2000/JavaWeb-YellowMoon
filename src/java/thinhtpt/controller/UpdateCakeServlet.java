/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import thinhtpt.tbllogs.TblLogsDao;
import thinhtpt.tbllogs.TblLogsDto;
import thinhtpt.tblproducts.TblProductsDao;
import thinhtpt.tblproducts.TblProductsDto;
import thinhtpt.utils.ValidationHelper;

/**
 *
 * @author ThinhTPT
 */
@WebServlet(name = "UpdateCakeServlet", urlPatterns = {"/UpdateCakeServlet"})
public class UpdateCakeServlet extends HttpServlet {

    private final String ERROR_PAGE = "errorAdminPage";

    private final Logger LOGGER = Logger.getLogger(UpdateCakeServlet.class);

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

        String idText = request.getParameter("txtId");
        String name = request.getParameter("txtName");
        String priceText = request.getParameter("txtPrice");
        String quantityText = request.getParameter("txtQuantity");
        String createDateText = request.getParameter("txtCreateDate");
        String expDateText = request.getParameter("txtExpireDate");
        String categoryText = request.getParameter("txtCategory");
        String statusText = request.getParameter("txtStatus");
        String imageLink = request.getParameter("txtFileLink");
        String pageText = request.getParameter("page");

        String url = ERROR_PAGE;

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                String userIdString = (String) (session.getAttribute("USER_ID") + "");
                int userId = -1;
                if (userIdString != null
                        && ValidationHelper.isValidNumber(userIdString)) {
                    userId = Integer.parseInt(userIdString);
                }
                if (userId > 0) {
                    int productId = Integer.parseInt(idText);
                    int price = Integer.parseInt(priceText);
                    int quantity = Integer.parseInt(quantityText);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsedDate = dateFormat.parse(createDateText);
                    Timestamp createDate = new Timestamp(parsedDate.getTime());
                    parsedDate = dateFormat.parse(expDateText);
                    Timestamp exprireDate = new Timestamp(parsedDate.getTime());

                    int categoryId = Integer.parseInt(categoryText);
                    int statusId = Integer.parseInt(statusText);

                    TblProductsDto dto
                            = new TblProductsDto(productId, name, imageLink, price,
                                    quantity, categoryId, createDate, exprireDate, statusId);
                    TblProductsDao dao = new TblProductsDao();
                    boolean result = dao.updateProduct(dto);
                    if (result) {
                        TblLogsDao logDao = new TblLogsDao();
                        Timestamp updateDate = new Timestamp(new Date().getTime());
                        TblLogsDto logDto = new TblLogsDto(userId, productId, updateDate);
                        result = logDao.addLog(logDto);
                        if (result) {
                            url = "loadCakes?page=" + pageText;
                        }
                    }
                }
            }
        } catch (ParseException | SQLException | NamingException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            response.sendRedirect(url);
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

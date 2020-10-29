/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import thinhtpt.tblproducts.TblProductsDao;
import thinhtpt.tblproducts.TblProductsDto;
import thinhtpt.tblproductstatus.TblProductStatusDao;

/**
 *
 * @author ThinhTPT
 */
@WebServlet(name = "CreateCakeServlet", urlPatterns = {"/CreateCakeServlet"})
public class CreateCakeServlet extends HttpServlet {

    private final String ERROR_PAGE = "errorAdminPage";
    private final String SUCCESS_PAGE = "createPage";

    private final Logger LOGGER = Logger.getLogger(CreateCakeServlet.class);

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

        String name = request.getParameter("txtName");
        String priceText = request.getParameter("txtPrice");
        String quantityText = request.getParameter("txtQuantity");
        String description = request.getParameter("txtDescription");
        String createDateText = request.getParameter("txtCreateDate");
        String expDateText = request.getParameter("txtExpireDate");
        String categoryText = request.getParameter("txtCategory");
        String imageLink = request.getParameter("txtFileLink");

        String url = ERROR_PAGE;
        boolean result = false;

        try {
            int price = Integer.parseInt(priceText);
            int quantity = Integer.parseInt(quantityText);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(createDateText);
            Timestamp createDate = new Timestamp(parsedDate.getTime());
            parsedDate = dateFormat.parse(expDateText);
            Timestamp exprireDate = new Timestamp(parsedDate.getTime());

            int categoryId = Integer.parseInt(categoryText);

            TblProductStatusDao statusDao = new TblProductStatusDao();
            int statusId = statusDao.getStatusIdByName("Active");

            TblProductsDto dto
                    = new TblProductsDto(name, description, imageLink, price, quantity, categoryId, statusId, createDate, exprireDate);
            TblProductsDao dao = new TblProductsDao();
            result = dao.addProduct(dto);

            if (result) {
                url = SUCCESS_PAGE;
                request.setAttribute("MSG", "Your cake is added");
            }

        } catch (ParseException | SQLException | NamingException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            if (result) {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                response.sendRedirect(url);
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

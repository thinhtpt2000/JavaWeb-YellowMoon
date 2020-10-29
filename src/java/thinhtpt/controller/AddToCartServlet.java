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
import java.util.Date;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import thinhtpt.cart.CartObject;
import thinhtpt.tblproducts.TblProductsDao;
import thinhtpt.tblproducts.TblProductsDto;
import thinhtpt.tblproductstatus.TblProductStatusDao;
import thinhtpt.utils.ValidationHelper;

/**
 *
 * @author ThinhTPT
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

    private final Logger LOGGER = Logger.getLogger(AddToCartServlet.class);

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
        String searchName = request.getParameter("txtName");
        String categoryText = request.getParameter("txtCategory");
        String txtMinPrice = request.getParameter("txtMinPrice");
        String txtMaxPrice = request.getParameter("txtMaxPrice");
        String page = request.getParameter("page");

        String urlRewriting = "search?txtName=" + searchName
                + "&txtCategory=" + categoryText
                + "&txtMinPrice=" + txtMinPrice
                + "&txtMaxPrice=" + txtMaxPrice
                + "&page=" + page;

        boolean result = false;

        try {
            int productId = -1;
            if (idText != null && ValidationHelper.isValidNumber(idText)) {
                productId = Integer.parseInt(idText);
            }
            if (productId > 0) {
                HttpSession session = request.getSession();

                CartObject cart = (CartObject) session.getAttribute("CART");
                if (cart == null) {
                    cart = new CartObject();
                }
                TblProductStatusDao statusDao = new TblProductStatusDao();
                int statusId = statusDao.getStatusIdByName("Active");

                TblProductsDao productDao = new TblProductsDao();
                Timestamp time = new Timestamp(new Date().getTime());
                TblProductsDto dto
                        = productDao.getProductCartById(productId, statusId, time);
                if (dto != null) {
                    result = cart.addItemToCart(dto.getProductId(), dto.getName(), dto.getPrice(), dto.getQuantity());
                    session.setAttribute("CART", cart);
                }
            }
        } catch (SQLException | NamingException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            if (result) {
                response.sendRedirect(urlRewriting);
            } else {
                request.setAttribute("ERROR_ADD", "Fail to add selected cake to cart");
                RequestDispatcher rd = request.getRequestDispatcher(urlRewriting);
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

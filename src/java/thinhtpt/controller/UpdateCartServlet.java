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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import thinhtpt.cart.CartObject;
import thinhtpt.tblproducts.TblProductsDao;
import thinhtpt.tblproductstatus.TblProductStatusDao;
import thinhtpt.utils.ValidationHelper;

/**
 *
 * @author ThinhTPT
 */
@WebServlet(name = "UpdateCartServlet", urlPatterns = {"/UpdateCartServlet"})
public class UpdateCartServlet extends HttpServlet {

    private final String CART_PAGE = "viewCart";

    private final Logger LOGGER = Logger.getLogger(UpdateCartServlet.class);

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
        String amountText = request.getParameter("txtAmount");

        int productId = -1;
        int amount = -1;

        String url = CART_PAGE;

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                CartObject cart = (CartObject) session.getAttribute("CART");
                if (cart != null) {
                    if (idText != null && ValidationHelper.isValidNumber(idText)) {
                        productId = Integer.parseInt(idText);
                    }
                    if (amountText != null && ValidationHelper.isValidNumber(amountText)) {
                        amount = Integer.parseInt(amountText);
                    }
                    if (productId != -1 && amount != -1) {
                        TblProductStatusDao statusDao = new TblProductStatusDao();
                        int statusId = statusDao.getStatusIdByName("Active");
                        TblProductsDao productDao = new TblProductsDao();
                        Timestamp time = new Timestamp(new Date().getTime());
                        int maxAmount = productDao.getProductQuantity(productId, statusId, time);
                        if (maxAmount >= amount) {
                            cart.updateAmount(productId, amount, maxAmount);
                            session.setAttribute("CART", cart);
                        } else {
                            cart.setAmountError(productId, "Only " + maxAmount + " cakes left");
                        }
                    }
                }
            }
        } catch (SQLException | NamingException ex) {
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

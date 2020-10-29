/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import thinhtpt.cart.CartDetails;
import thinhtpt.cart.CartObject;
import thinhtpt.tblorder.TblOrderDao;
import thinhtpt.tblpaymentstatus.TblPaymentStatusDao;
import thinhtpt.tblproducts.TblProductsDao;

/**
 *
 * @author ThinhTPT
 */
@WebServlet(name = "ProcessNganLuongCancelServlet", urlPatterns = {"/ProcessNganLuongCancelServlet"})
public class ProcessNganLuongCancelServlet extends HttpServlet {

    private final String ERROR_PAGE = "viewCart";
    private final Logger LOGGER = Logger.getLogger(ProcessNganLuongCancelServlet.class);

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

        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                CartObject cart = (CartObject) session.getAttribute("CART");
                if (cart != null) {
                    Map<Integer, CartDetails> items = cart.getItems();
                    if (items != null) {
                        String orderId = (String) session.getAttribute("ORDER_ID");
                        TblOrderDao orderDao = new TblOrderDao();
                        TblPaymentStatusDao paymentStatusDao = new TblPaymentStatusDao();
                        int paymentStatusId = paymentStatusDao.getStatusId("Cancel");
                        orderDao.updatePaymentStatusOrder(orderId, paymentStatusId);
                        TblProductsDao productDao = new TblProductsDao();
                        productDao.redoUpdateProductQuantity(items);
                        session.removeAttribute("ORDER_ID");
                    } // end if items is not null
                } // end if cart is not null
            } // end if session is not null
            request.setAttribute("CHECKOUT_CANCEL", "You canceled payment with Ngan Luong");
        } catch (SQLException | NamingException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(ERROR_PAGE);
            rd.forward(request, response);
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

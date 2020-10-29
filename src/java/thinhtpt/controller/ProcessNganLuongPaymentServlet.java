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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
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
@WebServlet(name = "ProcessNganLuongPaymentServlet", urlPatterns = {"/ProcessNganLuongPaymentServlet"})
public class ProcessNganLuongPaymentServlet extends HttpServlet {

    private final String ERROR_PAGE = "viewCart";
    private final String SUCCESS_PAGE = "checkoutSuccess";

    private final Logger LOGGER = Logger.getLogger(ProcessNganLuongPaymentServlet.class);

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

        String url = ERROR_PAGE;

        try {
            HttpSession session = request.getSession(false);

            TblPaymentStatusDao paymentStatusDao = new TblPaymentStatusDao();
            TblOrderDao orderDao = new TblOrderDao();

            if (session != null) {
                CartObject cart = (CartObject) session.getAttribute("CART");
                if (cart != null) {
                    Map<Integer, CartDetails> items = cart.getItems();
                    if (items != null) {
                        ServletContext context = request.getServletContext();
                        String merchantSiteCode = context.getInitParameter("merchant-site-code");
                        String securePass = context.getInitParameter("secure-pass");

                        String transactionInfo = request.getParameter("transaction_info");
                        String price = request.getParameter("price");
                        String paymentId = request.getParameter("payment_id");
                        String paymentType = request.getParameter("payment_type");
                        String errorText = request.getParameter("error_text");
                        String secureCode = request.getParameter("secure_code");
                        // String tokenNL = request.getParameter("token_nl");
                        String orderCode = request.getParameter("order_code");

                        String verifySecureCodeTmp = ' ' + transactionInfo + ' ' + orderCode
                                + ' ' + price + ' ' + paymentId + ' ' + paymentType
                                + ' ' + errorText + ' ' + merchantSiteCode + ' ' + securePass;
                        String verifySecureCode = DigestUtils.md5Hex(verifySecureCodeTmp);
                        if (verifySecureCode.equals(secureCode)) {
                            if (errorText.trim().length() == 0) {
                                // Success
                                int paymentStatusId = paymentStatusDao.getStatusId("Success");
                                boolean result = orderDao.updatePaymentStatusOrder(orderCode, paymentStatusId);
                                if (result) {
                                    request.setAttribute("CHECKOUT_MSG", "Your order is accepted with id: " + orderCode);
                                    session.removeAttribute("CART");
                                    url = SUCCESS_PAGE;
                                }
                            } else {
                                // Error
                                TblProductsDao productDao = new TblProductsDao();
                                productDao.redoUpdateProductQuantity(items);
                                int paymentStatusId = paymentStatusDao.getStatusId("Error");
                                orderDao.updatePaymentStatusOrder(orderCode, paymentStatusId);
                                request.setAttribute("CHECKOUT_ERR", "Error while checking out your cart, please try again");
                            }
                        } else {
                            // Error
                            TblProductsDao productDao = new TblProductsDao();
                            productDao.redoUpdateProductQuantity(items);
                            int paymentStatusId = paymentStatusDao.getStatusId("Error");
                            orderDao.updatePaymentStatusOrder(orderCode, paymentStatusId);
                            request.setAttribute("CHECKOUT_ERR", "Error while checking out your cart, please try again");
                        }
                    } // end if items not null
                } // end if cart not null
                session.removeAttribute("ORDER_ID");
            } // end if session not null
        } catch (SQLException | NamingException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
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

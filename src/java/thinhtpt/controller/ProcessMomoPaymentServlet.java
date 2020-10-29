/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.controller;

import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.utils.Encoder;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
@WebServlet(name = "ProcessMomoPaymentServlet", urlPatterns = {"/ProcessMomoPaymentServlet"})
public class ProcessMomoPaymentServlet extends HttpServlet {

    private final String ERROR_PAGE = "viewCart";
    private final String SUCCESS_PAGE = "checkoutSuccess";
    private final Logger LOGGER = Logger.getLogger(ProcessMomoPaymentServlet.class);

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

        String partnerCode = request.getParameter("partnerCode");
        String accessKey = request.getParameter("accessKey");
        String requestId = request.getParameter("requestId");
        String amount = request.getParameter("amount");
        String orderId = request.getParameter("orderId");
        String orderInfo = request.getParameter("orderInfo");
        String orderType = request.getParameter("orderType");
        String transId = request.getParameter("transId");
        String message = request.getParameter("message");
        String localMessage = request.getParameter("localMessage");
        String responseTime = request.getParameter("responseTime");
        String errorCode = request.getParameter("errorCode");
        String payType = request.getParameter("payType");
        String extraData = request.getParameter("extraData");
        String signature = request.getParameter("signature");
        String data = String.format("partnerCode=%s"
                + "&accessKey=%s"
                + "&requestId=%s"
                + "&amount=%s"
                + "&orderId=%s"
                + "&orderInfo=%s"
                + "&orderType=%s"
                + "&transId=%s"
                + "&message=%s"
                + "&localMessage=%s"
                + "&responseTime=%s"
                + "&errorCode=%s"
                + "&payType=%s"
                + "&extraData=%s",
                partnerCode, accessKey, requestId, amount, orderId, orderInfo, orderType, transId, message, localMessage, responseTime, errorCode, payType, extraData);

        String url = ERROR_PAGE;
        boolean isCancel = false;

        try {
            HttpSession session = request.getSession(false);

            TblPaymentStatusDao paymentStatusDao = new TblPaymentStatusDao();
            TblOrderDao orderDao = new TblOrderDao();

            if (session != null) {
                CartObject cart = (CartObject) session.getAttribute("CART");
                if (cart != null) {
                    Map<Integer, CartDetails> items = cart.getItems();
                    if (items != null) {
                        Environment env = Environment.selectEnv("dev", Environment.ProcessType.PAY_GATE);
                        String secrectKey = env.getPartnerInfo().getSecretKey();
                        String genrateSignature = Encoder.signHmacSHA256(data, secrectKey);

                        if (genrateSignature.equals(signature)) {
                            if (errorCode.equals("0")) {
                                // Success
                                int paymentStatusId = paymentStatusDao.getStatusId("Success");
                                boolean result = orderDao.updatePaymentStatusOrder(orderId, paymentStatusId);
                                if (result) {
                                    request.setAttribute("CHECKOUT_MSG", "Your order is accepted with id: " + orderId);
                                    session.removeAttribute("CART");
                                    url = SUCCESS_PAGE;
                                }
                            } else if (errorCode.equals("49")) {
                                // Cancel
                                int paymentStatusId = paymentStatusDao.getStatusId("Cancel");
                                orderDao.updatePaymentStatusOrder(orderId, paymentStatusId);
                                isCancel = true;
                                request.setAttribute("CHECKOUT_CANCEL", "You canceled payment with MOMO");
//                            } else {
//                                int paymentStatusId = paymentStatusDao.getStatusId("Error");
//                                orderDao.updatePaymentStatusOrder(orderId, paymentStatusId);
                            }// end if checking errorCode
                        } // end if valid signature
                        if (!url.equals(SUCCESS_PAGE)) {
                            TblProductsDao productDao = new TblProductsDao();
                            productDao.redoUpdateProductQuantity(items);
                        } // end if url is not success page
                    } // end if items not null
                } // end if cart not null
            } // end if session not null
            if (url.equals(ERROR_PAGE) && !isCancel) {
                int paymentStatusId = paymentStatusDao.getStatusId("Error");
                orderDao.updatePaymentStatusOrder(orderId, paymentStatusId);
                request.setAttribute("CHECKOUT_ERR", "Error while checking out your cart, please try again");
            }
        } catch (NoSuchAlgorithmException
                | InvalidKeyException
                | UnsupportedEncodingException
                | SQLException
                | NamingException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
//            response.sendRedirect(url);
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

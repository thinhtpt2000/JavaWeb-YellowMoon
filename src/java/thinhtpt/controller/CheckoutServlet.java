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
import thinhtpt.tblorder.TblOrderDto;
import thinhtpt.tblorderdetail.TblOrderDetailDao;
import thinhtpt.tblpaymentmethod.TblPaymentMethodDao;
import thinhtpt.tblpaymentstatus.TblPaymentStatusDao;
import thinhtpt.tblproducts.TblProductsDao;
import thinhtpt.tblproductstatus.TblProductStatusDao;
import thinhtpt.utils.ValidationHelper;

/**
 *
 * @author ThinhTPT
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

    private final String SUCCES_PAGE = "checkoutSuccess";
    private final String ERROR_PAGE = "viewCart";
    private final String SERVER_ERR_PAGE = "errorPage";
    private final String OUT_STOCK_PAGE = "viewCart";
    private final String PROCESS_MOMO = "sendMomoPayment";
    private final String PROCESS_NGAN_LUONG = "sendNganLuongPayment";

    private final Logger LOGGER = Logger.getLogger(CheckoutServlet.class);

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
        String phone = request.getParameter("txtPhone");
        String address = request.getParameter("txtAddress");
        String method = request.getParameter("txtMethod");
        int userId = -1;
        String url = ERROR_PAGE;

        try {
            TblPaymentMethodDao methodDao = new TblPaymentMethodDao();
            int methodId = Integer.parseInt(method);
            String methodName = methodDao.getMethodNameById(methodId);

            HttpSession session = request.getSession(false);
            if (session != null) {
                String userIdString = (String) (session.getAttribute("USER_ID") + "");
                if (userIdString != null && ValidationHelper.isValidNumber(userIdString)) {
                    userId = Integer.parseInt(userIdString);
                }
                CartObject cart = (CartObject) session.getAttribute("CART");
                if (cart != null) {
                    TblProductStatusDao proStatusDao = new TblProductStatusDao();
                    int statusId = proStatusDao.getStatusIdByName("Active");
                    // Check all amount of products
                    Map<Integer, CartDetails> items = cart.getItems();
                    TblProductsDao productDao = new TblProductsDao();
                    boolean isAllValid = true;
                    Timestamp time = new Timestamp(new Date().getTime());
                    if (items != null) {
                        for (int key : items.keySet()) {
                            CartDetails detail = items.get(key);
                            int maxAmount = productDao.getProductQuantity(key, statusId, time);
                            detail.setMaxAmount(maxAmount);
                            if (maxAmount < detail.getAmount()) {
                                detail.setCartErr("Only " + maxAmount + " cake(s) left");
                                cart.updateItem(key, detail);
                                isAllValid = false;
                            }
                        }
                        if (isAllValid) {
                            // Process payment
                            TblPaymentStatusDao paymentStatusDao = new TblPaymentStatusDao();
                            int paymentStatusId = paymentStatusDao.getStatusId("Pending");
                            Timestamp date = new Timestamp(new Date().getTime());
                            TblOrderDao orderDao = new TblOrderDao();
                            TblOrderDto orderDto = new TblOrderDto(userId, cart.getTotal(), date, name, address, phone, methodId, paymentStatusId);
                            String orderId = orderDao.addNewOrder(orderDto);
                            if (orderId != null) {
                                TblOrderDetailDao orderDetailDao = new TblOrderDetailDao();
                                boolean result = orderDetailDao.addAProductToOrder(orderId, items);
                                if (result) {
                                    result = productDao.updateProductQuantity(items);
                                    if (result) {
                                        if (methodName.equals("MOMO")) {
                                            url = PROCESS_MOMO;
                                            request.setAttribute("ORDER_ID", orderId);
                                            request.setAttribute("ORDER_TOTAL", cart.getTotal());
                                        } else if (methodName.equals("Ngan Luong")) {
                                            url = PROCESS_NGAN_LUONG;
                                            request.setAttribute("ORDER_ID", orderId);
                                            request.setAttribute("ORDER_AMOUNT", cart.getAmount());
                                            request.setAttribute("ORDER_TOTAL", cart.getTotal());
                                        } else {
                                            request.setAttribute("CHECKOUT_MSG", "Your order is accepted with id: " + orderId);
                                            session.removeAttribute("CART");
                                            url = SUCCES_PAGE;
                                        }
                                    } else {
                                        paymentStatusId = paymentStatusDao.getStatusId("Error");
                                        orderDao.updatePaymentStatusOrder(orderId, paymentStatusId);
                                    }

                                } else {
                                    paymentStatusId = paymentStatusDao.getStatusId("Error");
                                    orderDao.updatePaymentStatusOrder(orderId, paymentStatusId);
                                }
                            }
                        } else {
                            url = OUT_STOCK_PAGE;
                        }
                    }
                }
            }
            if (url.equals(ERROR_PAGE)) {
                request.setAttribute("CHECKOUT_ERR", "Error while checking out your cart, please try again");
            }
        } catch (SQLException | NamingException ex) {
            LOGGER.error(ex.getMessage());
            url = SERVER_ERR_PAGE;
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

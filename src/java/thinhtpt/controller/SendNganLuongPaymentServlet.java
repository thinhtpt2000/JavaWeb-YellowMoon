/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author ThinhTPT
 */
@WebServlet(name = "SendNganLuongPaymentServlet", urlPatterns = {"/SendNganLuongPaymentServlet"})
public class SendNganLuongPaymentServlet extends HttpServlet {

    private final String ERROR_PAGE = "errorPage";

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
            String orderCode = (String) request.getAttribute("ORDER_ID");
            if (orderCode != null) {
                ServletContext context = request.getServletContext();
                String merchantSiteCode = context.getInitParameter("merchant-site-code");
                String returnUrl = "http://localhost:8084/YellowMoonShop/processNganLuongPayment";
                String receiver = "tthinh2128@gmail.com";
                String transactionInfo = "Thanh toán bằng Ngân lượng";
                String price = (long) request.getAttribute("ORDER_TOTAL") + "";
                String currency = "vnd";
                String quantity = (int) request.getAttribute("ORDER_AMOUNT") + "";
                String tax = "0";
                String discount = "0";
                String feeCal = "0";
                String feeShipping = "0";
                String orderDescription = "Bánh trung thu Yellow Moon";

                String email = null;
                HttpSession session = request.getSession(false);
                if (session != null) {
                    email = (String) session.getAttribute("USER_NAME");
                    session.setAttribute("ORDER_ID", orderCode);
                }
                if (email == null) {
                    email = "";
                }
                String name = request.getParameter("txtName");
                String phone = request.getParameter("txtPhone");
                String address = request.getParameter("txtAddress");
                String buyerInfo = String.format("%s "
                        + "*|* %s "
                        + "*|* %s "
                        + "*|* %s", name, email, phone, address);

                String affiliateCode = "";

                String securePass = context.getInitParameter("secure-pass");

                String data = merchantSiteCode
                        + " " + returnUrl
                        + " " + receiver
                        + " " + transactionInfo
                        + " " + orderCode
                        + " " + price
                        + " " + currency
                        + " " + quantity
                        + " " + tax
                        + " " + discount
                        + " " + feeCal
                        + " " + feeShipping
                        + " " + orderDescription
                        + " " + buyerInfo
                        + " " + affiliateCode
                        + " " + securePass;
                String secureCode = DigestUtils.md5Hex(data).toLowerCase();
                String cancelUrl = "http://localhost:8084/YellowMoonShop/processNganLuongCancel";

                url = String.format("https://sandbox.nganluong.vn:8088/nl35/checkout.php?"
                        + "merchant_site_code=%s"
                        + "&return_url=%s"
                        + "&receiver=%s"
                        + "&transaction_info=%s"
                        + "&order_code=%s"
                        + "&price=%s"
                        + "&currency=%s"
                        + "&quantity=%s"
                        + "&tax=%s"
                        + "&discount=%s"
                        + "&fee_cal=%s"
                        + "&fee_shipping=%s"
                        + "&order_description=%s"
                        + "&buyer_info=%s"
                        + "&affiliate_code=%s"
                        + "&lang=vi"
                        + "&secure_code=%s"
                        + "&cancel_url=%s", merchantSiteCode, URLEncoder.encode(returnUrl, StandardCharsets.UTF_8.toString()),
                        receiver, URLEncoder.encode(transactionInfo, StandardCharsets.UTF_8.toString()),
                        orderCode, price, currency, quantity, tax, discount, feeCal, feeShipping,
                        URLEncoder.encode(orderDescription, StandardCharsets.UTF_8.toString()),
                        URLEncoder.encode(buyerInfo, StandardCharsets.UTF_8.toString()),
                        affiliateCode, secureCode, URLEncoder.encode(cancelUrl, StandardCharsets.UTF_8.toString()));
            }
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.controller;

import com.mservice.allinone.models.CaptureMoMoResponse;
import com.mservice.allinone.models.PayGateResponse;
import com.mservice.allinone.processor.allinone.CaptureMoMo;
import com.mservice.allinone.processor.allinone.PaymentResult;
import com.mservice.allinone.processor.allinone.QueryStatusTransaction;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.utils.LogUtils;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author ThinhTPT
 */
@WebServlet(name = "SendMomoPaymentServlet", urlPatterns = {"/SendMomoPaymentServlet"})
public class SendMomoPaymentServlet extends HttpServlet {

    private final String ERROR_PAGE = "errorPage";
    private final Logger LOGGER = Logger.getLogger(SendMomoPaymentServlet.class);

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
        PrintWriter out = response.getWriter();

        String url = ERROR_PAGE;
        Environment env = null;

        try {
            String orderId = (String) request.getAttribute("ORDER_ID");
            if (orderId != null) {
                LogUtils.init();
                String requestId = String.valueOf(System.currentTimeMillis());
                long total = (long) request.getAttribute("ORDER_TOTAL");

                String orderInfo = "Buy moon cake(s)";
                String returnURL = "http://localhost:8084/YellowMoonShop/processMomoPayment";
                String notifyURL = "http://localhost:8084/YellowMoonShop/processMomoPayment";

                env = Environment.selectEnv("dev", Environment.ProcessType.PAY_GATE);

                // Remember to change the IDs at enviroment.properties file
                // Payment Method- Phương thức thanh toán
                CaptureMoMoResponse captureMoMoResponse = CaptureMoMo.process(env, orderId, requestId, Long.toString(total), orderInfo, returnURL, notifyURL, "");
                if (captureMoMoResponse != null) {
                    url = captureMoMoResponse.getPayUrl();
                }
                // Transaction Query
                QueryStatusTransaction.process(env, orderId, requestId);
            }

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            response.sendRedirect(url);
            if (env != null) {
                // Process Payment Result - Xử lý kết quả thanh toán
                PaymentResult.process(env, new PayGateResponse());
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

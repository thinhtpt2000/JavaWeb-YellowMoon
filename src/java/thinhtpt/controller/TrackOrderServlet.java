/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import thinhtpt.tblorder.TblOrderDao;
import thinhtpt.tblorder.TblOrderDto;
import thinhtpt.tblorderdetail.TblOrderDetailDao;
import thinhtpt.tblorderdetail.TblOrderDetailDtoExtends;
import thinhtpt.tblpaymentmethod.TblPaymentMethodDao;
import thinhtpt.tblpaymentstatus.TblPaymentStatusDao;
import thinhtpt.tblproducts.TblProductsDao;
import thinhtpt.utils.ValidationHelper;

/**
 *
 * @author ThinhTPT
 */
@WebServlet(name = "TrackOrderServlet", urlPatterns = {"/TrackOrderServlet"})
public class TrackOrderServlet extends HttpServlet {

    private final String ERROR_PAGE = "trackOrderPage";
    private final String SUCCESS_PAGE = "trackOrderPage";

    private final Logger LOGGER = Logger.getLogger(TrackOrderServlet.class);

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

        String orderId = request.getParameter("txtOrderId");

        String url = ERROR_PAGE;
        boolean result = false;

        try {
            if (orderId != null && orderId.trim().length() > 0 && ValidationHelper.isValidUUID(orderId)) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    String userIdString = (String) (session.getAttribute("USER_ID") + "");
                    int userId = -1;
                    if (userIdString != null && ValidationHelper.isValidNumber(userIdString)) {
                        userId = Integer.parseInt(userIdString);
                    }
                    if (userId > 0) {
                        TblOrderDao orderDao = new TblOrderDao();
                        TblOrderDto orderDto = orderDao.getOrderInfo(orderId, userId);
                        if (orderDto != null) {
                            request.setAttribute("ORDER_INFO", orderDto);

                            TblPaymentStatusDao statusDao = new TblPaymentStatusDao();
                            String statusName = statusDao.getStatusName(orderDto.getPaymentStatus());
                            request.setAttribute("STATUS_NAME", statusName);

                            TblPaymentMethodDao methodDao = new TblPaymentMethodDao();
                            String methodName = methodDao.getMethodNameById(orderDto.getPaymentMethod());
                            request.setAttribute("METHOD_NAME", methodName);

                            TblOrderDetailDao detailDao = new TblOrderDetailDao();
                            List<TblOrderDetailDtoExtends> listDetail = detailDao.getListCartDetailByOrderId(orderId);
                            if (listDetail != null) {
                                TblProductsDao productDao = new TblProductsDao();
//                                TblProductStatusDao productStatusDao = new TblProductStatusDao();
//                                int productStatusId = productStatusDao.getStatusIdByName("Active");
                                for (TblOrderDetailDtoExtends dto : listDetail) {
//                                    String productName = productDao.getProductName(dto.getProductId(), productStatusId);
                                    String productName = productDao.getProductName(dto.getProductId());
                                    dto.setProductName(productName);
                                }
                            }
                            request.setAttribute("LIST_ITEMS", listDetail);
                            url = SUCCESS_PAGE;
                            result = true;
                        }
                    }
                }
            }
        } catch (SQLException | NamingException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            if (!result) {
                request.setAttribute("ERROR_MSG", "Order is not found, please check your order id");
            }
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

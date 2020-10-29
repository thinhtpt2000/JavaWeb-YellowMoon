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
import org.apache.log4j.Logger;
import thinhtpt.tblcategories.TblCategoriesDao;
import thinhtpt.tblcategories.TblCategoriesDto;
import thinhtpt.tblproducts.TblProductsDao;
import thinhtpt.tblproducts.TblProductsDto;
import thinhtpt.tblproductstatus.TblProductStatusDao;
import thinhtpt.tblproductstatus.TblProductStatusDto;
import thinhtpt.utils.ValidationHelper;

/**
 *
 * @author ThinhTPT
 */
@WebServlet(name = "RenderUpdatePageServlet", urlPatterns = {"/RenderUpdatePageServlet"})
public class RenderUpdatePageServlet extends HttpServlet {

    private final String ERROR_PAGE = "errorAdminPage";
    private final String UPDATE_PAGE = "updatePage";

    private final Logger LOGGER = Logger.getLogger(RenderUpdatePageServlet.class);

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

        String id = request.getParameter("txtId");
        int productId = 0;
        String url = ERROR_PAGE;

        try {
            if (id != null && ValidationHelper.isValidNumber(id)) {
                productId = Integer.parseInt(id);
            }
            TblProductsDao productDao = new TblProductsDao();
            TblProductsDto productDto = productDao.getProductById(productId);
            if (productDto != null) {
                request.setAttribute("PRODUCT_INFO", productDto);
                TblCategoriesDao cateDao = new TblCategoriesDao();
                List<TblCategoriesDto> listCategories = cateDao.getAllCategories();
                request.setAttribute("LIST_CATEGORIES", listCategories);
                TblProductStatusDao statusDao = new TblProductStatusDao();
                List<TblProductStatusDto> listStatus = statusDao.getAllProductStatus();
                request.setAttribute("LIST_STATUS", listStatus);
                url = UPDATE_PAGE;
            }
        } catch (SQLException | NamingException ex) {
            LOGGER.error(ex.getMessage());
            url = ERROR_PAGE;
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

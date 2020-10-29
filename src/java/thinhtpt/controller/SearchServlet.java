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
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import thinhtpt.tblproducts.TblProductsDao;
import thinhtpt.tblproducts.TblProductsDto;
import thinhtpt.tblproductstatus.TblProductStatusDao;
import thinhtpt.utils.PagingHelper;
import thinhtpt.utils.ValidationHelper;

/**
 *
 * @author ThinhTPT
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private final String SEARCH_PAGE = "searchPage";

    private final Logger LOGGER = Logger.getLogger(SearchServlet.class);

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
        String cateId = request.getParameter("txtCategory");
        String txtMinPrice = request.getParameter("txtMinPrice");
        String txtMaxPrice = request.getParameter("txtMaxPrice");
        String page = request.getParameter("page");

        String url = SEARCH_PAGE;

        try {
            int pageInt = 1;
            if (page != null && ValidationHelper.isValidNumber(page)) {
                pageInt = Integer.parseInt(page);
            }

            TblProductStatusDao statusDao = new TblProductStatusDao();
            int statusId = statusDao.getStatusIdByName("Active");

            if (name == null) {
                name = "";
            }

            int categoryId = -1;
            if (cateId != null && ValidationHelper.isValidNumber(cateId)) {
                categoryId = Integer.parseInt(cateId);
            }

            int minPrice = -1;
            if (txtMinPrice != null && ValidationHelper.isValidNumber(txtMinPrice)) {
                minPrice = Integer.parseInt(txtMinPrice);
            }
            int maxPrice = -1;
            if (txtMaxPrice != null && ValidationHelper.isValidNumber(txtMaxPrice)) {
                maxPrice = Integer.parseInt(txtMaxPrice);
            }

            TblProductsDao productDao = new TblProductsDao();

            // Paging
            // Get total
            Timestamp time = new Timestamp(new Date().getTime());
            int total = productDao.countProductBySearchParams(name, categoryId, minPrice, maxPrice, statusId, time);
            // Get number of page
            int numOfPage = (int) Math.ceil((double) total / 20);

            if (pageInt < 1 || pageInt > numOfPage) {
                pageInt = 1;
            }

            List<Integer> listPages = PagingHelper.getListNumPage(pageInt, numOfPage);
            request.setAttribute("LIST_PAGES", listPages);
            request.setAttribute("NUM_OF_PAGE", numOfPage);
            request.setAttribute("CURR_PAGE", pageInt);

            // Get list of products
            List<TblProductsDto> listProducts
                    = productDao.searchProducts(name, categoryId, minPrice, maxPrice, statusId, pageInt, time);
            request.setAttribute("LIST_PRODUCTS", listProducts);
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

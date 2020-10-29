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
import thinhtpt.tblproducts.TblProductsDao;
import thinhtpt.tblproducts.TblProductsDto;
import thinhtpt.utils.PagingHelper;
import thinhtpt.utils.ValidationHelper;

/**
 *
 * @author ThinhTPT
 */
@WebServlet(name = "LoadCakesServlet", urlPatterns = {"/LoadCakesServlet"})
public class LoadCakesServlet extends HttpServlet {

    private final String ADMIN_PAGE = "adminPage";
    
    private final Logger LOGGER = Logger.getLogger(LoadCakesServlet.class);

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

        String page = request.getParameter("page");

        String url = ADMIN_PAGE;

        try {
            int pageInt = 1;
            if (page != null && ValidationHelper.isValidNumber(page)) {
                pageInt = Integer.parseInt(page);
            }

            TblProductsDao productDao = new TblProductsDao();

            // Paging
            // Get total
            int total = productDao.countNumberOfProduct();

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
            List<TblProductsDto> listProducts = productDao.getAllProducts(pageInt);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.filter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import thinhtpt.tblorder.TblOrderInfoError;
import thinhtpt.tblpaymentmethod.TblPaymentMethodDao;
import thinhtpt.utils.ValidationHelper;

/**
 *
 * @author ThinhTPT
 */
public class CheckoutFilter implements Filter {

    private static final boolean debug = true;
    
    private final String ERROR_PAGE = "checkoutError";
    private final String CART_PAGE = "viewCart";
    
    private final Logger LOGGER = Logger.getLogger(CheckoutFilter.class);

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public CheckoutFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("CheckoutFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("CheckoutFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (debug) {
            log("CheckoutFilter:doFilter()");
        }

        doBeforeProcessing(request, response);

        Throwable problem = null;

        String name = request.getParameter("txtName");
        String phone = request.getParameter("txtPhone");
        String address = request.getParameter("txtAddress");
        String method = request.getParameter("txtMethod");

        TblOrderInfoError errors = new TblOrderInfoError();
        boolean foundErr = false;
        String url = ERROR_PAGE;

        try {
            String cartInfo = (String) request.getAttribute("CART_INFO");
            if (cartInfo == null) {
                if (name == null) {
                    foundErr = true;
                    errors.setNameErr("Name cannot be empty");
                } else {
                    if (name.trim().length() > 150) {
                        foundErr = true;
                        errors.setNameErr("Name must be from less than 150 chars");
                    }
                }
                if (phone == null) {
                    foundErr = true;
                    errors.setPhoneErr("Phone cannot be empty");
                } else {
                    if (!ValidationHelper.isValidPhoneNumber(phone)) {
                        foundErr = true;
                        errors.setPhoneErr("Phone number is invalid");
                    }
                }
                if (address == null) {
                    foundErr = true;
                    errors.setAddressErr("Address cannot be empty");
                } else {
                    if (address.trim().length() > 500) {
                        foundErr = true;
                        errors.setAddressErr("Address must be from less than 500 chars");
                    }
                }
                if (method == null) {
                    foundErr = true;
                    errors.setMethodErr("Please select a method");
                } else {
                    if (ValidationHelper.isValidNumber(method)) {
                        int methodId = Integer.parseInt(method);
                        TblPaymentMethodDao methodDao = new TblPaymentMethodDao();
                        String methodName = methodDao.getMethodNameById(methodId);
                        if (methodName == null) {
                            foundErr = true;
                            errors.setMethodErr("Please select a valid method");
                        }
                    } else {
                        foundErr = true;
                        errors.setMethodErr("Please select a valid method");
                    }
                }
                if (foundErr) {
                    request.setAttribute("ERRORS", errors);
                    HttpServletRequest req = (HttpServletRequest) request;
                    RequestDispatcher rd = req.getRequestDispatcher(url);
                    rd.forward(request, response);
                } else {
                    chain.doFilter(request, response);
                }
            } else {
                HttpServletRequest req = (HttpServletRequest) request;
                RequestDispatcher rd = req.getRequestDispatcher(CART_PAGE);
                rd.forward(request, response);
            }
        } catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
            LOGGER.error(t.getMessage());
        }

        doAfterProcessing(request, response);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("CheckoutFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("CheckoutFilter()");
        }
        StringBuffer sb = new StringBuffer("CheckoutFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}

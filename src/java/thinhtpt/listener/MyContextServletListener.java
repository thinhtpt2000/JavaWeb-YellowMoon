/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thinhtpt.listener;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import thinhtpt.utils.FileHelper;

/**
 * Web application lifecycle listener.
 *
 * @author ThinhTPT
 */
public class MyContextServletListener implements ServletContextListener {

    private final Logger LOGGER = Logger.getLogger(MyContextServletListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServletContext context = sce.getServletContext();

            String siteMapFile = context.getInitParameter("site-map-location");
            String siteMapPath = context.getRealPath("") + File.separator + siteMapFile;

            Map<String, String> siteMap = FileHelper.getSiteMapFromTextFile(siteMapPath);
            context.setAttribute("SITE_MAP", siteMap);

            String log4jFile = context.getInitParameter("log4j-config-location");
            String log4jPath = context.getRealPath("") + File.separator + log4jFile;

            PropertyConfigurator.configure(log4jPath);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}

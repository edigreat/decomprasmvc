package mx.unam.posgrado.servlet;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * Se encarga de  iniciar la base de datos
 * Realizar una conexion y ponerla en el contexto
 * del servlet, se encarga de la desconexion una vez
 * terminada la desconexion.
 * @author heriberto
 */
public class BaseDatosListener implements ServletContextListener {

	@Autowired
    private DataSource dataSource;
    @Autowired
    ServletContext context; 
    private static final Logger logger = Logger.getLogger(BaseDatosListener.class);
    
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	logger.debug("Iniciando Listener base de datos");
    	WebApplicationContextUtils
        .getRequiredWebApplicationContext(servletContextEvent.getServletContext())
        .getAutowireCapableBeanFactory()
        .autowireBean(this);
    	logger.debug("dataSource " + dataSource);
        /*String nombreBase = obtenerParametro(servletContext, "nombreBase", "jdbc:h2:~/test"); 
        String user = obtenerParametro(servletContext, "usuariodb", "sa");
        String password = obtenerParametro(servletContext, "passworddb", "sa");
        String puerto = obtenerParametro(servletContext, "puertodb", "9123");

        try {
             org.h2.Driver.load();
             server = Server.createTcpServer( "-tcpPort", puerto,"-webAllowOthers",
                    "-tcpAllowOthers");
             server.start();
             conn = DriverManager.getConnection(nombreBase, user,password);
            servletContext.setAttribute("connection", conn);
            
        } catch (SQLException ex) {
            System.out.println("Error al iniciar la base de datos " + ex.toString());
           
        }*/
    }

  
    
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	logger.debug("Cerrando base de datos" );
    	logger.debug("dataSource " +dataSource );
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                logger.debug("quitando el registro jdbc driver: " + driver);
            } catch (SQLException ew) {
                logger.warn("Error quitando el registro del  driver " + ew.getMessage());
            }

        }
    }
}
 



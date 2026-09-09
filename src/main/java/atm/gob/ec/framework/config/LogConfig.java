/**
 *
 * @author erik.flores
 */

package atm.gob.ec.framework.config;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import atm.gob.ec.framework.exception.TechnicalException;
import atm.gob.ec.framework.exception.ValidationException;

public class LogConfig {    

    private static final Logger logger = LogManager.getLogger(LogConfig.class);    
    
    public static LoggerContext configureLogging(String strLog4j2Path) {
        
        LoggerContext context;
        try {
            
            context = (LoggerContext) LogManager.getContext(false);
            
            URL url = new File(AppConfig.getSystemDir() + strLog4j2Path).toURI().toURL();
            
            if (url == null) 
                throw new ValidationException("log4j2.xml no existe: " );
            
            logger.info("Loading log4j2 config -> {}", url.getFile());
            
            context.setConfigLocation(url.toURI());

            return context;

        } catch (RuntimeException | MalformedURLException | URISyntaxException ex) {
            throw new TechnicalException("Error configurando Log4j2", ex);
        }
    } 
    
    public static LoggerContext configure(AppConfig appConfig) {
        
        LoggerContext context;
        try {
            
            context = (LoggerContext) LogManager.getContext(false);
            
            URL url = new File(AppConfig.getSystemDir() + appConfig.get("LOG4J2.SUBDIRECTORY")).toURI().toURL();
            
            if (url == null) 
                throw new ValidationException("log4j2.xml no existe: " );
            
            logger.info("Loading log4j2 config -> {}", url.getFile());
            
            context.setConfigLocation(url.toURI());

            return context;

        } catch (RuntimeException | MalformedURLException | URISyntaxException ex) {
            throw new TechnicalException("Error configurando Log4j2", ex);
        }
    }    


}

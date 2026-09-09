/**
 *
 * @author erik.flores
 */

package atm.gob.ec.framework.config;

import java.io.FileInputStream;

import java.util.Properties;

public class AppConfig {

    private Properties props;

    public AppConfig(String strResopurcesPath) {
        this.props = new Properties();
        try (FileInputStream fis = new FileInputStream(getSystemDir() + "/resources/" + strResopurcesPath)) {
            props.load(fis);
        } catch (Exception e) {
            throw new RuntimeException("Error cargando configuración", e);
        }

    }

    public String get(String key) {
        return props.getProperty(key);
    }
    
    public static String getSystemDir(){
        
        String strRes = System.getProperty("user.dir").replaceAll("\\\\", "/");

        if (strRes.contains("file:"))
            strRes=strRes.substring(5);

        return strRes;
    }
     
}

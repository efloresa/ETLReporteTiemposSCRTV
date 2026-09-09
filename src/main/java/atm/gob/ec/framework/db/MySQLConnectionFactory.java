/**
 *
 * @author erik.flores
 */

package atm.gob.ec.framework.db;

import atm.gob.ec.framework.config.AppConfig;
import atm.gob.ec.framework.interfaces.ConnectionFactory;
import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnectionFactory extends AbstractConnectionFactory implements ConnectionFactory {
    
    public MySQLConnectionFactory(AppConfig config){
        super(config); 
    }            

    @Override
    public Connection getConnection() throws Exception {
        return DriverManager.getConnection(config.get("DB.MYSQLDATABASE"), 
                                            config.get("DB.MYSQLSUSER"), 
                                            crypto.decrypt(config.get("DB.MYSQLPASSWD"))
        ); 
    }
    
}

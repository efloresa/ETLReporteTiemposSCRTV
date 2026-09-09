/**
 *
 * @author erik.flores
 */

package atm.gob.ec.framework.db;

import atm.gob.ec.framework.config.AppConfig;
import atm.gob.ec.framework.interfaces.ConnectionFactory;
import java.sql.Connection;
import java.sql.DriverManager;

public class OracleConnectionFactory extends AbstractConnectionFactory implements ConnectionFactory {

    public OracleConnectionFactory(AppConfig config) {
        super(config);
    }

    @Override
    public Connection getConnection() throws Exception {        
        return DriverManager.getConnection(config.get("DB.ORACLEURL"), 
                                            config.get("DB.ORACLEUSER"), 
                                            crypto.decrypt(config.get("DB.ORACLEPASSWD"))
        );
    }
}


/**
 *
 * @author erik.flores
 */

package atm.gob.ec.framework.db;

import atm.gob.ec.framework.config.AppConfig;
import atm.gob.ec.framework.interfaces.ConnectionFactory;
import java.sql.Connection;
import java.sql.DriverManager;

public class SqlServerConnectionFactory extends AbstractConnectionFactory implements ConnectionFactory {

    public SqlServerConnectionFactory(AppConfig config) {
        super(config);
    }

    @Override
    public Connection getConnection() throws Exception {
        return DriverManager.getConnection(config.get("DB.MSSQLURL"), 
                                            config.get("DB.MSSQLSUSER"), 
                                            crypto.decrypt(config.get("DB.MSMSSQLPASSWD"))
        );
    }
}

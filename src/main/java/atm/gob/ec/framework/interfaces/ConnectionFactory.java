/**
 *
 * @author erik.flores
 */

package atm.gob.ec.framework.interfaces;

import java.sql.Connection;

public interface ConnectionFactory {

    Connection getConnection() throws Exception;
}


/**
 *
 * @author erik.flores
 */

package atm.gob.ec.framework.db;

import atm.gob.ec.framework.config.AppConfig;
import atm.gob.ec.framework.interfaces.CryptoService;
import atm.gob.ec.framework.security.AesCryptoService;

public abstract class AbstractConnectionFactory {

    protected final AppConfig config;
    protected final CryptoService crypto;
    private static String secret = System.getProperty("atm.crypto.key");

    protected AbstractConnectionFactory(AppConfig config) {
        this.config = config;
        this.crypto = new AesCryptoService(secret);
    }
    
}

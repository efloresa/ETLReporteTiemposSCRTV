/**
 *
 * @author erik.flores
 */

package atm.gob.ec.framework.interfaces;

public interface CryptoService {

    String encrypt(String value);

    String decrypt(String value);
}


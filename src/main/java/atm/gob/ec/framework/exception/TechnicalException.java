/**
 *
 * @author erik.flores
 */

package atm.gob.ec.framework.exception;

public class TechnicalException extends RuntimeException {

    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}

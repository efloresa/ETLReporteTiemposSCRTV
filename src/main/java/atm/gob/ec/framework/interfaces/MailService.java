/**
 *
 * @author erik.flores
 */

package atm.gob.ec.framework.interfaces;

public interface MailService {

    void send(String subject, String body, String attachment);
}

/**
 *
 * @author erik.flores
 */

package atm.gob.ec.framework.interfaces;

import java.util.List;

public interface Transformer<T> {
    List<T> transform(List<T> data);
}

/**
 *
 * @author erik.flores
 */

package atm.gob.ec.framework.interfaces;

import java.util.List;

public interface Loader<T> {
    
    int load(List<T> data);
    
}

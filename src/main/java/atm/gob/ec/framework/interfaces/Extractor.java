/**
 *
 * @author erik.flores
 */

package atm.gob.ec.framework.interfaces;

import java.time.LocalDate;

import java.util.List;

public interface Extractor<T> {

    List<T> extract(LocalDate fIni, LocalDate fFin);

}
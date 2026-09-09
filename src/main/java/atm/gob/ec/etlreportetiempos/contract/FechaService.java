/**
 *
 * @author erik.flores
 */

package atm.gob.ec.etlreportetiempos.contract;

import java.time.LocalDate;

public interface FechaService {

    LocalDate getMaxFechaCheckpoint();

}


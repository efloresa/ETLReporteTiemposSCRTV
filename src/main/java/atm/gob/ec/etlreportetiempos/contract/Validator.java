/**
 *
 * @author erik.flores
 */

package atm.gob.ec.etlreportetiempos.contract;

import atm.gob.ec.etlreportetiempos.entity.RangoFechas;

public interface Validator {

    RangoFechas resolve();
    
    void validate(RangoFechas rango);
}


/**
 *
 * @author erik.flores
 */

package atm.gob.ec.etlreportetiempos.transformer;

import atm.gob.ec.etlreportetiempos.entity.VTiempos;
import atm.gob.ec.framework.interfaces.Transformer;

import java.util.List;

public class IdentityTransformer implements Transformer<VTiempos> {
    @Override
    public List<VTiempos> transform(List<VTiempos> data) {
        return data;
    }
        
}


/**
 *
 * @author erik.flores
 */

package atm.gob.ec.etlreportetiempos.orchestrator;

import atm.gob.ec.etlreportetiempos.contract.Validator;
import atm.gob.ec.etlreportetiempos.entity.RangoFechas;
import atm.gob.ec.etlreportetiempos.entity.VTiempos;
import atm.gob.ec.framework.exception.ValidationException;
import atm.gob.ec.framework.execution.ETLResult;
import atm.gob.ec.framework.execution.ProcessStatus;
import atm.gob.ec.framework.interfaces.Extractor;
import atm.gob.ec.framework.interfaces.Loader;
import atm.gob.ec.framework.interfaces.Transformer;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EtlOrchestrator {

    private static final Logger logger = LogManager.getLogger(EtlOrchestrator.class);
    private final Extractor<VTiempos> extractor;
    private final Transformer<VTiempos> transformer;
    private final Loader<VTiempos> loader;
    private final Validator validator;

    public EtlOrchestrator(
            Extractor<VTiempos> extractor,
            Transformer<VTiempos> transformer,
            Loader<VTiempos> loader,
            Validator validator) {

        this.extractor = extractor;
        this.transformer = transformer;
        this.loader = loader;
        this.validator = validator;
    }

    public void run(String param) {
        
        long inicio = System.currentTimeMillis();
        logger.info("Iniciando proceso ETL");
        
        RangoFechas rango = validator.resolve();
        
        logger.info("Periodo a procesar: {} -> {}", rango.getFechaInicio(), rango.getFechaFin());

        List<VTiempos> data = extractor.extract(rango.getFechaInicio(), rango.getFechaFin());
        logger.info("Registros extraidos: {}", data.size());
        
        if (data.isEmpty()) {
            logger.warn("No existen registros para procesar");
            return;
        }

        if (data.isEmpty())
            return;

        List<VTiempos> transformed = transformer.transform(data);
        logger.info("Registros transformados: {}", transformed.size());

        loader.load(transformed);
        logger.info("Carga finalizada correctamente");
        
        long fin = System.currentTimeMillis();

        logger.info("Tiempo total proceso: {} ms", (fin - inicio));
        
    }
    
    public ETLResult run() {
        
        ETLResult result = new ETLResult();
        long lStart = System.currentTimeMillis();
        
        try {

            logger.info("Iniciando proceso ETL");
        
            RangoFechas rango = validator.resolve();
            logger.info("Periodo a procesar: {} -> {}", rango.getFechaInicio(), rango.getFechaFin());
            result.setFechaInicio(rango.getFechaInicio());
            result.setFechaFin(rango.getFechaFin());
            
            validator.validate(rango);
            logger.info("Validar rango: {} -> {}", rango.getFechaInicio(), rango.getFechaFin());
                        
            List<VTiempos> data = extractor.extract(rango.getFechaInicio(), rango.getFechaFin());
            logger.info("Registros extraidos: {}", data.size());
            result.setRecordsExtracted(data.size());

            if (data.isEmpty()) {
                result.setStatus(ProcessStatus.WARNING);
                result.setMessage("No existen registros para procesar");
                return result;
                //return new ETLResult(ProcessStatus.WARNING, "No existen registros para procesar", 0);
            }
            
            List<VTiempos> transformed = transformer.transform(data);
            logger.info("Registros transformados: {}", transformed.size());
            
            int loaded = loader.load(data);
            result.setRecordsLoaded(loaded);
            logger.info("Registros cargados {}", loaded);
            
            result.setStatus(ProcessStatus.SUCCESS);
            result.setMessage("Proceso ejecutado correctamente");

        } catch (ValidationException ex) {
            result.setStatus(ProcessStatus.WARNING);
            result.setMessage(ex.getMessage());
        } catch (Exception ex){
            result.setStatus(ProcessStatus.ERROR);
            result.setMessage(ex.getMessage());
        } finally{
             long lStop = System.currentTimeMillis(); 
             result.setDurationMs(lStop-lStart);
            logger.info("Tiempo total proceso: {} ms", result.getDurationMs());
        }
        return result;
    }
}


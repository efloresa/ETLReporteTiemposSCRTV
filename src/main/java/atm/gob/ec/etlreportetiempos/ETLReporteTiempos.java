/**
 *
 * @author erik.flores
 */

package atm.gob.ec.etlreportetiempos;

import atm.gob.ec.framework.config.AppConfig;
import atm.gob.ec.framework.config.LogConfig;
import atm.gob.ec.etlreportetiempos.contract.Validator;
import atm.gob.ec.etlreportetiempos.entity.VTiempos;
import atm.gob.ec.etlreportetiempos.envelope.MailEnvelope;
import atm.gob.ec.etlreportetiempos.extractor.SqlServerExtractor;
import atm.gob.ec.etlreportetiempos.loader.OracleLoader;
import atm.gob.ec.etlreportetiempos.orchestrator.EtlOrchestrator;
import atm.gob.ec.etlreportetiempos.transformer.IdentityTransformer;
import atm.gob.ec.etlreportetiempos.validator.FechaValidator;
import atm.gob.ec.framework.exception.ValidationException;
import atm.gob.ec.framework.execution.ETLResult;
import atm.gob.ec.framework.execution.ProcessStatus;
import atm.gob.ec.framework.mail.DefaultMailService;
import atm.gob.ec.framework.interfaces.Extractor;
import atm.gob.ec.framework.interfaces.Loader;
import atm.gob.ec.framework.interfaces.MailService;
import atm.gob.ec.framework.interfaces.Transformer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ETLReporteTiempos {
    
    private static final Logger logger = LogManager.getLogger(ETLReporteTiempos.class);

    public static void main(String[] args) {
        
        ETLResult result = null;
        MailService mailService = null; 
        MailEnvelope formatter = null;
        
        try {
                        
            AppConfig config = new AppConfig("resourcesATM.properties");            
            LogConfig.configure(config);
            
            logger.info("ETL iniciado");
            
            Extractor<VTiempos> extractor = new SqlServerExtractor(config);

            Transformer<VTiempos> transformer = new IdentityTransformer();

            Loader<VTiempos> loader = new OracleLoader(config);

            Validator validator = new FechaValidator(config);
            
            mailService = new DefaultMailService(config);
            
            formatter = new MailEnvelope();
            
            result = new ETLResult();
            
            EtlOrchestrator etl = new EtlOrchestrator(
                    extractor,
                    transformer,
                    loader,
                    validator
                );

            result = etl.run();           
            
            logger.info(result.getMessage());

        } catch (ValidationException ex) {
            result.setStatus(ProcessStatus.WARNING);
            result.setMessage(ex.getMessage());
            logger.warn(ex.getMessage());
        } catch (Exception ex) {
            result.setStatus(ProcessStatus.ERROR);
            result.setMessage(ex.getMessage());
            logger.error("Error ETL", ex);
        } finally {
            try {
                if (mailService != null)                     
                    mailService.send(formatter.buildSubject(result),formatter.buildBody(result), "");
            } catch (Exception ex) {
                logger.error("Error enviando mail", ex);
            }

            System.exit(result.getExitCode().getCode());
        }
    }
}


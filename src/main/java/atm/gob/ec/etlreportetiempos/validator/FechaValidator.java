/**
 *
 * @author erik.flores
 */

package atm.gob.ec.etlreportetiempos.validator;

import atm.gob.ec.etlreportetiempos.contract.FechaService;
import atm.gob.ec.etlreportetiempos.contract.Validator;
import atm.gob.ec.etlreportetiempos.entity.RangoFechas;
import atm.gob.ec.etlreportetiempos.service.OracleFechaService;
import atm.gob.ec.framework.config.AppConfig;
import atm.gob.ec.framework.exception.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FechaValidator implements Validator {

    private static final Logger logger = LogManager.getLogger(FechaValidator.class);
    private final FechaService oraFechaServ;
    private final AppConfig config;
    
    public FechaValidator(AppConfig config) {
        this.config = config;
        this.oraFechaServ = new OracleFechaService(config);
    }

    @Override
    public RangoFechas resolve() {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        LocalDate fUltimoRegistro = oraFechaServ.getMaxFechaCheckpoint();
        logger.info("fUltReg {}", fUltimoRegistro);

        LocalDate fHoy = LocalDate.now();
        logger.info("fHoy {}", fHoy);

        String strFechaInicio = config.get("PARAM.FECHA_INICIO_PROCESO");
        logger.info("strIni {}", strFechaInicio);

        String strFechaFin = config.get("PARAM.FECHA_FIN_PROCESO");
        logger.info("strFin {}", strFechaFin);

        LocalDate fIni = (strFechaInicio == null || strFechaInicio.trim().isEmpty()) ? fUltimoRegistro.plusDays(1) : LocalDate.parse(strFechaInicio, fmt);
        logger.info("fIni {}", fIni);

        LocalDate fFin = (strFechaFin == null || strFechaFin.trim().isEmpty()) ? fHoy.minusDays(1) : LocalDate.parse(strFechaFin, fmt);
        logger.info("fFin {}", fFin);
        
        boolean automatico = (strFechaInicio == null || strFechaInicio.trim().isEmpty()) && (strFechaFin == null || strFechaFin.trim().isEmpty());

        
        return new RangoFechas(fIni, fFin, fUltimoRegistro, automatico);
    }

    @Override
    public void validate(RangoFechas rango) {

        LocalDate fHoy = LocalDate.now();

        LocalDate fUltReg = rango.getfechaUltimoRegistro();

        LocalDate fIni = rango.getFechaInicio();

        LocalDate fFin = rango.getFechaFin();

        if (fIni.isAfter(fFin)) {
            if (rango.isAutomatico()) 
                throw new ValidationException("El proceso ya se encuentra actualizado");
            
            throw new ValidationException("Fecha fin menor a fecha inicio");
        }
        
        if (fFin.equals(fHoy))
            throw new ValidationException("Fecha fin no puede ser hoy");

        if (fIni.isBefore(fUltReg))
            throw new ValidationException("Fecha inicio menor a ultimo registro");

        if (fFin.isBefore(fUltReg))
            throw new ValidationException("Fecha fin menor a ultimo registro");
        
    }
    
}


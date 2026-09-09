/**
 *
 * @author erik.flores
 */

package atm.gob.ec.framework.execution;

import java.time.LocalDate;

public class ETLResult {

    private ProcessStatus status;
    private String message;
    private int recordsExtracted;
    private int recordsLoaded;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private long durationMs;
    
    public ETLResult() {
        
    }
    
    public void setStatus(ProcessStatus status) {
        this.status = status;
    }
    
    public ProcessStatus getStatus() {
        return status;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
    public int getRecordsExtracted() {
        return recordsExtracted;
    }

    public void setRecordsExtracted(int recordsExtracted) {
        this.recordsExtracted = recordsExtracted;
    }

    public int getRecordsLoaded() {
        return recordsLoaded;
    }

    public void setRecordsLoaded(int recordsLoaded) {
        this.recordsLoaded = recordsLoaded;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public long getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(long durationMs) {
        this.durationMs = durationMs;
    }
    
    public ExitCode getExitCode() {

        switch (status) {

            case SUCCESS:
                return ExitCode.SUCCESS;

            case WARNING:
                return ExitCode.WARNING;

            default:
                return ExitCode.ERROR;
        }
    }
}


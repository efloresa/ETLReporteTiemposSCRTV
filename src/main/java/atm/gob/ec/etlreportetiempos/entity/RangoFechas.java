/**
 *
 * @author erik.flores
 */

package atm.gob.ec.etlreportetiempos.entity;

import java.time.LocalDate;

public class RangoFechas {

    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final LocalDate fechaUltimoRegistro;
    private boolean automatico;

    public RangoFechas(LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaUltimoRegistro, boolean automatico) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaUltimoRegistro = fechaUltimoRegistro;
        this.automatico = automatico;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public LocalDate getfechaUltimoRegistro() {
        return fechaUltimoRegistro;
    }
    
    public boolean isAutomatico(){
        return automatico;
    }
    
    @Override
    public String toString() {
        return "Fecha Inicio: " + fechaInicio + " Fecha Fin: " + fechaFin + "Fecha Ultimo Registro: " + fechaUltimoRegistro;
    }
}


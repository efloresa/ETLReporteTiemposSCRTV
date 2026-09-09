/**
 *
 * @author erik.flores
 */

package atm.gob.ec.etlreportetiempos.entity;

import java.sql.RowId;

import java.time.LocalDateTime;

public class VTiempos {
    
    private String PLACA;
    private Integer VISITA;
    private LocalDateTime  Fecha_Checkpoint;
    private LocalDateTime Tomar_ESOUT;
    private LocalDateTime Califica;
    private LocalDateTime Envio_Resultados;
    private Integer ID_ORDEN_SERVICIO;
    private RowId rowID;
    
    public VTiempos(){
        
    }

    public String getPLACA() {
        return PLACA;
    }

    public void setPLACA(String param) {
        PLACA = param;
    }

    public Integer getVISITA() {
        return VISITA;
    }

    public void setVISITA(Integer param) {
        VISITA = param;
    }

    public LocalDateTime getFecha_Checkpoint() {
        return Fecha_Checkpoint;
    }

    public void setFecha_Checkpoint(LocalDateTime param) {
        Fecha_Checkpoint = param;
    }

    public LocalDateTime getTomar_ESOUT() {
        return Tomar_ESOUT;
    }

    public void setTomar_ESOUT(LocalDateTime param) {
        Tomar_ESOUT = param;
    }

    public LocalDateTime getCalifica() {
        return Califica;
    }

    public void setCalifica(LocalDateTime param) {
        Califica = param;
    }

    public LocalDateTime getEnvio_Resultados() {
        return Envio_Resultados;
    }

    public void setEnvio_Resultados(LocalDateTime param) {
        Envio_Resultados = param;
    }

    public Integer getID_ORDEN_SERVICIO() {
        return ID_ORDEN_SERVICIO;
    }

    public void setID_ORDEN_SERVICIO(Integer param) {
        this.ID_ORDEN_SERVICIO = param;
    }

    public RowId getROWID() {
        return rowID;
    }

    public void setROWID(RowId param) {
        this.rowID = param;
    }
    
}



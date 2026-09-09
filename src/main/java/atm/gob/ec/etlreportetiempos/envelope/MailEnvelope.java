/**
 *
 * @author erik.flores
 */

package atm.gob.ec.etlreportetiempos.envelope;

import atm.gob.ec.framework.execution.ETLResult;
import atm.gob.ec.framework.util.DurationFormatter;

public class MailEnvelope {

    public String buildSubject(ETLResult result) {

        return "[" + result.getStatus() + "] ETL SCRTV";
    }
    
    public String buildBody(ETLResult result) {

        String periodo;

        if (result.getFechaInicio() != null
                &&
            result.getFechaFin() != null
                &&
            result.getFechaInicio().isAfter(result.getFechaFin())) {

            periodo = "SIN REGISTROS PENDIENTES";

        } else {

            periodo =
                    result.getFechaInicio()
                    + " - "
                    + result.getFechaFin();
        }

        String detalleProceso;

        switch (result.getStatus()) {

            case SUCCESS:

                detalleProceso =
                        "Proceso ejecutado correctamente.<br/>"
                        + result.getRecordsLoaded()
                        + " registros fueron cargados en AXIS.";

                break;

            case WARNING:

                detalleProceso =
                        result.getMessage();

                break;

            default:

                detalleProceso =
                        "El proceso finalizó con errores.<br/>"
                        + result.getMessage();

                break;
        }

        return
                "<html>"
                //+ "<body style='font-family: Arial, Helvetica, sans-serif;font-size: 12px;color:#1F2937;'>"
                + "<body>"

                + "<h3 style='color:#0F172A;'>Informe ETL SCRTV → AXIS</h3>"

                + "<hr/>"

                + "<table style='border-collapse: collapse;'>"

                + "<tr>"
                + "<td><b>Estado:</b></td>"
                + "<td>" + result.getStatus() + "</td>"
                + "</tr>"

                + "<tr>"
                + "<td><b>Periodo:</b></td>"
                + "<td>" + periodo + "</td>"
                + "</tr>"

                + "<tr>"
                + "<td><b>Registros recuperados:</b></td>"
                + "<td>" + result.getRecordsExtracted() + "</td>"
                + "</tr>"

                + "<tr>"
                + "<td><b>Registros cargados:</b></td>"
                + "<td>" + result.getRecordsLoaded() + "</td>"
                + "</tr>"

                + "<tr>"
                + "<td><b>Duración:</b></td>"
                + "<td>"
                + DurationFormatter.format(result.getDurationMs())
                + "</td>"
                + "</tr>"

                + "</table>"

                + "<br/>"

                + "<div style='padding:10px;"
                + "background-color:#F3F4F6;"
                + "border-left:4px solid #9CA3AF;'>"

                + detalleProceso

                + "</div>"

                + "<br/><br/>"

                + "<i>"
                + "Este es un mensaje informativo; "
                + "por favor no responder."
                + "</i>"

                + "<br/><br/>"

                + "Atentamente,<br/>"
                + "ETL SCRTV"

                + "</body>"
                + "</html>";
    }
}

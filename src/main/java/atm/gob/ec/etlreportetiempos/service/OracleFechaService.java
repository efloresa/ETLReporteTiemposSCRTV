/**
 *
 * @author erik.flores
 */

package atm.gob.ec.etlreportetiempos.service;

import atm.gob.ec.etlreportetiempos.contract.FechaService;
import atm.gob.ec.framework.config.AppConfig;
import atm.gob.ec.framework.db.OracleConnectionFactory;
import atm.gob.ec.framework.interfaces.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OracleFechaService implements FechaService {

    private final AppConfig config;
    private final ConnectionFactory connectionFactory;

    public OracleFechaService(AppConfig config) {
        this.config = config;
        this.connectionFactory = new OracleConnectionFactory(config);
    }

    @Override
    public LocalDate getMaxFechaCheckpoint() {
        
        String sql = config.get("SQL.Q2");

        try (
            Connection conn = connectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery() ) {

            if (rs.next()) {
                String fecha = rs.getString("FECHA_ULTIMO_REGISTRO");
                return LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            }

            throw new RuntimeException("No existe fecha ultimo registro");

        } catch (Exception ex) {
            throw new RuntimeException("Error consultando ultimo registro", ex);
        }
    }
}


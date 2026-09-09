/**
 *
 * @author erik.flores
 */

package atm.gob.ec.etlreportetiempos.extractor;

import atm.gob.ec.etlreportetiempos.entity.VTiempos;
import atm.gob.ec.framework.config.AppConfig;
import atm.gob.ec.framework.db.SqlServerConnectionFactory;
import atm.gob.ec.framework.exception.TechnicalException;
import atm.gob.ec.framework.interfaces.ConnectionFactory;
import atm.gob.ec.framework.interfaces.Extractor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

public class SqlServerExtractor implements Extractor<VTiempos> {

    private final AppConfig config;
    private final ConnectionFactory connectionFactory;
    
    public SqlServerExtractor(AppConfig config) {
        this.config = config;
        this.connectionFactory = new SqlServerConnectionFactory(config);
    }
    
    @Override
    public List<VTiempos> extract(LocalDate fIni, LocalDate fFin) {

        List<VTiempos> list = new ArrayList<>();

        String sql = config.get("SQL.Q1")
                + " WHERE vt.Fecha_Checkpoint >= CAST(? AS DATE)"
                + " AND vt.Fecha_Checkpoint < CAST(? AS DATE)";

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(fIni));
            ps.setDate(2, Date.valueOf(fFin.plusDays(1)));

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    VTiempos r = new VTiempos();

                    r.setPLACA(rs.getString("placa"));
                    r.setVISITA(rs.getInt("Visita"));
                    r.setFecha_Checkpoint(getNullableDateTime(rs, "Fecha_Checkpoint"));
                    r.setTomar_ESOUT(getNullableDateTime(rs, "Tomar_ESOUT"));
                    r.setCalifica(getNullableDateTime(rs, "Califica"));
                    r.setEnvio_Resultados(getNullableDateTime(rs, "Envio_Resultados"));
                    list.add(r);
                }
            }

        } catch (Exception ex) {
            throw new TechnicalException("Error extrayendo datos desde SQL Server", ex);
        }

        return list;
    }

    private LocalDateTime getNullableDateTime(ResultSet rs, String column) throws Exception {
        Timestamp ts = rs.getTimestamp(column);
        return (ts != null) ? ts.toLocalDateTime().withNano(0) : null;
    }
}

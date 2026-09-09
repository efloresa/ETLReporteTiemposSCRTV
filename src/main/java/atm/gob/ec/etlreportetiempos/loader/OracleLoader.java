/**
 *
 * @author erik.flores
 */

package atm.gob.ec.etlreportetiempos.loader;

import atm.gob.ec.etlreportetiempos.entity.VTiempos;
import atm.gob.ec.framework.config.AppConfig;
import atm.gob.ec.framework.db.OracleConnectionFactory;
import atm.gob.ec.framework.exception.TechnicalException;
import atm.gob.ec.framework.interfaces.ConnectionFactory;
import atm.gob.ec.framework.interfaces.Loader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;

import java.time.LocalDateTime;

import java.util.List;

public class OracleLoader implements Loader<VTiempos> {

    private final AppConfig config;
    private final ConnectionFactory connectionFactory;

    public OracleLoader(AppConfig config) {
        this.config = config; 
        this.connectionFactory = new OracleConnectionFactory(config);
    }
    
    @Override
    public int load(List<VTiempos> data) {
        int totalInsertados = 0;
        String sql = config.get("SQL.Q3");

        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            int count = 0;

            for (VTiempos r : data) {

                ps.setString(1, r.getPLACA());
                ps.setInt(2, r.getVISITA());

                setTimestampOrNull(ps, 3, r.getFecha_Checkpoint());
                setTimestampOrNull(ps, 4, r.getTomar_ESOUT());
                setTimestampOrNull(ps, 5, r.getCalifica());
                setTimestampOrNull(ps, 6, r.getEnvio_Resultados());
                ps.addBatch();
                totalInsertados++;
                count++;

                if (count % 1000 == 0) {
                    ps.executeBatch();
                    conn.commit();
                }
            }
            
            ps.executeBatch();
            conn.commit();

        } catch (Exception ex) {
            throw new TechnicalException("Error cargando datos en Oracle", ex);
        }
        return totalInsertados;
    }

    private void setTimestampOrNull(PreparedStatement ps, int index, LocalDateTime value) throws Exception {
        if (value == null) {
            ps.setNull(index, Types.TIMESTAMP);
        } else {
            ps.setTimestamp(index, Timestamp.valueOf(value));
        }
    }
}


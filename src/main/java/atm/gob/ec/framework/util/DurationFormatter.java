/**
 *
 * @author erik.flores
 */

package atm.gob.ec.framework.util;

public class DurationFormatter {

    private static final long SECOND = 1000;
    private static final long MINUTE = 60 * SECOND;
    private static final long HOUR = 60 * MINUTE;
    private static final long DAY = 24 * HOUR;
    private static final long MONTH = 30 * DAY;
    private static final long YEAR = 365 * DAY;

    private DurationFormatter() {
    }

    public static String format(long millis) {

        if (millis < 0) {
            throw new IllegalArgumentException(
                    "La duracion no puede ser negativa");
        }

        long years = millis / YEAR;
        millis %= YEAR;

        long months = millis / MONTH;
        millis %= MONTH;

        long days = millis / DAY;
        millis %= DAY;

        long hours = millis / HOUR;
        millis %= HOUR;

        long minutes = millis / MINUTE;
        millis %= MINUTE;

        long seconds = millis / SECOND;

        StringBuilder sb = new StringBuilder();

        append(sb, years, "año", "años");
        append(sb, months, "mes", "meses");
        append(sb, days, "día", "días");
        append(sb, hours, "hora", "horas");
        append(sb, minutes, "minuto", "minutos");
        append(sb, seconds, "segundo", "segundos");

        if (sb.length() == 0) {
            return "0 segundos";
        }

        return sb.toString().trim();
    }

    private static void append(StringBuilder sb,
                               long value,
                               String singular,
                               String plural) {

        if (value <= 0) {
            return;
        }

        sb.append(value)
          .append(" ")
          .append(value == 1 ? singular : plural)
          .append(" ");
    }
}

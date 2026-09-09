/**
 *
 * @author erik.flores
 */

package atm.gob.ec.framework.execution;

public enum ExitCode {

    SUCCESS(0),
    ERROR(1),
    WARNING(2);

    private final int code;

    ExitCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

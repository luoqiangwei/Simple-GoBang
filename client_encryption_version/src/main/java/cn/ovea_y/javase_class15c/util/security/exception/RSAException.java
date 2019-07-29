package cn.ovea_y.javase_class15c.util.security.exception;
/**
 * @author QiangweiLuo
 */
public class RSAException extends Exception {
    public RSAException() {
        super();
    }

    /**
     *
     * @param message
     */
    public RSAException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public RSAException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause
     */
    public RSAException(Throwable cause) {
        super(cause);
    }
}

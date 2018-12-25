package ar.com.hjg.pngj;

public class PngjException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public PngjException(String message, Throwable cause) {
        super(message, cause);
    }

    public PngjException(String message) {
        super(message);
    }

    public PngjException(Throwable cause) {
        super(cause);
    }
}

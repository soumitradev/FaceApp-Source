package ar.com.hjg.pngj;

public class PngjExceptionInternal extends RuntimeException {
    private static final long serialVersionUID = 1;

    public PngjExceptionInternal(String message, Throwable cause) {
        super(message, cause);
    }

    public PngjExceptionInternal(String message) {
        super(message);
    }

    public PngjExceptionInternal(Throwable cause) {
        super(cause);
    }
}

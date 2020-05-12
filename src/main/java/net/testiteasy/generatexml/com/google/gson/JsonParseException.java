package net.testiteasy.generatexml.com.google.gson;

public class JsonParseException extends RuntimeException {
    static final long serialVersionUID = -4086729973971783390L;

    /**
     * Creates exception with the specified message. If you are wrapping another exception, consider
     * using {@link #JsonParseException(String, Throwable)} instead.
     *
     * @param msg error message describing a possible cause of this exception.
     */
    public JsonParseException(String msg) {
        super(msg);
    }

    /**
     * Creates exception with the specified message and cause.
     *
     * @param msg error message describing what happened.
     * @param cause root exception that caused this exception to be thrown.
     */
    public JsonParseException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Creates exception with the specified cause. Consider using
     * {@link #JsonParseException(String, Throwable)} instead if you can describe what happened.
     *
     * @param cause root exception that caused this exception to be thrown.
     */
    public JsonParseException(Throwable cause) {
        super(cause);
    }
}

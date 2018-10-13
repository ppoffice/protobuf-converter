package net.badata.protobuf.converter.exception;

/**
 * Exception notifies that errors when invoking methods of a protobuf or domain object.
 *
 * @author ppoffice
 */
public class InvocationException extends ReflectiveOperationException {

	/**
	 * Constructs a new InvocationException with {@code null} as its detail message.
	 * The cause is not initialized.
	 */
	public InvocationException() {
		super();
	}

	/**
	 * Constructs a new InvocationException with the specified detail message.  The
	 * cause is not initialized.
	 *
	 * @param message the detail message. The detail message is saved for
	 *                later retrieval by the {@link #getMessage()} method.
	 */
	public InvocationException(String message) {
		super(message);
	}

	/**
	 * Constructs a new InvocationException with the specified detail message and
	 * cause.  <p>Note that the detail message associated with
	 * {@code cause} is <i>not</i> automatically incorporated in
	 * this exception's detail message.
	 *
	 * @param message the detail message (which is saved for later retrieval
	 *                by the {@link #getMessage()} method).
	 * @param cause   the cause (which is saved for later retrieval by the
	 *                {@link #getCause()} method).  (A <tt>null</tt> value is
	 *                permitted, and indicates that the cause is nonexistent or
	 *                unknown.)
	 */
	public InvocationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new InvocationException with the specified cause and a detail
	 * message of <tt>(cause==null ? null : cause.toString())</tt> (which
	 * typically contains the class and detail message of <tt>cause</tt>).
	 *
	 * @param cause the cause (which is saved for later retrieval by the
	 *              {@link #getCause()} method).  (A <tt>null</tt> value is
	 *              permitted, and indicates that the cause is nonexistent or
	 *              unknown.)
	 */
	public InvocationException(Throwable cause) {
		super(cause);
	}
}

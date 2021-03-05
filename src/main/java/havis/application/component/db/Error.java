package havis.application.component.db;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Provides a container for database access errors.
 */
public class Error extends JavaScriptObject {

	/**
	 * Provides an enumeration for database access errors.
	 */
	public enum Code {

		UNKNOWN, DATABASE, VERSION, TOO_LARGE, QUOTA, SYNTAX, CONSTRAINT, TIMEOUT;

		public static Code valueOf(short code) {
			switch (code) {
			case 0:
				return UNKNOWN;
			case 1:
				return DATABASE;
			case 2:
				return VERSION;
			case 3:
				return TOO_LARGE;
			case 4:
				return QUOTA;
			case 5:
				return SYNTAX;
			case 6:
				return CONSTRAINT;
			case 7:
				return TIMEOUT;
			default:
				return null;
			}
		}
	}

	/**
	 * Creates a new instance
	 */
	protected Error() {
	}

	public static Error createInstance() {
		return (Error) createObject();
	}

	/**
	 * Gets the error code
	 * 
	 * @return The code
	 */
	public final native short getCode() /*-{
		return this.code;
	}-*/;

	/**
	 * Sets the error code
	 * 
	 * @param code
	 *            The code
	 */
	public final native short setCode(short code) /*-{
		this.code = code;
	}-*/;

	/**
	 * Gets the error message text
	 * 
	 * @return The message text
	 */
	public final native String getMessage() /*-{
		return this.message;
	}-*/;

	/**
	 * Sets the error message text
	 * 
	 * @param message
	 *            The message text
	 */
	public final native String setMessage(String message) /*-{
		this.message = message;
	}-*/;
}
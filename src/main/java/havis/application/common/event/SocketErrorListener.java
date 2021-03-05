package havis.application.common.event;

public interface SocketErrorListener {

	/**
	 * Called when the remote service is not reachable, must block and return
	 * the new URL to use
	 * 
	 * @param origin
	 *            the origin URL of the remote service
	 * @return the new URL to use
	 */
	String onError(String origin);
}

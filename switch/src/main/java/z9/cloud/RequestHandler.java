package z9.cloud;

import java.net.Socket;

/**
 * RequestHandler interface
 */
public interface RequestHandler {
	/**
	 * Handles the incoming request
	 * 
	 * @param socket    The socket communication back to the client
	 */
	public void handleRequest(Socket socket);
}
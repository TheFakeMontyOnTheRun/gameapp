/**
 * 
 */
package br.odb.gameapp;

/**
 * @author monty
 * 
 */
// TODO This should be used in conjunction with @FileServerDelegate
public class SaveStateNotFound extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5571211116702934068L;

	/**
	 * 
	 */
	public SaveStateNotFound() {
	}

	/**
	 * @param message
	 */
	public SaveStateNotFound(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SaveStateNotFound(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SaveStateNotFound(String message, Throwable cause) {
		super(message, cause);
	}
}

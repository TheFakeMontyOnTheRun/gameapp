/**
 * 
 */
package br.odb.gameapp;

import br.odb.utils.FileServerDelegate;

/**
 * @author monty
 * 
 */
public interface ApplicationClient {
	void setClientId(String id);

	void clear();

	void printWarning(String msg);

	void printError(String msg);

	void printVerbose(String msg);

	String requestFilenameForSave();

	String requestFilenameForOpen();

	String getInput(String msg);

	int chooseOption(String question, String[] options);

	FileServerDelegate getFileServer();

	void printNormal(String string);

	void alert(String string);

	void playMedia(String uri, String alt);

	void sendQuit();
	
	boolean isConnected();

	String openHTTP(String url);

	void shortPause();
}

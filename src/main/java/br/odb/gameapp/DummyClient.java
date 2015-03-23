package br.odb.gameapp;

import br.odb.utils.FileServerDelegate;

public class DummyClient implements ApplicationClient {

	@Override
	public void setClientId(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void printWarning(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void printError(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void printVerbose(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public String requestFilenameForSave() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String requestFilenameForOpen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInput(String msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int chooseOption(String question, String[] options) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FileServerDelegate getFileServer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void printNormal(String string) {
		// TODO Auto-generated method stub

	}

	@Override
	public void alert(String string) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playMedia(String uri, String alt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendQuit() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String openHTTP(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void shortPause() {
		// TODO Auto-generated method stub

	}

}

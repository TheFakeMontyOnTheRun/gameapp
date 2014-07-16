package br.odb.gameapp;



public abstract class UserMetaCommandLineAction extends UserCommandLineAction {
	
	private ConsoleApplication application;

	public UserMetaCommandLineAction( ConsoleApplication application ) {
		this.application = application;
	}
	
	public ConsoleApplication getApplication() {
		return application;
	}
}

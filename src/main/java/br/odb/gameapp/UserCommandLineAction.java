package br.odb.gameapp;


public abstract class UserCommandLineAction {
	
	public class InvalidCommandRunException extends Exception {
		
	}
	
	public abstract void run( ConsoleApplication application, String operand ) throws Exception;

	@Override
	public abstract String toString();

    public abstract int requiredOperands();

	public abstract String getHelp();
}

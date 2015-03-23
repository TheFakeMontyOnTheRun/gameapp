package br.odb.gameapp;


public abstract class UserCommandLineAction {
	
	public class InvalidCommandRunException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5986983118464310069L;
		
	}
	
	public abstract void run( ConsoleApplication application, String operand ) throws Exception;

	@Override
	public abstract String toString();

    public abstract int requiredOperands();

	public abstract String getHelp();
}

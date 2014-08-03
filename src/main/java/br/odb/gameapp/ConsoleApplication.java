/**
 *
 */
package br.odb.gameapp;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import br.odb.utils.FileServerDelegate;
import br.odb.utils.Utils;

/**
 * @author monty
 * 
 */
public abstract class ConsoleApplication extends Thread implements
		FileServerDelegate, Runnable {

	ApplicationClient client;
	final HashMap<String, UserCommandLineAction> commands = new HashMap<String, UserCommandLineAction>();
	private String appName;
	private String authorName;
	private String licenseName;
	private int yearRelease;
	public boolean continueRunning;
	private GameUpdateDelegate gameUpdateDelegate;
	final ArrayList<String> cmdHistory = new ArrayList<String>();
	private boolean saveInHistory = true;
	
	public void runCmd(String entry) throws Exception {

		if (entry == null || entry.length() == 0) {
			return;
		}

		String[] tokens = Utils.tokenize(entry.trim(), " ");
		String operator = tokens[0];
		String operand = entry.replace(operator, "").trim();

		UserCommandLineAction cmd = getCommand(tokens[0]);

		if (cmd != null) {

			cmd.run(this, operand);
		}

	}	

	public ConsoleApplication() {
		init();
	}

	public UserCommandLineAction getCommand(String cmdName) {
		return commands.get(cmdName);
	}

	public HashMap<String, UserCommandLineAction> getCommandList() {
		return commands;
	}

	public void update(long ms) {
	}

	protected void registerCommand(UserCommandLineAction cmd) {
		commands.put(cmd.toString(), cmd);
	}

	public class ConsoleClient implements ApplicationClient {

		Scanner in;

		public ConsoleClient() {
			in = new Scanner(System.in);
			in.useDelimiter("\n");
		}

		@Override
		public void printWarning(final String msg) {
			printNormal("*WARNING* " + msg);
		}

		@Override
		public void printError(final String msg) {
			printNormal("*ERROR* " + msg);
		}

		@Override
		public void printVerbose(final String msg) {
			printNormal("*...* " + msg);
		}

		@Override
		public String requestFilenameForSave() {

			return getInput("Enter file name to save:");
		}

		@Override
		public String requestFilenameForOpen() {
			printNormal("File for open?");
			return "";
		}

		@Override
		public String getInput(String msg) {
			System.out.println(msg);
			System.out.print(">");
			return in.nextLine();
		}

		@Override
		public int chooseOption(final String question, final String[] options) {

			printNormal(question);

			int optionNum = 1;

			for (String option : options) {
				printNormal(optionNum + ") " + option);
				++optionNum;
			}

			String option = "-1";

			option = getInput("Please select one option [ 1 - "
					+ (options.length) + " ] ?");

			return Integer.parseInt(option);
		}

		@Override
		public FileServerDelegate getFileServer() {
			return null;
		}

		@Override
		public void printNormal(final String msg) {
			System.out.println(msg);
		}

		void destroy() {
			in.close();
		}

		@Override
		public void setClientId(final String id) {
			// TODO Do we still need this? Looks like it. Maybe check the idea
			// with Telnet clients.
		}

		@Override
		public void alert(final String string) {
			printNormal("*" + string + "*");

		}

		@Override
		public void playMedia(final String uri, final String alt) {
			printNormal("#" + alt + "#");

		}

		@Override
		public void clear() {
			printNormal("\n\n\n\n\n");
		}

		@Override
		public void sendQuit() {
			continueRunning = false;
		}

		@Override
		public boolean isConnected() {

			return continueRunning;
		}

		@Override
		public String openHTTP(String url) {

			return ConsoleApplication.defaultJavaHTTPGet(url, this);
		}

		@Override
		public void shortPause() {
			try {
				Thread.sleep(750);
			} catch (InterruptedException e) {
			}
		}
	}

	public ConsoleApplication setApplicationClient(
			final ApplicationClient client) {
		this.client = client;
		return this;
	}

	public ConsoleApplication printPreamble() {
		if (client != null) {
			client.printNormal(getApplicationName() + " - " + getAuthor()
					+ " - copyright " + getYearRelease() + " - licensed under "
					+ getLicenseName() + ". ");
		}

		return this;
	}

	public int getYearRelease() {
		return yearRelease;
	}

	public ConsoleApplication setReleaseYear(final int year) {
		this.yearRelease = year;
		return this;
	}

	private String getApplicationName() {

		return appName;
	}

	public ApplicationClient getClient() {
		return client;
	}

	public ConsoleApplication setAppName(final String appName) {
		this.appName = appName;
		return this;
	}

	public ConsoleApplication setAuthorName(final String authorName) {
		this.authorName = authorName;
		return this;
	}

	public ConsoleApplication setLicenseName(final String licenseName) {
		this.licenseName = licenseName;
		return this;
	}

	public static String defaultJavaHTTPGet(String url, ApplicationClient client) {

		String response = "";
		BufferedReader in = null;
		String inputLine;
		URLConnection conn = null;
		URL myURL = null;

		try {
			myURL = new URL(url);

			conn = myURL.openConnection();
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			while ((inputLine = in.readLine()) != null) {

				response += inputLine;
			}
			in.close();
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {
			client.printError("Something went wrong with your request");
		}

		return response;
	}

	public ConsoleApplication init() {
		return this;
	}

	public void printPrompt() {
	}

	public void setShouldSaveHistory(boolean shouldSave) {
		this.saveInHistory = shouldSave;
	}

	public void onDataEntered(final String data) {
		if (saveInHistory) {

			cmdHistory.add(data);
		}
	}



	public void waitForInput() {

		String entry;
		continueRunning = true;

		while (continueRunning) {
			if (client != null) {
				showUI();
				entry = getInputFromClient("----");
				onDataEntered(entry);
			}
		}
	}

	public String getInputFromClient(String string) {

		return getClient().getInput(string);
	}

	public ConsoleApplication showUI() {

		if (gameUpdateDelegate != null) {
			this.gameUpdateDelegate.update();
		}

		return this;
	}

	@Override
	public void run() {

		if (client != null) {

			printPreamble();
			waitForInput();

			if (!client.isConnected()) {
				doQuit();
				client = null;
			}
		}
	}

	protected abstract void doQuit();

	private String getAuthor() {

		return authorName;
	}

	private String getLicenseName() {

		return licenseName;
	}

	void setArgs(final String[] args) {
	}

	public void sendData(String data) {

		if (data != null && data.length() > 0) {

			onDataEntered(data);
		}
		showUI();
	}

	public List<String> getCommandHistory() {

		return cmdHistory;
	}

	@Override
	public InputStream openAsInputStream(final String filename)
			throws IOException {
		return null;
	}

	@Override
	public InputStream openAsset(final String filename) throws IOException {
		// TODO Check what to do with this.
		return null;
	}

	@Override
	public InputStream openAsset(final int resId) throws IOException {
		// TODO You shithead. Implement this.
		return null;
	}

	@Override
	public OutputStream openAsOutputStream(final String filename)
			throws IOException {

		return new FileOutputStream( filename );
	}

	public ConsoleApplication createDefaultClient() {
		setApplicationClient(new ConsoleClient());
		return this;
	}

	public ConsoleApplication setGameUpdateDelegate(
			final GameUpdateDelegate delegate) {

		this.gameUpdateDelegate = delegate;

		return this;
	}

	public UserCommandLineAction[] getAvailableCommands() {
		return commands.values().toArray(new UserCommandLineAction[] {});
	}

}

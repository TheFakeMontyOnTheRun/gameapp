package br.odb.gameapp;

public class ScheduledEvent implements Runnable {

	public long timeToGoOff;
	private Runnable runnable;
	public boolean wentOff;
	public ApplicationClient client;

	protected ScheduledEvent(Runnable runnable, long time) {
		this.runnable = runnable;
		this.timeToGoOff = time;
	}

	@Override
	public void run() {

		wentOff = true;
		if (runnable != null) {
			runnable.run();
		}
	}
}

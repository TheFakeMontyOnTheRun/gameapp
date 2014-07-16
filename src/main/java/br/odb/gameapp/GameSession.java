package br.odb.gameapp;


public abstract class GameSession {

	public abstract void reset();

	public abstract void pause();

	public abstract void start();

	public abstract void save();

	public abstract void restore() throws SaveStateNotFound;

	public abstract void clearSavedSession();

	public abstract void stop();
}

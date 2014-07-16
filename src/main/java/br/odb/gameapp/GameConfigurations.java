package br.odb.gameapp;

public class GameConfigurations {

	public static GameConfigurations getInstance() {
		if (instance == null)
			instance = new GameConfigurations();
		return instance;
	}

	private static GameConfigurations instance = null;

	public GameConfigurations() {

	}
}

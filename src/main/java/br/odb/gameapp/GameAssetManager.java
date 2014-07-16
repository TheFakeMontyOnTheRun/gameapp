package br.odb.gameapp;




public abstract class GameAssetManager {

	public abstract MediaPlayerWrapper createMediaPlayer(int resId);

	public abstract boolean isSilentModeEnabled();

	//public abstract ObjMesh meshForName(String string);

	/*
	 * Concerns: - Keep shared resources as much as possible - Tries to preload
	 * some of the resources
	 */

}

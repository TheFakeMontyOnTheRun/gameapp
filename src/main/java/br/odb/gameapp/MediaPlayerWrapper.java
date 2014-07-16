package br.odb.gameapp;

public abstract class MediaPlayerWrapper {

	public abstract void setLooping(boolean b);

	public abstract void start();
	
	public abstract void start( float l, float r );

	public abstract boolean isPlaying();

	public abstract void stop();

	public abstract void setVolume(float f, float g);
}

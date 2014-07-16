package br.odb.gameapp;

import java.util.ArrayList;

import br.odb.utils.math.Vec3;

/***
 * 
 * @author monty
 * 
 */
public class GameAudioManager {
	// ------------------------------------------------------------------------------------------------------------
	public boolean active = true;

	// SoundPool soundPool;

	public boolean isActive() {
		return active;
	}

	/**
	 * 
	 */
	private static GameAudioManager instance;
	/**
	 * 
	 */
	private ArrayList<PositionalMediaPlayer> audioPlayers = new ArrayList<PositionalMediaPlayer>();
	/**
	 * 
	 */
	private GameAudioListener listener;

	// ------------------------------------------------------------------------------------------------------------
	/**
	 * 
	 * @return
	 */
	public static GameAudioManager getInstance() {

		if (instance == null)
			instance = new GameAudioManager();

		return instance;
	}

	// public PositionalMediaPlayer getFor(Vec3 pos, int resId ) {
	//
	// int id = soundPool.load(
	// PlayGameActivity.getInstance().getApplicationContext(), resId, 1 );
	// PositionalMediaPlayer pmp = PositionalMediaPlayer.getFor( pos, id );
	// return pmp;
	// }

	public GameAudioManager() {
		// soundPool = new SoundPool( 8, AudioManager.STREAM_MUSIC, 0 );
	}

	// ------------------------------------------------------------------------------------------------------------
	/**
	 * 
	 * @param pmp
	 */
	public void registerPlayer(PositionalMediaPlayer pmp) {
		audioPlayers.add(pmp);
	}

	// ------------------------------------------------------------------------------------------------------------
	/**
	 * 
	 * @param listener
	 */
	public void setListener(GameAudioListener listener) {
		this.listener = listener;
	}

	// ------------------------------------------------------------------------------------------------------------
	/**
	 * 
	 */
	public void update() {

		if (!active)
			return;


		if (listener != null) {
			
			Vec3 position = listener.getPosition();
			Vec3 direction = listener.getDirectionVector();

			for (PositionalMediaPlayer pmp : audioPlayers) {

				if (pmp.isPlaying()
						&& pmp.getPosition() != listener.getPosition())
					pmp.update(position, direction);
			}
		}

	}

	// ------------------------------------------------------------------------------------------------------------
	public void updateFor(PositionalMediaPlayer pmp) {

		if (!active)
			return;

		if (listener != null && pmp.getPosition() != listener.getPosition())
			pmp.update(listener.getPosition(), listener.getDirectionVector());
	}

	// public void playId(int soundId, float leftVolume, float rightVolume, int
	// priority, int loop, float rate ) {
	//
	// soundPool.play( soundId, leftVolume, rightVolume, priority, loop, rate);
	// }
	//
	// public void stopId(int soundId) {
	//
	// soundPool.stop( soundId );
	// }
	//
	// public void unload(int soundId) {
	//
	// soundPool.unload( soundId );
	// }
}

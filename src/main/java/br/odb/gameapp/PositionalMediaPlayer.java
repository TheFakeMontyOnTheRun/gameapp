package br.odb.gameapp;

import br.odb.utils.math.Vec3;

public class PositionalMediaPlayer {

	public static final float LISTENING_CUTOFF_DISTANCE = 150.0f;
	private MediaPlayerWrapper mp;
	private Vec3 pos;
	private float dist;

	public static PositionalMediaPlayer getFor(Vec3 pos, int resId,
			GameAssetManager factory) {

		PositionalMediaPlayer pmp = null;
		MediaPlayerWrapper mp = null;

		if (GameAudioManager.getInstance().isActive()) {
			mp = factory.createMediaPlayer(resId);
		}

		pmp = new PositionalMediaPlayer(mp, pos, factory);

		return pmp;
	}

	public PositionalMediaPlayer(MediaPlayerWrapper mp, Vec3 pos,
			GameAssetManager factory) {

		if (mp == null || !GameAudioManager.getInstance().isActive())
			return;

		this.pos = new Vec3(pos);
		this.mp = mp;
		mp.setLooping(false);
	}

	public void playContinuously() {

		if (mp == null || !GameAudioManager.getInstance().isActive())
			return;

		GameAudioManager.getInstance().updateFor(this);

		if (dist > LISTENING_CUTOFF_DISTANCE) {
			this.stop();
			return;
		}

		mp.start();
		mp.setLooping(true);
	}

	public void playUnique() {

		if (mp == null || !GameAudioManager.getInstance().isActive())
			return;

		GameAudioManager.getInstance().updateFor(this);

		if (dist > LISTENING_CUTOFF_DISTANCE) {
			this.stop();
			return;
		}

		if (!mp.isPlaying()) {

			mp.setLooping(false);
			mp.start();
		}
	}

	public void stop() {

		if (mp == null || !GameAudioManager.getInstance().isActive())
			return;

		mp.stop();
	}

	public void update(Vec3 listener, Vec3 heading) {

		if (mp == null || !GameAudioManager.getInstance().isActive())
			return;

		Vec3 newPos = pos.sub(listener);
		dist = Math.abs(newPos.length());

		if (dist > LISTENING_CUTOFF_DISTANCE) {
			this.stop();
			return;
		}

		float distanceFactor = (1.0f - (dist / LISTENING_CUTOFF_DISTANCE));
		Vec3 direction = heading.normalized();
		Vec3 dirBy90 = new Vec3(direction.z, direction.y, -direction.x);
		newPos = newPos.normalized();
		float dot = dirBy90.dotProduct(newPos);
		float lenPos = newPos.length();
		float left = lenPos * dot;
		float right = lenPos * dot;

		if (left > 0)
			left = 0;
		else
			left = -left;

		if (right < 0)
			right = 0;

		// Log.d( "derelict", "audio:" + ( (1.0f - left) * distanceFactor ) +
		// ", " + ( (1.0f - right) * distanceFactor ) );

		// mp.setVolume( 1.0f, 1.0f );
		mp.setVolume((1.0f - left) * distanceFactor, (1.0f - right)
				* distanceFactor);

	}

	public void setPosition(Vec3 position) {

		pos.set(position);
	}

	public boolean isPlaying() {

		if (mp == null || !GameAudioManager.getInstance().isActive())
			return false;

		return mp.isPlaying();
	}

	public void bindPosition(Vec3 position) {

		pos = position;
	}

	public Vec3 getPosition() {

		return pos;
	}

	public void destroy() {

		mp = null;
		pos = null;
	}
}

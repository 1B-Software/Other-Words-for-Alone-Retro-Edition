package robatortas.code.files.core.sound;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundEngine {
	
	public static final SoundEngine entityHurt = new SoundEngine("/sound/sound/hit.wav");
	public static final SoundEngine take = new SoundEngine("/sound/sound/take1.wav");
	public static final SoundEngine dead = new SoundEngine("/sound/sound/dead.wav");
	public static final SoundEngine walk = new SoundEngine("/sound/sound/walk.wav");
	public static final SoundEngine walkGrass = new SoundEngine("/sound/sound/walkGrass.wav");
	public static final SoundEngine swim = new SoundEngine("/sound/sound/swim.wav");
	public static final SoundEngine splash = new SoundEngine("/sound/sound/splash.wav");
//	public static final SoundEngine music1 = new SoundEngine("/sound/music/185.wav");
	public static final SoundEngine music1 = new SoundEngine("/sound/sound/break.wav");
	public static final SoundEngine breakTile = new SoundEngine("/sound/sound/break.wav");
	public static final SoundEngine select = new SoundEngine("/sound/sound/select3.wav");
	public static final SoundEngine enter = new SoundEngine("/sound/sound/enter.wav");
	public static final SoundEngine drop = new SoundEngine("/sound/sound/drop.wav");
	
	public static final SoundEngine whiteNoise = null;
	
	//private AudioClip clip;
	//Clip clip = AudioSystem.getClip();
	public Clip clip;
	private FloatControl gainControl;
	
	/* ALERT!:
	 * Sound engine limitation...
	 * Need to play first sound at the start of the game,
	 * since the first played sound freezes the game for a moment.
	 * This makes the play experience exactly the same at the cost of some wait time at the start.
	 * 
	 * First limitation in our engines!
	 * And it's not our fault, it's just Java's sound class being shit.
	 */
	
	private SoundEngine(String path) {
		try {
			//Decoding Audio process
			InputStream audioSrc = SoundEngine.class.getResourceAsStream(path);
			//Buffers encoded audio source from path to the system memory
			InputStream bufferedin = new BufferedInputStream(audioSrc);
			//Makes the inputStream the buffered audio
			AudioInputStream audioStream1 = AudioSystem.getAudioInputStream(bufferedin);
			//Gets the format of the audioStream1
			AudioFormat baseFormat = audioStream1.getFormat();
			//Decodes the baseFormat.
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
														baseFormat.getSampleRate(),
														16,
														baseFormat.getChannels(),
														baseFormat.getChannels() * 2,
														baseFormat.getSampleRate(),
														false);
			
			//Decoded Audio (output)
			AudioInputStream decodedAudioStream = AudioSystem.getAudioInputStream(decodeFormat, audioStream1);
			
			clip = AudioSystem.getClip();
			//Opens Decoded Audio
			clip.open(decodedAudioStream);
			
			//Gain Control for Audio
			gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		if(clip == null)
			return;
			
			stop();
			
			clip.setFramePosition(0);
			
			
			/*Fixes not playing bug.
			 * Forces it to play if not running when called
			 * Keeps trying to play it until played.
			 */
			while(!clip.isRunning()) {
				clip.start();
			}
	}
	
	public void stop() {
		if(clip.isRunning()) {
			clip.stop();
		}
	}
	
	public void close() {
		stop();
		clip.drain();
		clip.close();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		play();
	}
	
	public void volume(float value) {
		gainControl.setValue(value);
	}
	
	public boolean isRunning() {
		return clip.isRunning();
	}
}
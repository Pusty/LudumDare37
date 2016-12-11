package me.game.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class SoundFile {

	Sound sound;
	
	boolean loop;
	public SoundFile(FileHandle file, boolean loop) {
		this.loop=loop;
		sound = Gdx.audio.newSound(file);
	}
	Thread thread;
	public Thread getThis(){return thread;}
	public synchronized  void start(){
		run();
	}
	public synchronized  void run() {
			if(loop)
				sound.loop();
			else
				sound.play();
	}
	
	public synchronized void close() {
		sound.stop();
		sound.dispose();
	}
//	public AudioClip getClip(){
//		return mainclip;
//	}

}

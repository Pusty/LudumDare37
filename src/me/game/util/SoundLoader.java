package me.game.util;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.files.FileHandle;

public class SoundLoader {

	 HashMap<String, SoundFile> list;
     boolean muted=false;
     static SoundLoader instance = null;
	public SoundLoader() {
		list = new HashMap<String, SoundFile>();
		SoundLoader.instance = this;
	}
	public void mute(){muted=true;}
	public void unmute(){muted=false;}

	
	public void addSound(String name, FileHandle file,boolean l) {
		list.put(name, new SoundFile(file,l));
	}
	
	
//	public AudioClip getClip(String name){
//		return list.get(name).getClip();
//	}
	public static void close() {
		if(instance!=null)
		for(Entry<String,SoundFile> sound:instance.list.entrySet()) {
			sound.getValue().close();
		}
	}
	public SoundFile getSound(String name){
		return list.get(name);
	}
	
	public synchronized  void playClip(String name){
	     if(list.get(name) != null){
	    	 if(!muted)
	    		 list.get(name).start();
	   
	     }
	}

}

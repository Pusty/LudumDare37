package me.game.render;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.JsonValue;


public class RawAnimation {
	String[] imageNames;
	Integer[] frameDelay;
	int currentFrame;
	int currentTick;
	public RawAnimation(int frames){
		imageNames = new String[frames];
		frameDelay = new Integer[frames];
		currentFrame=0;
		currentTick=0;
	}
	
	public RawAnimation(JsonValue obj) {
		if(obj.has("textures")) {
			List<String> list = new ArrayList<String>();
			for(JsonValue string:obj.get("textures"))
				list.add(string.asString());
			imageNames = list.toArray(new String[list.size()]);
		}else imageNames = null;
		
		if(obj.has("times")) {
			List<Integer> list = new ArrayList<Integer>();
			for(JsonValue num:obj.get("times"))
				list.add(num.asInt());
			frameDelay = list.toArray(new Integer[list.size()]);
		}else frameDelay = null;
		
		currentFrame=0;
		currentTick=0;
	}
	
	int full=0;
	public void addImage(String name,int delay){
		if(full>imageNames.length)return;
		imageNames[full]=name;
		frameDelay[full]=delay;
		full++;
	}
	public String getImage(int frame){
		return imageNames[frame];
	}
	public int getDelay(int frame){
		return frameDelay[frame];
	}
	public String[] getImageNames() {
		return imageNames;
	}
	public Integer[] getFrameDelays() {
		return frameDelay;
	}
	public String getLastFrame() {
		return imageNames[currentFrame];
	}
	public int getCurrentTick() {
		return currentTick;
	}
	public int getCurrentFrame() {
		return currentFrame;
	}
	public String getFrame(){
		String frame="";
		if(currentTick>=getLength())
			return null;
		int curtick = currentTick-getLength(currentFrame);
		if(curtick>frameDelay[currentFrame])
			currentFrame++;
		frame = imageNames[currentFrame];
		currentTick=currentTick+1;
		return frame;
	}
	public int getLength() {
		int length=0;
		for(int d:frameDelay)
			length=length+d;
		return length;
	}
	public int getLength(int a){
		int length=0;
		for(int i=0;i<a;i++){
			int d = frameDelay[i];
			length=length+d;
		}
		return length;
	}
	
	public RawAnimation getWorkCopy() {
		RawAnimation anim = new RawAnimation(imageNames.length);
		anim.imageNames = new String[imageNames.length];
		for(int index=0;index<imageNames.length;index++)
			anim.imageNames[index] = this.imageNames[index]+"";
		anim.frameDelay = new Integer[frameDelay.length];
		for(int index=0;index<frameDelay.length;index++)
			anim.frameDelay[index] = this.frameDelay[index]+0;
		anim.full=this.full;
		anim.currentFrame=0;
		anim.currentTick=0;
		return anim;
	}
}

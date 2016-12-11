package me.game.render;

import java.util.HashMap;

public class RawAnimationHandler {
	HashMap<String,RawAnimation> animations;
	public RawAnimationHandler() {
		animations = new HashMap<String,RawAnimation>();
	}
	public HashMap<String,RawAnimation> getAnimations() {
		return animations;
	}
	public void addAnimation(String s,RawAnimation a){
		animations.put(s, a);
	}
	public RawAnimation getAnimation(String s){
		if(!animations.containsKey(s))
			return null;
		return animations.get(s);
	}
}

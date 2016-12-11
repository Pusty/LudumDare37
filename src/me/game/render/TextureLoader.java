package me.game.render;


import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.TextureRegion;




public class TextureLoader {
	HashMap<String, TextureRegion> list = new HashMap<String, TextureRegion>();

	
	public TextureLoader() {
		list.clear();
	}

	
	
	
	
	
	public void addImage(String name, TextureRegion img) {
		list.put(name, img);
	}

	public TextureRegion getImage(String name) {
		if (list.containsKey(name))
			return list.get(name);
		else
			return null;
	}

	

	public TextureRegion removeImage(String name) {
		if (list.containsKey(name))
			return list.remove(name);
		else
			return null;
	}

	public HashMap<String, TextureRegion> getList() {
		return list;
	}
	

	public void setList(HashMap<String, TextureRegion> list) {
		this.list = list;
	}

	
}

package me.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import me.game.main.Engine;
import me.game.render.RawAnimation;
import me.game.util.Location;


public class Entity{
	
	Location location;
	RawAnimation animation=null;
	float sizex;
	float sizez;
	boolean init=false;

	public Entity(int x,int z){
		location=new Location(x,z);
	}
	
	public void playAnimation() {
		
	}


	public Location getLocation(){
		return location;
	}
	public void setLocation(Location l) {
		location.set(l);
	}
	public int getX(){
		return location.getX();
	}
	public int getZ(){
		return location.getZ();
	}

	

	public void render(Engine e,SpriteBatch g) {
		try {
			TextureRegion image = e.getImageHandler().getImage(getImage());
			g.draw(image,(getLocation().getX()-(image.getRegionWidth()/2)),((getLocation().getZ())),image.getRegionWidth(),image.getRegionHeight());
		} catch(Exception ex) { System.err.println(getImage()); }
	}
	
	String img=null;
	
	
	public String getTextureName() {
		return "empty";
	}
	
	public String getImage() {
		if(img!=null)
			return img;
		return getTextureName();
	}
	public void setAnimation(RawAnimation a){
		if(a!=null)
			animation=a.getWorkCopy();
		else
			animation=null;
	}
	public boolean isAnimationNull(){
		return animation==null;
	}
	public void initTick(Engine engine,int ind) {
		if(!init) {
			//INIT HERE
			init = true;
		}
	}
	public void renderTick(Engine engine,int ind){
		if(animation!=null) {
			String img = animation.getFrame();
			if(img!=null)
			setImage(img);
			else
			{setAnimation(null);setDefault();}
		}else if(img!=null)
			setDefault();
	}
	
	public void setDefault(){
		img=null;
	}
	
	public void setImage(String i) {
		img = i;
	}
	
	
}

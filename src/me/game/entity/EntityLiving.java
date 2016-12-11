package me.game.entity;

import me.game.main.Engine;
import me.game.util.Location;

public class EntityLiving extends Entity {

	int direction = 0;
	int lastDirection = 1;
	Location lastLocation = new Location(0,0);
	public EntityLiving(int x, int z) {
		super(x, z);
		lastLocation = new Location(x,z);
	}
	public int getLastDirection() {
		return lastDirection;
	}
	public int getDirection() {
		return direction;
	}
	public Location getLastLocation() {
		return lastLocation;
	}
	@Override
	public void setLocation(Location l) {
		lastLocation = location.clone();
		super.setLocation(l);
	}
	
	public boolean lastTimeMoved() {
		return lastLocation.sameAs(location);
	}
	
	
	public void setDirection(int d) {
		direction = d;
		if(direction!=0)
			lastDirection=direction;
	}
	public void setLastDirection(int d) {
			lastDirection=d;
	}
	public String getMovingTexture() {
		return null;
	}
	
	public boolean hasDirections() { return false; }
	//A ENTITY HAS 4 MOVING FRAMES PER DIRECTION!
	
	int walkAnimationRunned = 0;
	public String getImage() {
		if(img!=null)
			return img;
		if(getDirection() == 0 || getMovingTexture() == null) {
			if(!hasDirections())
				return getTextureName();
			else
				return getTextureName()+"_"+(this.getLastDirection()-1);
		}else if(getDirection() != 0) {
			float percent = ((float)getSpeed()-getTraveled())/getSpeed();
			int frame = Math.round(percent * 1) + walkAnimationRunned ; // frame = process * framecount
			if(!hasDirections())
				return getMovingTexture()+"_"+frame;
			else
				return getMovingTexture()+"_"+((this.getDirection()-1)*4 + frame);
		}
		return getTextureName();
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
	
	
	public Location getAddLocation(boolean tick) {
		if(tick)
			walkAnimationRunned = walkAnimationRunned==0?2:0;
		if(animation!=null) return new Location(0,0);
		return getAddLocation(direction);
	}
	
	
	public Location getAddLocation(int d) {
		if(d==1)
			return new Location(0,-1);
		else if(d==2)
			return new Location(0,1);
		else if(d==3)
			return new Location(1,0);
		else if(d==4)
			return new Location(-1,0);
		return new Location(0,0);
	}
	
	
	boolean setDirection = false;
	int setDirectionInt = 0;
	boolean setDirectionNull = false;
	public void queueDirection(int d) {
		if(direction!=0)
			lastDirection = direction;
		setDirection = true;
		if(d == 0) {
			setDirectionNull = true;
		}else {
			setDirectionNull = false;
			setDirectionInt = d;
		}
	}
	int traveled = 0;
	public void startWalking(boolean wall) {
		traveled = getSpeed();
		moving = wall;
	}
	boolean moving=true;
	public boolean isMoving() {
		return moving;
	}
	public void setTraveled(int a) {
		traveled=a;
	}
	//Get Speed 30 = 1 sec
	public int getSpeed() {
		return 10;
	}
	public int getTraveled() {
		return traveled;
	}
	float speed = 1.6f;
	float tick=0;
	public void tickTraveled(Engine e,float delta) {
		if(animation!=null) return;
		tick=tick+delta*30*e.getTickSpeed() * speed;
		if(tick>=1)
			tick=0;
		else
			return;
		if(traveled > 0)
			traveled--;
		
		if(traveled <= 0 && (setDirection || setDirectionNull) ) {
			if(setDirectionNull && setDirectionInt==0) 
				setDirection(0);
			else
				setDirection(setDirectionInt);
			setDirectionInt = 0;
			setDirection = false;
			setDirectionNull = false;
		}
	}
}

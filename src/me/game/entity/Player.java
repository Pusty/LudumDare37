package me.game.entity;



public class Player extends EntityLiving {

	public Player(int x, int y) {
		super(x, y);
	}
	
	
	public String getTextureName() {
		return "player_idle";
	}
	public String getMovingTexture() {
		return "player_moving";
	}
	
	public boolean hasDirections() { return true; }

	//Get Speed 30 = 1 sec
	public int getSpeed() {
		return 10;
	}
	
}



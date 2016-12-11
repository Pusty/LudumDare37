package me.game.dif;

import com.badlogic.gdx.graphics.Color;

import me.game.main.Engine;

public class LoadingTick extends Tick{

	public static int length = 5;

	public LoadingTick(Engine engine) {
		super(engine);
	}
	@Override
	public void tick(Engine e,float elta) {
		if(e.getIdleTime()>0)
			e.setIdleTime(e.getIdleTime()-1);
	}
	@Override
	public void show() {
	}
	@Override
	public void mouse(Engine engine, int screenX, int screenY, int pointer,
			int button) {
	}
	float percent = 0.1f;
	boolean blend = true;
	float tick = 0;
	@Override
	public void render(Engine e, float delta) {
		e.getBatch().setColor(new Color(1f,1f,1f,percent));
		tick=tick+delta*30*e.getTickSpeed();
		if(tick>1) {
			tick=0;
			if(blend)
				percent=percent*1.2f;
			else
				percent=percent*0.9f;
			
			if(percent < 0.1f) {
				engine.setGameStatus(1);
				engine.getEventHandler().cycle();
			}
		if(percent>0.5f)
			blend=false;
		}
		e.getBatch().draw(e.getImageHandler().getImage("empty"), 0, 0,e.getCamera().viewportWidth,e.getCamera().viewportHeight);
	}


	

}

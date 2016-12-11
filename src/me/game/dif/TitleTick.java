package me.game.dif;

import com.badlogic.gdx.graphics.Color;

import me.game.main.Engine;
import me.game.util.Location;
import me.game.util.RenderUtil;

public class TitleTick extends Tick{

	
	public TitleTick(Engine engine) {
		super(engine);
	}



	@Override
	public void show() {
	}

	@Override
	public void tick(Engine engine, float delta) {
		if(ticks>0)
			ticks=ticks-(1*delta*30*engine.getTickSpeed());
		
		if(Tick.enter){
			Tick.enter=false;
			engine.getSound().playClip("col");
			if(engine.getOption()==0)
				engine.setGameStatus(1);
			else if(engine.getOption()==1)
				engine.setGameStatus(2);
			else if(engine.getOption()==2)
				engine.setGameStatus(3);
			else if(engine.getOption()==3){
				engine.setTimeRunning(false);
				engine.setRunning(false);
				
				if(engine.getCanSave()) {
					if(engine.getDebugMode())
						System.out.println(engine.getVariable());
					engine.saveVariables();
					//Save
				}
				System.out.println("[Closed]");
				
				System.exit(0);
			}
		}
	}

	int xOffset = -12;
	int yOffset = 12;
	@Override
	public void mouse(Engine engine, int screenX, int screenY, int pointer,
			int button) {
		
    	Location mouseLocation = new Location(screenX,screenY);
		if(Tick.overCentured(engine,mouseLocation,new Location(0 + xOffset ,24 + yOffset),"Title Screen")){}
		else if(Tick.overCentured(engine,mouseLocation,new Location(0 + xOffset,-8 + yOffset),"Play")){Tick.selectOption(engine,0);}
	}

	@Override
	public void render(Engine e, float delta) {
			e.getBatch().setColor(new Color(0f,0f,0f,1f));
			e.getBatch().draw(e.getImageHandler().getImage("empty"), 0, 0,e.getCamera().viewportWidth,e.getCamera().viewportHeight);

			e.getBatch().setColor(new Color(1f,1f,1f,1f));
			
		//	RenderUtil.renderCentured(e,e.getBatch(),new Location(xOffset,24 + yOffset),"Title Screen");
			RenderUtil.renderCentured(e,e.getBatch(),new Location(xOffset,-8 + yOffset),"Play");
			
			RenderUtil.renderCentured(e,e.getBatch(),new Location(-52 + xOffset,-8+yOffset  -2),"[");
			RenderUtil.renderCentured(e,e.getBatch(),new Location(64 + xOffset,-8+ yOffset -2),"]");
			
			e.getBatch().setColor(new Color(1f,1f,1f,1f));

	}

}

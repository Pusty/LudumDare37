package me.game.dif;

import com.badlogic.gdx.graphics.Color;

import me.game.main.Engine;
import me.game.util.Location;
import me.game.util.RenderUtil;

public class EndTick extends Tick{

	
	public EndTick(Engine engine) {
		super(engine);
	}



	@Override
	public void show() {
	}

	@Override
	public void tick(Engine engine, float delta) {
	}

	int xOffset = 0;
	int yOffset = 12;
	@Override
	public void mouse(Engine engine, int screenX, int screenY, int pointer,
			int button) {
	}

	@Override
	public void render(Engine e, float delta) {
			e.getBatch().setColor(new Color(0f,0f,0f,1f));
			e.getBatch().draw(e.getImageHandler().getImage("empty"), 0, 0,e.getCamera().viewportWidth,e.getCamera().viewportHeight);

			e.getBatch().setColor(new Color(1f,1f,1f,1f));
			
			RenderUtil.renderCentured(e,e.getBatch(),new Location(xOffset,24 + yOffset),"Ludum Dare 37");
			RenderUtil.renderCentured(e,e.getBatch(),new Location(xOffset,8 + yOffset),"Thank you for playing");
			RenderUtil.renderCentured(e,e.getBatch(),new Location(xOffset,-4 + yOffset),"my game!");
			RenderUtil.renderCentured(e,e.getBatch(),new Location(xOffset,-20 + yOffset),"- Pusty");
			
		
			e.getBatch().setColor(new Color(1f,1f,1f,1f));

	}

}

package me.game.event;

import com.badlogic.gdx.Gdx;

import me.game.dif.LoadingTick;
import me.game.main.Engine;

public class ScreenEvent implements Event{
	int index;
	public ScreenEvent(int index) {
		this.index=index;
	}
	
	
	@Override
	public String getName() {
		return "screen";
	}
	

	/**
	 * Index:
	 * 
	 *  0 = Loading Screen
	 * 
	 */

	@Override
	public void event(Engine game) {
		if(index==0) {
			LoadingTick tick = new LoadingTick(game);
			game.setScreen(tick);
		    Gdx.input.setInputProcessor(tick);
		}
	}

	@Override
	public int getIndex() {
		return index;
	}
}


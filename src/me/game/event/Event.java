package me.game.event;

import me.game.main.Engine;


public interface  Event {
	public int getIndex();
	public abstract String getName();
	public abstract void event(Engine game);
}

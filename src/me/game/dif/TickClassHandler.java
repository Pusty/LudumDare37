package me.game.dif;

import me.game.main.Engine;

public class TickClassHandler {
	
	public static TickClassHandler handler = null;
	
	public static void initTickHandler(Engine engine) {
		handler = new TickClassHandler(engine);
	}
	
	Tick ticks[];
	public TickClassHandler(Engine engine){
		ticks = new Tick[6];
		ticks[0]=new TitleTick(engine);
		ticks[1]=new GameTick(engine);
		ticks[2]=new EndTick(engine);
	}
	
	public Tick getTick(Engine e,int i){
		if(e.getIdleTime()>0 && i == 1)
			return ticks[5];
		return ticks[i];
	}
	public void setTick(int i,Tick t){
		ticks[i] = t;
	}
}

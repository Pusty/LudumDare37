package me.game.event;

import java.util.ArrayList;
import java.util.List;

import me.game.main.Engine;

public class EventHandler {
	ArrayList<Event> currentEvent;
	Engine engine;
	public EventHandler(Engine e) {
		currentEvent = new ArrayList<Event>(32); //Limit Events Queue to 32
		engine = e;
	}
	/**Event Cycle*/
	public void cycle() {
		if(getQueueSize()<=0)return;
		callEvent();
		nextEvent();
	}
	/**Call current event*/
	public void callEvent() {
		currentEvent.get(0).event(engine);
	}
	/**Adds a event to the queue*/
	public void queueEvent(Event event) {
		currentEvent.add(event);
	}
	/**Returns next event*/
	public Event getCurrentEvent() {
		return currentEvent.get(0);
	}
	/**Returns next event's class*/
	public Class<?> getCurrentEventType() {
		return currentEvent.get(0).getClass();
	}
	/**Returns next event's name*/
	public String getCurrentEventName() {
		return currentEvent.get(0).getName();	
	}
	/**Removes last event*/
	public void nextEvent() {
		currentEvent.remove(0);
	}
	/**Gets current queue's size */
	public int getQueueSize() {
		return currentEvent.size();
	}
	/**Gets Event List*/
	public List<Event> getEvents() {
		return currentEvent;
	}
	
}

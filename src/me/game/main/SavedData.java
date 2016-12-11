package me.game.main;

import java.util.HashMap;


public class SavedData {
	HashMap<String,Object> data;
	boolean loaded=false;
	public SavedData(){
		data = new HashMap<String,Object>();
	}
	public void putData(String s,Object o){
		data.put(s, o);
	}
	public void removeData(String s){
		data.remove(s);
	}
	public void clearData(){
		data.clear();
	}
	public Object getData(String s){
		if(data.containsKey(s))return data.get(s);
		return null;
	}
	public void createFromNew(){
		//GAMEINFO
		putData("timeplayed",0);
//		putData("posX",-1f);
//		putData("posZ",-1f);
//		putData("world",1);
//		putData("worldOld",0);
//		putData("skill",0);
//		putData("inv","0&Fireball%1&Smash%2&Bloodball%3&Potion%3&Potion%3&Potion");
		loaded=true;
	}
	
	public void printOutAll(){
		for(int ind = 0;ind<data.size();ind++){
			String key = (String) data.keySet().toArray()[ind];
			String value = data.get(key).toString();
			System.out.println(key+"/"+value);
		}
	}
	public void saveToFile(String file){

	}
	
	public void loadFromFile(String file){
	
		}
	
}

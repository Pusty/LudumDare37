package me.game.dif;


import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

import me.game.main.Engine;
import me.game.util.Location;

public abstract class Tick implements Screen, InputProcessor  {
	
	public static boolean down;
	public static boolean up;
	public static boolean left;
	public static boolean right;
	
	public static boolean enter;
	public static boolean shift;
	public static boolean pause;
	public static boolean esc;


	public static boolean cycle;
	public static boolean cycle2;
	float ticks=5f;
	
	Engine engine;
	public Tick(Engine engine){
		this.engine = engine;
	}
	public Engine E() { return engine; }
	
	public abstract void tick(Engine engine,float delta);
	public  abstract void mouse(Engine engine,int screenX, int screenY, int pointer, int button);
	public abstract void render(Engine e,float delta);
	
	public void genericMouse(Engine engine,int type,int screenX, int screenY, int pointer, int button){}
	public boolean keyEvent(Engine e,int type,int keycode){return false;}
	
	
	@Override
	public boolean keyDown(int keycode) {
		if(keyEvent(E(),0,keycode)) return true;
		
		switch(keycode) {
			case Keys.NUM_1:
				if(!engine.getDebugMode())break;
				if(engine.getEndGame()!=0)break;
				engine.setSpeed(1);
				engine.setTimeRunning(true);
				break;
			case Keys.NUM_2:
				if(!engine.getDebugMode())break;
				if(engine.getEndGame()!=0)break;
				engine.setSpeed(2);
				engine.setTimeRunning(true);
				break;
			case Keys.NUM_3:
				if(!engine.getDebugMode())break;
				if(engine.getEndGame()!=0)break;
				engine.setSpeed(3);
				engine.setTimeRunning(true);
				break;
			case Keys.UP:
				up = true;
				break;
			case Keys.DOWN:
				down = true;
				break;
			case Keys.SPACE:
				enter = true;
				break;
		
			case Keys.ENTER:
				enter = true;
				break;
			case Keys.SHIFT_LEFT:
				shift=true;
				break;
			case Keys.ESCAPE:
				esc = true;
				break;
			case Keys.M:
		//		engine.getSound().getClip("bg_1").stop();
		//		engine.getSound().getClip("bg_2").stop();
				break;
			case Keys.W:
				up = true;
				break;
			case Keys.S:
				down = true;
				break;
			case Keys.L:
				if(!engine.getDebugMode())break;
				cycle = true;
				break;
			case Keys.K:
				if(!engine.getDebugMode())break;
				cycle2 = true;
				break;
			case Keys.A:
				left = true;	
				break;
			case Keys.D:
				right = true;	
				break;
			case Keys.LEFT:
				left = true;	
				break;
			case Keys.RIGHT:
				right = true;
				break;
			case Keys.P:
				if(!engine.getDebugMode())break;
				if(engine.getEndGame()!=0)break;
				if(!pause){
				engine.setTimeRunning(!engine.isTimeRunning());
				pause=true;
				}
				break;
	}
		return true;
	}
	@Override
	public boolean keyUp(int keycode) {
		if(keyEvent(E(),1,keycode)) return true;
		switch(keycode) {
		case Keys.NUM_1:
			break;
		case Keys.NUM_2:
			break;
		case Keys.NUM_3:
			break;
		case Keys.UP:
			up = false;
			break;
		case Keys.DOWN:
			down = false;
			break;
		case Keys.SPACE:
			enter = false;
			break;
	
		case Keys.ENTER:
			enter = false;
			break;
		case Keys.SHIFT_LEFT:
			shift= false;
			break;
		case Keys.ESCAPE:
			esc = false;
			break;
		case Keys.M:
			break;
		case Keys.W:
			up = false;
			break;
		case Keys.S:
			down = false;
			break;
		case Keys.L:
			cycle = false;
			break;
		case Keys.K:
			cycle2 = false;
			break;
		case Keys.A:
			left = false;	
			break;
		case Keys.D:
			right = false;	
			break;
		case Keys.LEFT:
			left = false;	
			break;
		case Keys.RIGHT:
			right = false;
			break;
		case Keys.P:
			pause= false;
			break;
}
		return true;
	}
	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Ray ray = engine.getCamera().getPickRay(screenX, screenY);
		Vector3 out = new Vector3();
		ray.getEndPoint(out, engine.getCamera().near);
		mouse(E(),(int)out.x,(int)out.y,pointer,button);
		return true;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Ray ray = engine.getCamera().getPickRay(screenX, screenY);
		Vector3 out = new Vector3();
		ray.getEndPoint(out, engine.getCamera().near);
		genericMouse(E(),3,(int)out.x,(int)out.y,pointer,button);
		return true;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Ray ray = engine.getCamera().getPickRay(screenX, screenY);
		Vector3 out = new Vector3();
		ray.getEndPoint(out, engine.getCamera().near);
		genericMouse(E(),4,(int)out.x,(int)out.y,pointer,0);
		return true;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		Ray ray = engine.getCamera().getPickRay(screenX, screenY);
		Vector3 out = new Vector3();
		ray.getEndPoint(out, engine.getCamera().near);
		genericMouse(E(),5,(int)out.x,(int)out.y,0,0);
		return true;
	}
	
	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	@Override
	public void render(float delta) {
		if(engine.isTimeRunning())
			tick(E(),delta);
			render(E(),delta);
		

	}
	
	@Override
	public void resize(int width, int height) {
	}
	@Override
	public void pause() {
	}
	@Override
	public void resume() {
	}
	@Override
	public void hide() {
	}
	@Override
	public void dispose() {
	}
	
	
	   static int lastIndex=-1;
	    static int lastSide=-1;
	    static int lastGSide=-1;
	     public static void selectOption(Engine engine,int ind){
	     	if(ind==lastIndex && lastIndex == engine.getOption() && lastSide==engine.getGameStatus() && lastGSide==engine.getOptionSide())
	     	{
	     		lastIndex=-1;
	     		lastSide=-1;
	     		lastGSide=-1;
	     		Tick.enter=true;
	     	}else{
	     		lastIndex=ind;
	     		lastSide=engine.getGameStatus();
	     		lastGSide=engine.getOptionSide();
	     		engine.setOption(ind);
	     	}
	     }
	    public static Location getCentured(Engine engine,Location l,String txt){
	    	return new Location((int)engine.getCamera().viewportWidth/2 + l.x - 16*txt.length()/2,(int)engine.getCamera().viewportHeight/2 +l.z);
	    }
	    
	    public static boolean overCentured(Engine engine,Location m,Location off,String txt){
	    	Location centure = getCentured(engine,off,txt);
	    	if(m.z >= centure.z && m.z < centure.z+16){
	    		if(m.x < centure.x+16*txt.length()+32 && m.x > centure.x-32)
	    			return true;
	    	}
	    	return false;
	    }
	    
	    public static boolean overAbsulute(Location m,Location centure,int size){
	    	if(m.z >= centure.z && m.z < centure.z+16){
	    		if(m.x < centure.x+16*size+32 && m.x > centure.x-32)
	    			return true;
	    	}
	    	return false;
	    }
}

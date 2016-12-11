package me.game.main;

import java.util.HashMap;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import me.game.dialog.Dialog;
import me.game.dif.TickClassHandler;
import me.game.entity.Player;
import me.game.event.EventHandler;
import me.game.render.TextureLoader;
import me.game.render.RawAnimationHandler;
import me.game.util.Location;
import me.game.util.LocationFloat;
import me.game.util.SoundLoader;
import me.game.world.World;
import me.game.world.Zone;

public abstract class Engine extends Game {
	
	boolean timeRunning=true;
	int selectedIndex=-1;
	float tickSpeed=1f;
	int endGame = 0;
	TextureLoader pictureloader;
	SoundLoader soundloader;
	SavedData savedData;
	int ticksRunning=0;
	
	HashMap<String,Integer> variables;
	
	RawAnimationHandler animationHandler;
	EventHandler eventHandler;
	Dialog dialog = null;
	
	OrthographicCamera camera = null;
	SpriteBatch batch = null;
	BitmapFont font = null;
	
	public int getTicksRunning(){return ticksRunning;}
	public void resetTicks(){ticksRunning=0;}
	public void addTicks(){ticksRunning=ticksRunning+1;}
	
	boolean running=true;
	int idleTime;

	public int getEndGame(){return endGame;}
	public void setEndGame(int inx){endGame=inx;}
	public int getSelectedIndex(){return selectedIndex;}
	public void setSelectedIndex(int inx){selectedIndex=inx;}
	
	public float getTickSpeed(){return tickSpeed;}
	public void setSpeed(float s){tickSpeed=s;}
	public SoundLoader getSound(){return soundloader;}
	public void setDialog(Dialog d) {
		dialog=d;
	}
	public Dialog getDialog() {
		return dialog;
	}
	public Engine(){
		pictureloader=new TextureLoader();
		soundloader=new SoundLoader();
		savedData=new SavedData();
		animationHandler=new RawAnimationHandler();
		eventHandler=new EventHandler(this);
		variables = new HashMap<String,Integer>();
		
	}
	public void startInit() {
		preInit();
		Init();
		postInit();
		
		initStartScreen();
	}
	public EventHandler getEventHandler() { return eventHandler; }
	public HashMap<String,Integer> getVariable() { return variables; }
	public RawAnimationHandler getAnimationHandler() {
		return animationHandler;
	}
	public SavedData getSave(){return savedData; }
	public boolean isTimeRunning(){return timeRunning;}
	public void setTimeRunning(boolean b){timeRunning=b;}

	public TextureLoader getImageHandler(){return pictureloader;}


	public LocationFloat getPlayerLocation() {
		Player player = getPlayer();
			float extraX = 0f;
			float extraZ = 0f;
			if(player.getDirection()!=0) {
				Location add = player.getAddLocation(false);
				 extraX = (float)add.getX()/player.getSpeed() * (player.getTraveled());
				 extraZ = (float)add.getZ()/player.getSpeed() * (player.getTraveled());
				 if(!player.isMoving()) {
					 extraX = (float)add.getX()/player.getSpeed() * (player.getSpeed() - player.getTraveled()) * -1f;
					 extraZ = (float)add.getZ()/player.getSpeed() * (player.getSpeed() - player.getTraveled()) * -1f;				 
					 if(Math.abs(extraX) >= 0.5f)
						extraX = (1f - Math.abs(extraX)) * (extraX >0?1:-1);
					 if(Math.abs(extraZ) >= 0.5f)
						 extraZ = (1f - Math.abs(extraZ)) * (extraZ >0?1:-1);		 
				 }
			}
			if(!player.isMoving()) {
				extraX = 0f;
				 extraZ = 0f;
			}
		return new LocationFloat(player.getX()-extraX,player.getZ()-extraZ);
	}
	public LocationFloat getCL() {	
		return new LocationFloat((getCamera().viewportWidth/2),(getCamera().viewportHeight/2 /*- 0.35f*Options.tileSize*/));
	}
	
	public boolean isRunning(){
		return running;
	}
	public void setRunning(boolean b){
		running=b;
	}
	
	
	public void setIdleTime(int i){
		idleTime = i;
	}
	
	public int getIdleTime() {
		return idleTime;
	}
	
	public abstract void preInit();
	public abstract void Init();
	public abstract void postInit();
	
	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}
	
	public BitmapFont getFont() {
		return font;
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}

	private World world;
	
	public Zone getZone() {
		return world.getZoneAt(getPlayer().getLocation());
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	public World getWorld() {
		return world;
	}


	public String getName(){return "Engine";}
	
	public abstract void initStartScreen();
	public static boolean STOP = false;
	@Override
	public void create () {
			normalInit();

	}
	
	public void normalInit() {
		try {
		loadDefault();
		loadVariables();
		} catch(Exception e){e.printStackTrace();}
		initializeFBO();
		startInit();
	}
	
	
	@Override
	public void resize(int w,int h) {
		initializeFBO();
	}

	@Override
	public void render () {
        
		sleep(30); //SET FPS!
	}
	
//	private long diff, start = com.badlogic.gdx.utils.TimeUtils.millis();

	public void sleep(int fps) {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |  GL20.GL_DEPTH_BUFFER_BIT );
			
//	    if(fps>0){
//	      diff = com.badlogic.gdx.utils.TimeUtils.millis() - start;
//	      long targetDelay = 1000/fps;
//	      if (diff < targetDelay) {
//	    	  initializeFBO(); // remaking the BufferedFrame. May be removed for performance sake! (only there cuz main screen has no background)
	    	  realRender();
//	        }else 
//	        	start = com.badlogic.gdx.utils.TimeUtils.millis();      
			batch.begin();
	  		batch.draw(fbo.getColorBufferTexture(), 0, 0, camera.viewportWidth, camera.viewportHeight, 0, 0, 1, 1);
	  		batch.end();

	        
//	    } 
	}
	  FrameBuffer fbo;
	public void initializeFBO() {
		if(fbo != null) fbo.dispose();
		fbo = new FrameBuffer(Format.RGB888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		
		if(batch != null) batch.dispose();
		batch = new SpriteBatch();

	}
	
	public void realRender() {
			fbo.begin();
        	if (isRunning()) {
        		camera.update();
        		batch.setProjectionMatrix(camera.combined);
        		batch.begin();
        		super.render();
        		batch.end();
        	}
        	fbo.end();
	}
	
	
	@Override
	public void dispose() {
		batch.dispose();
   	    font.dispose();
   	    SoundLoader.close();
    }

		Player player;
		public Player getPlayer(){
			return player;
		}
		
		public void setPlayer(Player p){
			player=p;
		}


		
		private String iconPath = "icon";
		public static String gameName = "ClickGame";
		private boolean debugMode = false;
		public static boolean canSave = true;
		private String[] text = {"Credits","","Framework by Pusty"};
		private String startWorld = "world";
		public void setStartWorld(String s){
			startWorld = s;
		}
		public String getStartWorld() {
			return startWorld;
		}
		public void setIconPath(String s){
			iconPath = s;
		}
		public String getIconPath() {
			return iconPath;
		}
		public void setGameName(String s){
			gameName = s;
		}
		public void setDebugMode(boolean b){
			debugMode = b;
		}
		public void setCanSave(boolean b){
			canSave = b;
		}
		public void setText(String[] t){
			text = t;
		}
		public String getGameName() {
			return gameName;
		}
		public boolean getCanSave() {
			return canSave;
		}
		public String[] getText() {
			return text;
		}
		public boolean getDebugMode() {
			return debugMode;
		}
		int gameStatus=0;
		public int getGameStatus() {
			return gameStatus;
		}
		public void setGameStatus(int i){
			this.setSpeed(1f);
			optionInt=0;
			optionSide=0;
			gameStatus=i;
			TickClassHandler.handler.getTick(this, gameStatus).tick(this, 0f);
			this.setScreen(TickClassHandler.handler.getTick(this, gameStatus));
			Gdx.input.setInputProcessor((TickClassHandler.handler.getTick(this, gameStatus)));
		}
		
		int optionInt=0;
		int optionSide=0;
		public void setOption(int op){
			optionInt=op;
		}
		public int getOption(){
			return optionInt;
		}
		
		public void setOptionSide(int op){
			optionSide=op;
		}
		public int getOptionSide(){
			return optionSide;
		}


		public void saveVariables() {}
		public void loadVariables() {}
		public void loadDefault() {}
}

package me.game.main;


import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonValue;




import me.game.dif.TickClassHandler;
import me.game.entity.Player;
import me.game.event.BlockEvent;
import me.game.render.RawAnimation;
import me.game.util.FileChecker;
import me.game.util.Location;
import me.game.util.RenderUtil;
import me.game.util.json.JsonHandler;
import me.game.world.World;
import me.game.world.Zone;
import me.game.world.ZoneLoader;

public class GameClass extends Engine{

	

	public GameClass(){
		super();
		
		
	}
	
	
	public void loadDefault() {
		try {
			JsonValue obj  = (new JsonHandler()).getObjectFromFile(Gdx.files.getFileHandle("scripts/game.json", FileType.Internal).read());
			if(obj.has("icon"))
				setIconPath(obj.getString("icon"));
			if(obj.has("name"))
				setGameName(obj.getString("name"));
			if(obj.has("world"))
				setStartWorld(obj.getString("world"));
			if(obj.has("debug"))
				setDebugMode(obj.getBoolean("debug"));
			if(obj.has("save"))
				setCanSave(obj.getBoolean("save"));
			if(obj.has("text")) {
				List<String> list = new ArrayList<String>();
				for(JsonValue jst:obj.get("text"))
					list.add(jst.asString());
				setText(list.toArray(new String[list.size()]));
			}
			if(obj.has("variables")) {
				for(JsonValue jst:obj.get("variables"))
					getVariable().put(jst.name(),jst.asInt());
			}
		} catch(Exception e){e.printStackTrace();}
	
	}
	
	
	@Override
	public void preInit() {
		FileChecker.createStreams();
		TickClassHandler.initTickHandler(this);
		this.setBatch(new SpriteBatch());
		this.setFont(new BitmapFont());
		
		
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 213, 120);
		try{
			
			for(Entry<String,FileHandle> entry:FileChecker.checkFolderToHashMap("", "png").entrySet()) {
				String name = FileChecker.splitNonRegex(entry.getKey(),".")[0];
				FileHandle file = entry.getValue();
				if(name.equalsIgnoreCase("letters")) {
					String[] letter = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
							"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
							"V", "W", "X", "Y", "Z", ":", "!", "?", ".", "[", "]", "0",
							"1", "2", "3", "4", "5", "6", "7", "8", "9", "(", ")", "+",
							"-", "/", " ", "_","," };
					
					

					Texture tex = new Texture(file);
					TextureRegion[][]  tmp = TextureRegion.split(tex, tex.getWidth()/8, tex.getHeight()/8);
			        int index = 0;
			        for (int i = 0; i < 8; i++) {
			            for (int j = 0; j < 8; j++) {
			            	getImageHandler().addImage("char_" + letter[index], tmp[i][j]);
			                index++;
			                if(index >= letter.length)
			                	break;
			            }
		                if(index >= letter.length)
		                	break;
			        }
			        
			     
				}else if(name.equalsIgnoreCase("chars")) {
					
					char[] smallletters = { ' ','a','b','c','d','e','f','g','h','i',
											'j','k','l','m','n','o','p','q','r','s',
											't','u','v','w','x','y','z','A','B','C',
											'D','E','F','G','H','I','J','K','L','M',
											'N','O','P','Q','R','S','T','U','V','W',
											'X','Y','Z','0','1','2','3','4','5','6',
											'7', '8','9','!','"','%','&','/','(',')',
											'=', '?','[',']','{','}','\\','|','<','>',
											'*', '+','~',"'".toCharArray()[0],'#','-','_','.',':',',',
											';'};
					
					
					
					Texture tex = new Texture(file);
					TextureRegion[][]  tmp = TextureRegion.split(tex, tex.getWidth()/10, tex.getHeight()/10);
			        int index = 0;
			        for (int i = 0; i < tmp.length; i++) {
			            for (int j = 0; j < tmp[i].length; j++) {
			            	getImageHandler().addImage("small_" + smallletters[index], tmp[i][j]);
			                index++;
			                if(index >= smallletters.length)
			                	break;
			            }
		                if(index >= smallletters.length)
		                	break;
			        }
				}else {
					Texture texture = new Texture(file);
					if(texture.getWidth() <= Options.tileSize && texture.getHeight() <= Options.tileSize 
							|| name.contains("bg") || name.contains("dialog"))
						getImageHandler().addImage(name, new TextureRegion(texture));
					else {
						int splitterX = texture.getWidth()/Options.tileSize;
						int splitterY = texture.getHeight()/Options.tileSize;
						TextureRegion[][]  tmp = TextureRegion.split(texture, texture.getWidth()/splitterX, texture.getHeight()/splitterY);
				        int index = 0;
				        for (int i = 0; i < tmp.length; i++) {
				            for (int j = 0; j < tmp[i].length; j++) {
				            	getImageHandler().addImage(name+"_"+index, tmp[i][j]);
				                index++;
				            }
				        }
					}
						
				}
			}
			
				{
				
				//CHECK FOR ALPHA TO MAKE IT A OVERLAY
				ArrayList<Integer> idList = new ArrayList<Integer>();
				int id=1;
				idList.add(-1);
				while(getImageHandler().getImage("tile_"+id)!=null || id < 32) {
					if(getImageHandler().getImage("tile_"+id)==null){id++;continue;}
					TextureRegion reg = getImageHandler().getImage("tile_"+id);
					TextureData data = reg.getTexture().getTextureData();
					data.prepare();
					Pixmap pixMap = data.consumePixmap();
					boolean trans=false;
					for(int x=0;x<reg.getRegionWidth();x++)
						for(int y=0;y<reg.getRegionHeight();y++) {
							int pixel = pixMap.getPixel(x+reg.getRegionX(), y+reg.getRegionY());
							int alpha = ((pixel>>0)&0xFF); // ALPHA
							if(alpha != 255)
								trans=true;
						}
					data.disposePixmap();
					idList.add(trans?1:-1);
					id++;
				}
				RenderUtil.overlayIDS = idList.toArray(new Integer[idList.size()]);
				}
		
			


			//Animation loader
			
			JsonHandler handler = new JsonHandler();
		
				try {
					JsonValue jsobj  = handler.getArrayFromFile(Gdx.files.getFileHandle("scripts/animations.json", FileType.Internal).read());;
					for(JsonValue jobj:jsobj){
						RawAnimation animation = new RawAnimation(jobj);
						this.getAnimationHandler().addAnimation(jobj.getString("name"), animation);
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			
		

		}catch(Exception e){e.printStackTrace();}
		
		for(Entry<String,FileHandle> entry:FileChecker.checkFolderToHashMap("", "wav").entrySet()) {
			getSound().addSound(FileChecker.splitNonRegex(entry.getKey(),".")[0],entry.getValue(),false);
		}
		
	}
	

	@Override
	public void Init() {
		this.setPlayer(new Player(4,5));
		
		World world = new World();
		
		{
			Zone room = ZoneLoader.loadZone(this, new Location(0,0), "tilemap_00.txm",32);
			world.addZone(room);
		}
		
		
		world.createArray();
		this.setWorld(world);
		
		BlockEvent.changeWorld(this.getZone(), this);
	}

	@Override
	public void postInit() {

	}


	@Override
	public void initStartScreen() {
		this.setScreen(TickClassHandler.handler.getTick(this, 0));
	    Gdx.input.setInputProcessor(TickClassHandler.handler.getTick(this, 0));
	}
    
}

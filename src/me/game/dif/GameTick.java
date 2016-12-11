package me.game.dif;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

import me.game.entity.Entity;
import me.game.entity.Player;
import me.game.event.BlockEvent;
import me.game.main.Engine;
import me.game.util.Location;
import me.game.util.RenderUtil;
import me.game.world.Chunk;
import me.game.world.Zone;

public class GameTick extends Tick{



	public GameTick(Engine engine) {
		super(engine);

	}
	
	@Override
	public boolean keyEvent(final Engine e,int type,int keycode) {
		Player player = e.getPlayer();
		if(e.getDialog()!=null) {
			if(keycode==Keys.SPACE || keycode==Keys.ENTER) {
				if(type==0)
					e.getDialog().nextLine(e);
			}else if(keycode==Keys.W || keycode==Keys.UP) {
				if(type==0)
					e.getDialog().up(e);
			}else if(keycode==Keys.S || keycode==Keys.DOWN) {
				if(type==0)
					e.getDialog().down(e);
			}
		} else {
		switch(keycode) {
			case Keys.DOWN:
			case Keys.S:
				if(type==0)
					player.queueDirection(1);
				return true;
			case Keys.UP:
			case Keys.W:
				if(type==0)
					player.queueDirection(2);
				return true;	
			case Keys.RIGHT:
			case Keys.D:
				if(type==0)
					player.queueDirection(3);
				return true;
			case Keys.LEFT:
			case Keys.A:
				if(type==0)
					player.queueDirection(4);
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public void tick(Engine e,float delta) {
		if(ticks>0)
			ticks=ticks-(1*delta*30*e.getTickSpeed());
		
	
		Player player = e.getPlayer();
		player.tickTraveled(e,delta);
		
		if((player.getSpeed() - player.getTraveled())==5 || (player.getSpeed() - player.getTraveled())==10) {
			engine.getEventHandler().cycle();
		}
		
		if(player.getTraveled()<=0 && player.getDirection()!=0) {
			if((!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.W) && !Gdx.input.isKeyPressed(Keys.S) && !Gdx.input.isKeyPressed(Keys.D) 
					&& !Gdx.input.isKeyPressed(Keys.UP) && !Gdx.input.isKeyPressed(Keys.DOWN) && !Gdx.input.isKeyPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.RIGHT)) || e.getDialog()!=null)
				player.setDirection(0);
		}
		if(player.getTraveled()<=0 && player.getDirection()!=0) {		

			Location newLoc = player.getLocation().add(player.getAddLocation(true));
			Zone newZone = e.getWorld().getZoneAt(newLoc);
			
			if(newZone != null) {
				int newBlock = newZone.getBlockID(newLoc);
				Entity entityAt = newZone.entityAt(newLoc);
				// COLLISION
				if(BlockEvent.handleBlockCollision(engine,player, newLoc.getX(), newLoc.getZ(), newBlock) && entityAt==null ) {
					player.setLocation(newLoc);
					player.startWalking(true);
					engine.getSound().playClip("step");
				}else {
					player.startWalking(false);
					engine.getSound().playClip("col");
				}
				
			} else {
				player.setLocation(newLoc);
				player.startWalking(true);
			}
		}
		
		if(Tick.esc && ticks<=0) {
			e.setGameStatus(0);
			ticks=10;
		}
		
	}
	
	public boolean intersectRect(int x1,int y1,int w1,int h1,int x2,int y2,int w2,int h2) {
		  return !(x2 > x1+w1 || 
		           x2+w2 < x1 || 
		           y2+h2 > y1 ||
		           y2 < y1+h1);
	}

	@Override
	public void genericMouse(Engine engine,int type,int screenX, int screenY, int pointer, int button){
//    	Location mouseLocation = new Location(screenX,screenY);
//    	Location pos = new Location(mouseLocation.x/Options.renderSize,mouseLocation.z/Options.renderSize);
    	if(type==5) {
    	
    	}
	}

	@Override
	public void mouse(Engine e,int screenX, int screenY, int pointer, int button) {
		if(e.getDialog()!=null) {
					e.getDialog().nextLine(e);	
		} 
	}
	
	@Override
	public void render(Engine e, float delta) {
		e.getBatch().setColor(new Color(0f,0f,0f,1f));
		//May get removed later (just for debug purpose)
		e.getBatch().draw(e.getImageHandler().getImage("empty"), 0, 0,e.getCamera().viewportWidth,e.getCamera().viewportHeight);
		e.getBatch().setColor(new Color(1f,1f,1f,1f));
		
		boolean playerDrawn = false;
		
		Zone zone = e.getZone();
		Location loc = new Location(0,0);
		if(zone!=null)
			for(int z=-3;z<=3;z++)
				for(int x=-4;x<=4;x++) {
					loc = new Location(e.getPlayer().getLocation().getX()+x,e.getPlayer().getLocation().getZ()+z);
					if(e.getWorld().getZoneAt(loc)!=null)
						RenderUtil.renderBlock(e, null, loc.x,loc.z ,zone.getDefID());
					else
						RenderUtil.renderBlock(e, null, loc.x,loc.z ,zone.getOutID());
				}
		
		Zone[] zones = e.getWorld().getZonesNearPlayer(e);
		if(zones!=null) {
			for(int zIndex=0;zIndex<zones.length;zIndex++) {
				Zone z = zones[zIndex];
				if(z==null)continue;
				for(int chunkIndex=0;chunkIndex<z.getChunkArray().length;chunkIndex++) {
					Chunk c = z.getChunkArray()[chunkIndex];
					if(!c.withInScreen(e,z))continue;
					for (int bz = 0; bz < c.getSizeZ(); bz++) {
						for (int bx = 0; bx < c.getSizeX(); bx++) {
							if(z.getDefID()!=c.getBlockID(bx, bz))
								RenderUtil.renderBlock(e, z, c.getChunkX() * c.getSizeX()
									+ bx, c.getChunkZ() * c.getSizeZ() + bz,c.getBlockID(bx, bz));
						}
					}
				}
			}
			
			
			for(int zIndex=0;zIndex<zones.length;zIndex++) {
				Zone z = zones[zIndex];
				if(z==null)continue;
				boolean onOverlay = RenderUtil.hasOverlay(z.getBlockID(e.getPlayer().getLocation())) ||
						RenderUtil.hasOverlay(z.getBlockID(e.getPlayer().getLastLocation()));
				for(int chunkIndex=0;chunkIndex<z.getChunkArray().length;chunkIndex++) {
					Chunk c = z.getChunkArray()[chunkIndex];
					if(!c.withInScreen(e,z))continue;
						boolean[] bools = new boolean[z.getEntityArray().length];
						for (int bz = c.getSizeZ()-1; bz >= 0; bz--) {
							
							if(onOverlay)
								if(c.getChunkZ() * c.getSizeZ() + bz +z.getOffset().getZ() == e.getPlayer().getZ()
										&& (c.getChunkX()*c.getSizeX()+z.getOffset().getX()<=e.getPlayer().getX() && c.getChunkX()*c.getSizeX()+z.getOffset().getX()+c.getSizeX()>e.getPlayer().getX())) {
									if(!playerDrawn) {
										RenderUtil.renderEntity(e,null,  e.getPlayer());
										playerDrawn = true;
									}
								}	

							drawEntitys(e,z,c.getChunkZ() * c.getSizeZ() + bz,c.getChunkX() * c.getSizeX() + z.getOffset().getX(),bools,true);	
							
							for (int bx = 0; bx < c.getSizeX(); bx++) {
								if(RenderUtil.hasOverlay(c.getBlockID(bx, bz)))
									RenderUtil.renderBlockOverlay(e, z, c.getChunkX() * c.getSizeX()
											+ bx, c.getChunkZ() * c.getSizeZ() + bz,c.getBlockID(bx, bz));
							}
							
							if(!onOverlay)
								if(c.getChunkZ() * c.getSizeZ() + bz + z.getOffset().getZ() == e.getPlayer().getZ()
										&& (c.getChunkX()*c.getSizeX()+z.getOffset().getX()<=e.getPlayer().getX() && c.getChunkX()*c.getSizeX()+z.getOffset().getX()+c.getSizeX()>e.getPlayer().getX())) {
									if(!playerDrawn) {
										RenderUtil.renderEntity(e, null, e.getPlayer());
										playerDrawn = true;
									}
								}	
							
					}
				}
			}
		}
			if(!playerDrawn) {
				RenderUtil.renderEntity(e, null, e.getPlayer());
			}
			
			
	//HUD HERE 
			if(e.getDialog()!=null){
				RenderUtil.renderDialog(e);
			}
	}

	@Override
	public void show() {
	}
		
	private void drawEntitys(Engine e,Zone zone,int z,int x,boolean[] bools,boolean overlay) {
		if(zone==null)return;
		for(int entityIndex=0;entityIndex<zone.getEntityArray().length;entityIndex++) {
			Entity entity = null;
			if(entityIndex < zone.getEntityArray().length)
				entity = zone.getEntityArray()[entityIndex];
			if(entity==null)continue;
					if(z == entity.getZ() && (x<=entity.getX() && x+10>entity.getX())) {
						if(!bools[entityIndex]) {
							RenderUtil.renderEntity(e,zone, entity);
							bools[entityIndex] = true;
						}
					}
		}
	}

	

}

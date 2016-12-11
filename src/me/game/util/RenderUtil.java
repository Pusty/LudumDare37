package me.game.util;

import me.game.entity.Entity;
import me.game.entity.EntityLiving;
import me.game.entity.Player;
import me.game.main.Engine;
import me.game.main.Options;
import me.game.world.Zone;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RenderUtil {
	public static void renderEntity(Engine e,Zone zone,Entity entity) {
		renderEntity(e,zone,entity,entity.getX(),entity.getZ());
	}
	public static void renderEntity(Engine e,Zone zone,Entity entity,int x,int z) {
		TextureRegion reg = e.getImageHandler().getImage(entity.getImage());
		if(reg==null)
			reg = e.getImageHandler().getImage("empty");
		int xSize = reg.getRegionWidth();
		int zSize = reg.getRegionHeight();
		if(zone!=null) {
			x=x+zone.getOffset().getX();
			z=z+zone.getOffset().getZ();
		}
		
		int movementX = 0;
		int movementZ = 0;
		float offsetX =  (x  - e.getPlayerLocation().getX())*Options.tileSize;
		float offsetZ = (z  - e.getPlayerLocation().getZ())*Options.tileSize;
		
		if(entity instanceof Player) {
			if(((Player) entity).isMoving()) {
			 offsetX=0;
			 offsetZ=0;
			}
		}
		
		if(entity instanceof EntityLiving && !(entity instanceof Player)) {
			EntityLiving living = (EntityLiving)entity;
			if(living.getDirection()!=0) {
				Location add = living.getAddLocation(false);
				float extraX = (float)add.getX()/living.getSpeed() * (living.getTraveled());
				float extraZ = (float)add.getZ()/living.getSpeed() * (living.getTraveled());
				movementX = (int)(extraX*xSize);
				movementZ = (int)(extraZ*zSize);
			}
		}
		
//		offsetZ = offsetZ + (0.35f*Options.tileSize);
		
		e.getBatch().draw(reg, e.getCL().getX() - xSize/2 -movementX + offsetX, e.getCL().getZ() - zSize/2 -movementZ + offsetZ, xSize, zSize);
	}
	
	public static void renderBlock(Engine e,Zone zone,int x,int z,int id) {
		int size = Options.tileSize;
		if(id<=0 || hasOverlay(id))return;
		if(zone!=null) {
		x=x+zone.getOffset().getX();
		z=z+zone.getOffset().getZ();
		}
		e.getBatch().draw(e.getImageHandler().getImage("tile_"+id),e.getCL().getX() - size/2 +  (x - e.getPlayerLocation().getX())*size,
				e.getCL().getZ() - size/2 + (z - e.getPlayerLocation().getZ())*size);
	}
	public static Integer[] overlayIDS = null;
	
	public static boolean hasOverlay(int id) {
		return overlayIDS[id]==1;
	}
	public static void renderBlockOverlay(Engine e,Zone zone,int x,int z,int id) {
		int size = Options.tileSize;
		if(zone!=null) {
		x=x+zone.getOffset().getX();
		z=z+zone.getOffset().getZ();
		}
		e.getBatch().draw(e.getImageHandler().getImage("tile_"+id),e.getCL().getX() - size/2 +  (x - e.getPlayerLocation().getX())*size,
				e.getCL().getZ() - size/2 + (z - e.getPlayerLocation().getZ())*size);
	}
	
	
	public static void renderDialog(Engine e) {
		String[] text = e.getDialog().getText();
		e.getBatch().setColor(new Color(1f,1f,1f,1f));
		if(e.getDialog().getOwner()!=null && !e.getDialog().getOwner().equalsIgnoreCase("null"))
			e.getBatch().draw(e.getImageHandler().getImage("dialog_char_"+e.getDialog().getOwner()), 5, 20);
		e.getBatch().draw(e.getImageHandler().getImage("dialog"), 0, 0,e.getCamera().viewportWidth,40);
		e.getBatch().setColor(new Color(0f,0f,0f,1f));
		
		for(int index=0;index<Math.min(3,text.length-e.getDialog().getLine());index++) 
			RenderUtil.renderText(e, e.getBatch(), new Location(0,36-((index+1)*11)), text[index+e.getDialog().getLine()]);
		if(text.length-e.getDialog().getLine()>3) 
			RenderUtil.renderText(e, e.getBatch(), new Location(11*16,36-(3*11)), "\\/");
		
		e.getBatch().setColor(new Color(1f,1f,1f,1f));
	}
	
	

	public static void renderCentured(Engine engine,SpriteBatch g,Location offset,String txt){
		renderText(engine,g,new Location((int)engine.getCamera().viewportWidth/2 + offset.x - calculateOffset(txt,txt.length()-1)/2,(int)engine.getCamera().viewportHeight/2 +offset.z),txt);
	}
	
	public static int calculateOffset(String txt,int index) {
		if(txt == null || txt == "" || index >= txt.length())return 0;
		int tempsize = 0;
		for(int a=0;a<txt.length();a++) {
			if(a>index)break;
			tempsize = tempsize+6;	
			if(txt.toCharArray()[a]==' ') 
				tempsize=tempsize-3;
			else if(Character.isUpperCase(txt.toCharArray()[a])) 
				tempsize=tempsize+2;	
			else if(txt.toCharArray()[a]=='l') 
				tempsize=tempsize-4;
			else if(txt.toCharArray()[a]=='t') 
				tempsize=tempsize-2;
			else if(txt.toCharArray()[a]=='r') 
				tempsize=tempsize-2;
			else if(txt.toCharArray()[a]=='m') 
				tempsize=tempsize+2;
			else if(txt.toCharArray()[a]=="'".toCharArray()[0]) 
				tempsize=tempsize-4;
			else if(txt.toCharArray()[a]==':') 
				tempsize=tempsize-4;
			
		}
		return tempsize;
	}
	
	
	public static void renderText(Engine en,SpriteBatch g,Location loc,String txt){
		if(txt == null || txt == "")return;
		int tempsize = 0;
		for(int a=0;a<txt.length();a++) {
			TextureRegion image = en.getImageHandler().getImage("small_"+txt.toCharArray()[a]);
			if(image==null) continue;
			tempsize = tempsize+6;	
			if(txt.toCharArray()[a]==' ') {
				tempsize=tempsize-3;
				continue;
			}
			g.draw(image, (int)loc.x + tempsize, (int)loc.z ,image.getRegionWidth(),image.getRegionHeight());

			if(Character.isUpperCase(txt.toCharArray()[a])) 
				tempsize=tempsize+2;	
			else if(txt.toCharArray()[a]=='l') 
				tempsize=tempsize-4;
			else if(txt.toCharArray()[a]=='t') 
				tempsize=tempsize-2;
			else if(txt.toCharArray()[a]=='r') 
				tempsize=tempsize-2;
			else if(txt.toCharArray()[a]=='m') 
				tempsize=tempsize+2;
			else if(txt.toCharArray()[a]=="'".toCharArray()[0]) 
				tempsize=tempsize-4;
			else if(txt.toCharArray()[a]==':') 
				tempsize=tempsize-4;
		}
	}

	public static String toShortFloat(float f){
		String text= f+"";
		text=text.replace('.', ',');
		String preA = text+"";
		String preP = "";
		if(text.split(",").length>1)
			{
			text=preA.split(",")[0];
			preP=preA.split(",")[1];
			preP=preP.substring(0, preP.length()>2?2:preP.length());
			text=text+","+preP;
			}
		text=text.replace(',', '.');
		return text;
	}
}

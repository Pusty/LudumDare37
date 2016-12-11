package me.game.event;

import com.badlogic.gdx.Gdx;

import me.game.dialog.Dialog;
import me.game.dif.EndTick;
import me.game.entity.EntityLiving;
import me.game.main.Engine;
import me.game.util.Location;
import me.game.world.Zone;

public class BlockEvent implements Event{
	
	public static int RIGHT_DOOR = 1000;
	public static int LEFT_DOOR = 1001;
	public static int PAINTING_TURN_UP = 1002;
	public static int PAINTING_TURN_DOWN = 1003;
	public static int WOOD_OPEN = 1004;
	public static int WOOD_CLOSE = 1005;
	public static int PLANT = 1006;
	public static int HANG_PLANT = 1007;
	public static int HAPPY = 1008;
	public static int ENDING = 1009;
	public static int HINT_1 = 1010;
	public static int HINT_2 = 1011;
	public static int HINT_3 = 1012;
	public static int HINT_0 = 1013;
	public static int DIA_COUCH = 1014;
	public static int DIA_BOOKS = 1015;
	public static int DIA_WOOD = 1016;
	public static int DIA_CAKE = 1017;
	public static int DIA_COMPUTER = 1018;
	public static int DIA_CHAIR = 1019;
	public static int DIA_TABLE = 1020;
	public static int DIA_EMPTY = 1021;
	public static int DIA_TREE = 1022;
	public static int DIA_PLANT = 1023;
	public static int DIA_SALAD = 1024;
	public static int DIA_FOOD = 1025;
	public static int DIA_HANG = 1026;
	public static int DIA_MIRROR = 1027;
	public static int DIA_POTATO = 1028;
	
	EntityLiving living;
	int x;
	int z;
	int id;
	int index;
	public BlockEvent(int index,EntityLiving l,int x,int z,int id) {
		this.living=l;
		this.x=x;
		this.z=z;
		this.id=id;
		this.index=index;
	}
	
	public static boolean handleBlockCollision(Engine engine,EntityLiving e,int x,int z,int id) {
		
		if(engine.getVariable().get("lvl_1") == 0 &&
		(((engine.getVariable().get("right")+1)%4 == 0 && engine.getZone().getBlockID(5, 9) == 33)
		|| ((engine.getVariable().get("right")+1)%4 == 3 && engine.getZone().getBlockID(5, 9) == 32))) {
			engine.getVariable().put("lvl_1", 1);
			engine.getSound().playClip("up");
			engine.getVariable().put("lvl_2", 0);
		}
		if(id == 7) {
				engine.getEventHandler().queueEvent(new ScreenEvent(0));
				engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.RIGHT_DOOR,e,x,z,id));
		}else if(id == 27) {
				engine.getEventHandler().queueEvent(new ScreenEvent(0));
				engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.LEFT_DOOR,e,x,z,id));
		}
		if(engine.getVariable().get("lvl_1") == 0) {
			if(id == 32) {
				engine.getEventHandler().queueEvent(new ScreenEvent(0));
				engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.PAINTING_TURN_UP,e,x,z,id));
			}else if(id == 33) {
				engine.getEventHandler().queueEvent(new ScreenEvent(0));
				engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.PAINTING_TURN_DOWN,e,x,z,id));
			}
		}
		if(engine.getVariable().get("lvl_3") != 1) {
			if(id == 45 && e.getDirection()==2 && x==2 && z == 3) 
				engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.WOOD_OPEN,e,x,z,id));
			else if(id == 46 && e.getDirection()==2) 
				engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.WOOD_CLOSE,e,x,z,id));
			else if(id == 23 && (engine.getZone().getBlockID(2,3) == 46)) {
				engine.getEventHandler().queueEvent(new ScreenEvent(0));
				engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.HANG_PLANT,e,x,z,id));
			}else if(id == 23) {
				engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.DIA_TREE,e,x,z,id));
			}
		}else if(id == 23) {
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.DIA_TREE,e,x,z,id));
		}
		if(id == 45 && x==1 && z == 3)
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.DIA_WOOD,e,x,z,id));
		
		if(id == 29)
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.DIA_BOOKS,e,x,z,id));
		else if(id == 30)
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.DIA_BOOKS,e,x,z,id));
		else if(id == 31)
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.DIA_BOOKS,e,x,z,id));
		
		if(id == 11 || id == 14 || (id >= 34 && id <= 39))
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.DIA_COUCH,e,x,z,id));
		
		
		if(id == 44 && engine.getVariable().get("lvl_2") == 0 && (engine.getVariable().get("right")+engine.getVariable().get("left"))%4 == 0) 
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.PLANT,e,x,z,id));
		else if(id == 44)
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.DIA_FOOD,e,x,z,id));
		
		if(id == 48) {
			if(engine.getVariable().get("lvl_1") == 0)
				engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.HINT_1,e,x,z,id));
			else if(engine.getVariable().get("lvl_2") == 0)
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.HINT_2,e,x,z,id));
			else if(engine.getVariable().get("lvl_3") == 0)
				engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.HINT_3,e,x,z,id));
			if(engine.getVariable().get("lvl_4") == 0) {
				engine.getEventHandler().queueEvent(new ScreenEvent(0));
				engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.HAPPY,e,x,z,id));
			}
		}
		if(id == 52)
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.DIA_COMPUTER,e,x,z,id));
		else if(id == 53)
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.DIA_CAKE,e,x,z,id));
		else if(id == 26)
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.DIA_EMPTY,e,x,z,id));
		else if(id == 25 || id == 24)
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.DIA_PLANT,e,x,z,id));
		else if(id == 8)
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.DIA_SALAD,e,x,z,id));
		else if(id == 47)
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.DIA_MIRROR,e,x,z,id));
		else if(id == 41)
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.DIA_TABLE,e,x,z,id));
		else if(id == 43)
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.DIA_CHAIR,e,x,z,id));
		else if(id == 49)
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.DIA_HANG,e,x,z,id));
		else if(id == 54)
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.DIA_POTATO,e,x,z,id));
		if((id == 6 || id == 28) && engine.getVariable().get("lvl_4") == 1) {
			engine.getEventHandler().queueEvent(new BlockEvent(BlockEvent.ENDING,e,x,z,id));
		}
		
		
		if(id >= 15 && id <= 22) return true;
		if(id == 7 || id == 27) return true;
		if(id == 5 || id == 0) return true;
		return false;
	}
	
	
	@Override
	public String getName() {
		return "block";
	}
	


	public static void changeWorld(Zone z, Engine game) {
		if(game.getVariable().get("lvl_2") <= 0)
			couch(z, (game.getVariable().get("right")+game.getVariable().get("left"))%4);
		if(game.getVariable().get("lvl_1") == 0) 
			carpet(z, (game.getVariable().get("right")+1)%4);
		//Table bot right
		if(game.getVariable().get("lvl_2") != 1)
			z.setBlockID(5, 3, 44);
		if(game.getVariable().get("lvl_3") >= 0 && game.getVariable().get("left") == 2)
			z.setBlockID(5, 3, 52);
		else if(game.getVariable().get("lvl_3") >= 0)
			z.setBlockID(5, 3, 41);
			
			z.setBlockID(5, 2, 43);
		//Bookshelf unsorted
			bookself(z, Math.abs(game.getVariable().get("medium"))%3);
		//Picture wrong way
		if(game.getVariable().get("lvl_1") == 0) 
			z.setBlockID(5, 9, 33);
		//Flowerpot Empty
		if(game.getVariable().get("lvl_3") < 0)
			z.setBlockID(4, 8, 26);
		else if(game.getVariable().get("lvl_4") < 0)
			plant(z, Math.min(4, (int)Math.abs(Math.sin(game.getVariable().get("right")*game.getVariable().get("left"))*4)));
		//Mirror
		if(game.getVariable().get("lvl_4") < 0)
			z.setBlockID(8, 3, 48);
		//Woodenthingies
		if(game.getVariable().get("lvl_2") != 1) {
			z.setBlockID(1, 3, 45);
			z.setBlockID(2, 3, 45);
		}
		if(game.getVariable().get("right") == 3 && game.getVariable().get("left") == 0) 
			z.setBlockID(6, 6, 53);
		else if(game.getVariable().get("left") == 1 && game.getVariable().get("right") == 2) 
			z.setBlockID(1, 8, 54);
		else {
			z.setBlockID(6, 6, 5);
			z.setBlockID(1, 8, 5);
		}

	}
	
	private static void bookself(Zone z, int d) {
		if(d == 2)
			z.setBlockID(7, 8, 29);
		else if(d == 1)
			z.setBlockID(7, 8, 30);
		else if(d == 0)
			z.setBlockID(7, 8, 31);
	}
	
	
	private static void plant(Zone z, int d) {
		if(d == 3)
			z.setBlockID(4, 8, 25);
		else if(d == 2)
			z.setBlockID(4, 8, 24);
		else if(d == 1)
			z.setBlockID(4, 8, 23);
		else if(d == 0)
			z.setBlockID(4, 8, 8);
	}
	
	private static void couch(Zone z, int d) {
		z.setBlockID(2, 8, d==0?11:5);
		z.setBlockID(3, 8, d==0?14:5);
		
		z.setBlockID(1, 6, d==1?36:5);
		z.setBlockID(1, 5, d==1?37:5);
		
		z.setBlockID(1, 2, d==2?38:5);
		z.setBlockID(2, 2, d==2?39:5);
		
		
		z.setBlockID(7, 5, d==3?35:5);
		z.setBlockID(7, 6, d==3?34:5);
	}
	
	private static void carpet(Zone z, int d) {
		z.setBlockID(4, 6, d!=0?15:19);
		z.setBlockID(5, 6,  d!=1?16:20);
		z.setBlockID(4, 5,  d!=2?17:21);
		z.setBlockID(5, 5,  d!=3?18:22);
	}
	
	@Override
	public void event(final Engine game) {
		if(getIndex()==BlockEvent.RIGHT_DOOR) {
			living.setLocation(new Location(4,5));
			living.setTraveled(0);
			game.getVariable().put("right", game.getVariable().get("right")+1);
			game.getVariable().put("medium", game.getVariable().get("medium")+1);
			changeWorld(game.getZone(), game);
			if(game.getVariable().get("dial") == 0)
				game.getEventHandler().queueEvent(new BlockEvent(BlockEvent.HINT_0,living,x,z,id));
		}else if(getIndex()==BlockEvent.LEFT_DOOR) {
			living.setLocation(new Location(4,5));
			living.setTraveled(0);
			game.getVariable().put("left", game.getVariable().get("left")+1);
			game.getVariable().put("medium", game.getVariable().get("medium")-1);
			changeWorld(game.getZone(), game);
			if(game.getVariable().get("dial") == 0)
				game.getEventHandler().queueEvent(new BlockEvent(BlockEvent.HINT_0,living,x,z,id));
		}else if(getIndex()==BlockEvent.PAINTING_TURN_UP) {
			game.getZone().setBlockID(5, 9, 33);
		}else if(getIndex()==BlockEvent.PAINTING_TURN_DOWN) {
			game.getZone().setBlockID(5, 9, 32);
		}else if(getIndex()==BlockEvent.WOOD_OPEN) {
			game.getZone().setBlockID(x, z, 46);
		}else if(getIndex()==BlockEvent.WOOD_CLOSE) {
			game.getZone().setBlockID(x, z, 45);
		}else if(getIndex()==BlockEvent.PLANT) {
			game.getZone().setBlockID(4, 8, 8);
			game.getZone().setBlockID(5, 3, 41);
			game.getVariable().put("lvl_2", 1);
			game.getSound().playClip("up");
			game.getVariable().put("lvl_3", 0);
		}else if(getIndex()==BlockEvent.HANG_PLANT) {
			game.getZone().setBlockID(2, 3, 49);
			game.getZone().setBlockID(4, 8, 26);
			game.getVariable().put("lvl_3", 1);
			game.getSound().playClip("up");
			game.getVariable().put("lvl_4", 0);
		}else if(getIndex()==BlockEvent.HINT_0) {
			game.getVariable().put("dial", 1);
			String[] text = {"This room is w rong","I ca n't go yet"};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.HINT_1) {
			String[] text = {"The  orientations are w rong"};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.HINT_2) {
			String[] text = {"The couch needs ox ygen"};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.HINT_3) {
			String[] text = {"I have to give the tiny people","something to chop up "};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.DIA_BOOKS) {
			String[] text = {"Books.. I like books"};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.DIA_WOOD) {
			String[] text = {"I can't open it","The small  people locked it"};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.DIA_MIRROR) {
			String[] text = {"A mirror without a reflection"};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.DIA_CAKE) {
			String[] text = {"The cake is a  lie"};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.DIA_COMPUTER) {
			String[] text = {"A Computer with  Half Life 3 installed"};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.DIA_CHAIR) {
			String[] text = {"A chair","Sitting dow n w ould  be nice.."};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.DIA_HANG) {
			String[] text = {"I hope the small people will  be happy","w ith  this tree"};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.DIA_EMPTY) {
			String[] text = {"A empty flow er pot","I can't breath here"};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.DIA_TABLE) {
			String[] text = {"I'm hungry.. Salad w ould  be nice"};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.DIA_PLANT) {
			String[] text = {"A plant. Looks like someone","stole it from my garden"};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.DIA_TREE) {
			String[] text = {"A small tree for small people"};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.DIA_SALAD) {
			String[] text = {"Looks like salad in a flow er pot"};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.DIA_FOOD) {
			String[] text = {"Sa lad.."};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.DIA_POTATO) {
			String[] text = {"A  gigantic  potato","I don't think the tiny people w ould","like it"};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.DIA_COUCH) {
			String[] text = {"A couch..a blue couch","Why is this couch blue?","I don't like blue things","Blue is such a unnatural color"};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.HAPPY) {
			game.getZone().setBlockID(8, 3, 47);
			game.getZone().setBlockID(8, 5, 28);
			game.getZone().setBlockID(0, 5, 6);
			game.getVariable().put("lvl_4", 1);
			game.getSound().playClip("up");
			String[] text = {"I can go now ..."};
			game.setDialog(new Dialog(null,text,null));
		}else if(getIndex()==BlockEvent.ENDING) {
			game.getVariable().put("done", 1);
			//GAME END
			EndTick tick = new EndTick(game);
			game.setScreen(tick);
		    Gdx.input.setInputProcessor(tick);
		}
	}

	@Override
	public int getIndex() {
		return index;
	}
}


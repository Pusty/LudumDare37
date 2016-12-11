package me.game.world;

import me.game.entity.Entity;
import me.game.main.Engine;
import me.game.util.Location;



public class Zone {
	int sizex;
	int sizez;
	int sizee;
	Entity[] entityarray;
	Chunk[] chunkarray;
	Engine mainclass;
	String name;
	Location offset;
	int defID;
	int outID;
	public Zone(Engine m,int sx, int sz, int se, Location off, String n,int d,int o) {
		mainclass=m;
		sizex = sx;
		sizez = sz;
		sizee = se;
		chunkarray = new Chunk[(sx/10) * (sz/10)];
		int c=0;
		for(int cx=0;cx<sx/10;cx++)
			for(int cz=0;cz<sz/10;cz++){
			chunkarray[c]=new Chunk(cx,cz,10,10);c++;}
		entityarray  = new Entity[se];
		offset= off;
		name = n;
		defID=d;
		outID=o;
	}
	public int getDefID() {
		return defID;
	}
	public void setDefID(int id) {
		defID = id;
	}
	public int getOutID() {
		return outID;
	}
	public void setOutID(int id) {
		outID = id;
	}
	public String getName() {
		return name;
	}
	public Location getOffset() {
		return offset;
	}


	public int addEntity(Entity e){
		for(int i=0;i<entityarray.length;i++){
			if(entityarray[i]==null){
				entityarray[i] = e;return i;}
		}return -1;
	}
	
	public void removeEntity(Entity e){
		for(int i=0;i<entityarray.length;i++){
			if(entityarray[i]==e){
				entityarray[i] = null;return;}
		}
	}
	
	public Entity[] getEntityArray(){
		return entityarray;
	} 

	
	public Entity entityAt(int x,int z) {
		for(int entityIndex=0;entityIndex<getEntityArray().length;entityIndex++) {
			Entity entity = getEntityArray()[entityIndex];
			if(entity==null)continue;
			if(entity.getX() == x && entity.getZ() == z)
				return entity;
		}return null;
	}

	public int getBlockID(int x, int z) {
		int wx = x/10;
		int wz = z/10;
		if(wx*sizez/10+wz>=sizex/10*sizez/10)return 0;
		if(x<0 || z<0)return 0;
		return chunkarray[((wx*(sizez/10))+wz)].getBlockID(x-(10*wx),  z-(10*wz));
	}
	
	/**Using the offset!*/
	public int getBlockID(Location loc) {
		int x = loc.getX() - offset.getX();
		int z = loc.getZ() - offset.getZ();
		int wx = x/10;
		int wz = z/10;
		if(wx*sizez/10+wz>=sizex/10*sizez/10)return 0;
		if(x<0 || z<0)return 0;
		return chunkarray[((wx*(sizez/10))+wz)].getBlockID(x-(10*wx),  z-(10*wz));
	}
	/**Using the offset!*/
	public Entity entityAt(Location loc) {
		int x = loc.getX() - offset.getX();
		int z = loc.getZ() - offset.getZ();
		for(int entityIndex=0;entityIndex<getEntityArray().length;entityIndex++) {
			Entity entity = getEntityArray()[entityIndex];
			if(entity==null)continue;
			if(entity.getX() == x && entity.getZ() == z)
				return entity;
		}return null;
	}


	public void setBlockID(int x, int z,int id) {
		int wx = x/10;
		int wz = z/10;
		if(wx*sizez/10+wz>=sizex/10*sizez/10)return;
		chunkarray[((wx*(sizez/10))+wz)].setBlockID(x-(10*wx),  z-(10*wz),id);
	}
	
	public int getSizeX() {
		return sizex;
	}

	public int getSizeZ() {
		return sizez;
	}
	
	public Chunk[] getChunkArray(){
		return chunkarray;
	}
	public int getEntityArraySize() {
	int i=0;
	for(Entity e:getEntityArray())
		if(e!=null)i++;
	return i;
	}
	
	public boolean hasEntity(Entity en) {
		for(int i=0;i<entityarray.length;i++){
			if(entityarray[i]==en){
				return true;}
		}
		return false;
	}
	
}

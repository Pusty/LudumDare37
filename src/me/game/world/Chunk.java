package me.game.world;

import me.game.main.Engine;
import me.game.util.Location;

public class Chunk {
	int[] idarray;
	int sizex;
	int sizez;
	int chunkx;
	int chunkz;
		public Chunk(int cx,int cz,int sx,int sz){
			idarray = new int[sx * sz];
			sizex = sx;
			sizez = sz;
			chunkx=cx;
			chunkz=cz;
			for(int i=0;i<idarray.length;i++){
				idarray[i]=0;
			}
			
		}
		
		public boolean withInScreen(Engine engine,Zone z) {
			Location loc = engine.getPlayer().getLocation();
			return (z.getOffset().getX() + getChunkX()*getSizeX() <= loc.getX()+4 &&
					z.getOffset().getZ() + getChunkZ()*getSizeZ() <= loc.getZ()+3 &&
					z.getOffset().getX() + getChunkX()*getSizeX() + getSizeX()> loc.getX() -4&&
					z.getOffset().getZ() + getChunkZ()*getSizeZ() + getSizeZ() >loc.getZ() -3);
		}
		
		
		public int getChunkX(){return chunkx;}
		public int getChunkZ(){return chunkz;}
		public int getBlockID(int x, int z) {
			if (x >= sizex || z >= sizez || x < 0 || z < 0)
				return 0;
			return idarray[z * sizex + x];
		}
		
	

		public int getSizeX() {
			return sizex;
		}

		public int getSizeZ() {
			return sizez;
		}

		public int[] getBlockArray() {
			return idarray;
		}
		public void setBlockID(int x, int z,int id) {
			if (x >= sizex || z >= sizez || x < 0 || z < 0)
				return;
			idarray[z * sizex + x]=id;
		}
	
}

package me.game.util;

public class Location {
	public int x;
	public int z;
	public Location(int x,int z){
		this.x=x;
		this.z=z;
	}
	
	public Location sub(Location l){
		int cx=getX()-l.getX();
		int cz=getZ()-l.getZ();
		return new Location(cx,cz);
	}
	
	public static Location getNorm(Location v) {
		double distance = getDistance(v,new Location(0,0));
		int cx=(int) (v.x/distance);
		int cz=(int) (v.z/distance);
		return new Location(cx,cz);
	}
	
	
	public int angle(Location l2){
		int scalar = (this.getX()*l2.getX())+(this.getZ()*l2.getZ());
		int distance1 = (int) Location.getDistance(new Location(0,0), this);
		int distance2 = (int) Location.getDistance(new Location(0,0), l2);
		int distance = distance1*distance2;
//		Szstem.out.println(distance1+","+distance2+","+Math.acos(scalar/distance));
		return (int) Math.toDegrees(Math.acos(scalar/distance));
		
				// |1|	* |2|
				// |1|	* |2|
	}
	
	public Location subToDirection(Location l){
		int cx=l.getX()-getX();
		int cz=l.getZ()-getZ();
		return new Location((int)Math.sin(cx)/2,(int)Math.sin(cz)/2);
	}
	
	public Location addVelocity(Velocity v){
		int cx=getX()+v.getX();
		int cz=getZ()+v.getZ();
		return new Location(cx,cz);
	}
	public int getX(){return x;}
	public int getZ(){return z;}
	public void setX(int x){this.x=x;}
	public void setZ(int z){this.z=z;}
	public Location clone(){return new Location(x,z);}
    public static double getDistance(Location l,Location l2){
    	return Math.sqrt(((l2.getX()-l.getX())*(l2.getX()-l.getX()))+((l2.getZ()-l.getZ())*(l2.getZ()-l.getZ())));
    }
    

	public Location add(Location a) {
		int cx = x + a.x;
		int cz = z + a.z;
 
		return new Location(cx,cz);
	}
 

 


	
	public boolean sameAs(Location loc){
		if(this.x==loc.x && this.z==loc.z)return true;
		return false;
	}
	
	
	
	public String toString(){
		return x+"|"+z;
	}

	public Location multiply(Location location) {
		int cx = x*location.x;
		int cz = z*location.z;
		return new Location(cx,cz);
	}

	public void set(Location l) {
		this.setX(l.getX());
		this.setZ(l.getZ());
	}

	public Velocity toVelocity() {
		return new Velocity(getX(),getZ());
	}
	
	public Location redirect() {
		return new Location(-x,-z);
	}


}

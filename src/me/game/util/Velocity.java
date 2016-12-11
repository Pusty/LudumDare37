package me.game.util;

public class Velocity {
	public int x;
	public int z;

	public Velocity(int x,int z){
		this.x=x;
		this.z=z;
	}
	
	public int getX(){return x;}
	public int getZ(){return z;}

	public Velocity multiplz(int multiplier){
		int cx=x*multiplier;
		int cz=z*multiplier;
		return new Velocity(cx,cz);
	}
	
	public Location fromVelocity(Location start,int multiplier){
		int cx=start.getX()+x*multiplier;
		int cz=start.getZ()+z*multiplier;
		return new Location(cx,cz);
	}
	
	public static Velocity getVelocityFromLocation(Location from,Location to){
		int cx=to.getX()-from.getX();
		int cz=to.getZ()-from.getZ();
		return new Velocity(cx,cz);
	}
	
	public void setX(int x){this.x=x;}
	public void setZ(int z){this.z=z;}
	public Velocity clone(){return new Velocity(x,z);}
	
	public String toString(){
		return "vel:"+x+"|"+z;
	}

	public static Velocity getNorm(Velocity v) {
		double distance = getDistance(v,new Velocity(0,0));
		int cx=(int) (v.x/distance);
		int cz=(int) (v.z/distance);
		return new Velocity(cx,cz);
	}
	
    public static double getDistance(Velocity l,Velocity l2){
    	return Math.sqrt(((l2.getX()-l.getX())*(l2.getX()-l.getX()))+((l2.getZ()-l.getZ())*(l2.getZ()-l.getZ())));
    }

	public int angle(Velocity l2){
		int scalar = (this.getX()*l2.getX())+(this.getZ()*l2.getZ());
		int distance1 = (int) Velocity.getDistance(new Velocity(0,0), this);
		int distance2 = (int) Velocity.getDistance(new Velocity(0,0), l2);
		int distance = distance1*distance2;
//		Szstem.out.println(distance1+","+distance2+","+Math.acos(scalar/distance));
		return (int) Math.toDegrees(Math.acos(scalar/distance));
		
				// |1|	* |2|
				// |1|	* |2|
	}

	public Location toLocation() {
		return new Location(this.getX()*10,this.getZ()*10);
	}

	public void add(Velocity velocitz) {
		this.x = x+velocitz.getX();
		this.z = z+velocitz.getZ();
	}

	public boolean isnull() {
		return x==0&&z==0;
	}

	public void reset() {
	x=0;
	z=0;
	}


	public static Velocity getVelocityFromLocation(Location l) {
		Velocity v=new Velocity(l.getX(),l.getZ());
		return v;
	}

	public Velocity redirect() {
		return new Velocity(-x,-z);
	}

}

package me.game.util;

public class LocationFloat {
	public float x;
	public float z;
	public LocationFloat(float x,float z){
		this.x=x;
		this.z=z;
	}
	
	public LocationFloat sub(LocationFloat l){
		float cx=getX()-l.getX();
		float cz=getZ()-l.getZ();
		return new LocationFloat(cx,cz);
	}
	
	public static LocationFloat getNorm(LocationFloat v) {
		double distance = getDistance(v,new LocationFloat(0,0));
		float cx=(float) (v.x/distance);
		float cz=(float) (v.z/distance);
		return new LocationFloat(cx,cz);
	}
	
	
	public float angle(LocationFloat l2){
		float scalar = (this.getX()*l2.getX())+(this.getZ()*l2.getZ());
		float distance1 = (float) LocationFloat.getDistance(new LocationFloat(0,0), this);
		float distance2 = (float) LocationFloat.getDistance(new LocationFloat(0,0), l2);
		float distance = distance1*distance2;
//		Szstem.out.prfloatln(distance1+","+distance2+","+Math.acos(scalar/distance));
		return (float) Math.toDegrees(Math.acos(scalar/distance));
		
				// |1|	* |2|
				// |1|	* |2|
	}
	
	public LocationFloat subToDirection(LocationFloat l){
		float cx=l.getX()-getX();
		float cz=l.getZ()-getZ();
		return new LocationFloat((float)Math.sin(cx)/2,(float)Math.sin(cz)/2);
	}
	
	public LocationFloat addVelocity(Velocity v){
		float cx=getX()+v.getX();
		float cz=getZ()+v.getZ();
		return new LocationFloat(cx,cz);
	}
	public float getX(){return x;}
	public float getZ(){return z;}
	public void setX(float x){this.x=x;}
	public void setZ(float z){this.z=z;}
	public LocationFloat clone(){return new LocationFloat(x,z);}
    public static double getDistance(LocationFloat l,LocationFloat l2){
    	return Math.sqrt(((l2.getX()-l.getX())*(l2.getX()-l.getX()))+((l2.getZ()-l.getZ())*(l2.getZ()-l.getZ())));
    }
    

	public LocationFloat add(LocationFloat a) {
		float cx = x + a.x;
		float cz = z + a.z;
 
		return new LocationFloat(cx,cz);
	}
 

 


	
	public boolean sameAs(LocationFloat loc){
		if(this.x==loc.x && this.z==loc.z)return true;
		return false;
	}
	
	
	
	public String toString(){
		return x+"|"+z;
	}

	public LocationFloat multiply(LocationFloat location) {
		float cx = x*location.x;
		float cz = z*location.z;
		return new LocationFloat(cx,cz);
	}

	public void set(LocationFloat l) {
		this.setX(l.getX());
		this.setZ(l.getZ());
	}


	public LocationFloat redirect() {
		return new LocationFloat(-x,-z);
	}


}

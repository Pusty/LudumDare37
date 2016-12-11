package me.game.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.game.main.Engine;
import me.game.util.Location;

public class World {
	List<Zone> zonesList;
	Zone[] zones = null;
	public World() {
		zonesList = new ArrayList<Zone>();
	}
	
	public List<Zone> getList() {
		return zonesList;
	} 
	public void addZone(Zone z) {
		zonesList.add(z);
	}
	/**Necessary to work with this object*/
	public void createArray() {
		zones = zonesList.toArray(new Zone[zonesList.size()]);
	}

	public Zone getZoneAt(Location location) {
		for(int z=0;z<zones.length;z++) {
			Zone zone = zones[z];
			if(zone.getOffset().getX() <= location.getX() && zone.getOffset().getZ() <= location.getZ() &&
			zone.getOffset().getX()+zone.getSizeX()> location.getX() &&
			zone.getOffset().getZ()+zone.getSizeZ() > location.getZ() )
				return zone;
		}
		return null;
	}

	/**Getting Zones which are to render*/
	public Zone[] getZonesNearPlayer(Engine engine) {
		HashMap<String,Zone> zoneMap = new HashMap<String,Zone>();
		Zone z = getZoneAt(engine.getPlayer().getLocation().add(new Location(-4,3)));
		if(z!=null)
			zoneMap.put(z.getName(), z);
		z = getZoneAt(engine.getPlayer().getLocation().add(new Location(4,3)));
		if(z!=null)
			zoneMap.put(z.getName(), z);
		z = getZoneAt(engine.getPlayer().getLocation().add(new Location(-4,-3)));
		if(z!=null)
			zoneMap.put(z.getName(), z);
		z = getZoneAt(engine.getPlayer().getLocation().add(new Location(4,-3)));
		if(z!=null)
			zoneMap.put(z.getName(), z);
		return zoneMap.values().toArray(new Zone[zoneMap.size()]);
	}
}

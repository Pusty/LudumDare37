package me.game.world;

import me.game.main.Engine;
import me.game.util.FileChecker;
import me.game.util.Location;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class ZoneLoader {
	public static Zone loadZone(Engine e,Location offset,String name,int entityCap) {
		Zone zone = null;
		FileHandle handle = Gdx.files.internal("resources/"+name);
		String text = handle.readString();
		String lines[] = text.split("\n");
		int count = 0;
		for(char c:lines[0].toCharArray())
			if(c==',')
				count++;
		
		int sizeX = count+1;
		int sizeY = lines.length;
		
		
		zone = new Zone(e,sizeX,sizeY,entityCap,offset,name,0,0);
		int[] blockIds = new int[100];
		for(int y=0;y<sizeY;y++) {
			String[] numbers = FileChecker.splitNonRegex(lines[y].trim() , ",");
			for(int x=0;x<sizeX;x++) {
				int number = Integer.parseInt(numbers[x].trim());
				zone.setBlockID(x,(sizeY-1)-y, number);
				blockIds[number+1]=blockIds[number+1]+1;
				if(y==0 && x== 0)
					zone.setOutID(number);
			}
		}
		zone.setOutID(51);
		int highest=0;
		for(int i=0;i<blockIds.length;i++)
			if(blockIds[i]>blockIds[highest] && i-1!=zone.getOutID())
				highest=i;
		zone.setDefID(51);
		return zone;
	}
}

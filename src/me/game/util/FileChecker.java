package me.game.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonValue;

import me.game.util.json.JsonHandler;


public class FileChecker {
	
	public static List<FileHandle> checkFolder(String prefix,String suffix) {
		List<FileHandle> list = new ArrayList<FileHandle>();
		for(Entry<String,FileHandle> entry:getStreams().entrySet()) {
			String name = splitNonRegex(entry.getKey(),".")[0];
			String type = splitNonRegex(entry.getKey(),".")[1];
			if(suffix != null && !type.equalsIgnoreCase(suffix))continue;
			if(prefix != null && !name.startsWith(prefix))continue;
			list.add(entry.getValue());
		}
		return list;
	}
	
	public static HashMap<String,FileHandle> checkFolderToHashMap(String prefix,String suffix) {
		HashMap<String,FileHandle> list = new HashMap<String,FileHandle>();
		for(Entry<String,FileHandle> entry:getStreams().entrySet()) {
			if(splitNonRegex(entry.getKey(),".").length < 2)continue;
			String name = splitNonRegex(entry.getKey(),".")[0];
			String type = splitNonRegex(entry.getKey(),".")[1];
			if(type==null)continue;
			if(suffix != null && !type.equalsIgnoreCase(suffix))continue;
			if(prefix != null && !name.startsWith(prefix))continue;
			list.put(entry.getKey(),entry.getValue());
		}
		return list;
	}
	
	
	public static String[] splitNonRegex(String input, String delim)
	{
	    List<String> l = new ArrayList<String>();
	    int offset = 0;

	    while (true)
	    {
	        int index = input.indexOf(delim, offset);
	        if (index == -1)
	        {
	            l.add(input.substring(offset));
	            return l.toArray(new String[l.size()]);
	        } else
	        {
	            l.add(input.substring(offset, index));
	            offset = (index + delim.length());
	        }
	    }
	}
	
	public static String replaceAll(String in, String ths, String that) {
	    StringBuilder sb = new StringBuilder(in);
	    int idx = sb.indexOf(ths); 
	    
	    while (idx > -1) {
	        sb.replace(idx, idx + ths.length(), that);
	        idx = sb.indexOf(ths);

	    }
	    
	    return sb.toString();

	}

	static HashMap<String,FileHandle> hashMaps = null;
	
	public static HashMap<String,FileHandle> getStreams() {
		if(hashMaps==null)
			hashMaps = createStreams();
		return hashMaps;
	}
	
	public static void close() {
		SoundLoader.close();
		if(hashMaps!=null) {
		for(Entry<String,FileHandle> entry:hashMaps.entrySet()) {
			try {
				entry.setValue(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		hashMaps.clear();
		}
	}
	public static HashMap<String,FileHandle> createStreams() {
		HashMap<String,FileHandle> streams = new HashMap<String,FileHandle>();
		try {
			FileHandle fileHandle = Gdx.files.internal("scripts/files.json");
			JsonHandler jsonHandler = new JsonHandler();
			JsonValue jsonArray = jsonHandler.getArrayFromFile(fileHandle.read());
			
			for(JsonValue jsonEntry:jsonArray) {
				FileHandle file = Gdx.files.internal(jsonEntry.asString());
				streams.put(file.name(),file);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return streams;
	}
}

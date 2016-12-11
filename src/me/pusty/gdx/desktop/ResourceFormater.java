package me.pusty.gdx.desktop;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.badlogic.gdx.utils.JsonWriter;

import me.game.util.SoundLoader;


public class ResourceFormater {
	//Formats Resources into a Json File to load them internally
	public static void main(String[] args) {
		createStreams();
		File file = new File(getURL("scripts\\files.json").getFile());
		try {
		FileWriter fileWriter = new FileWriter(file);
		JsonWriter writer = new JsonWriter(fileWriter);

		writer.array();
		HashMap<String,File> hashmap = ResourceFormater.getStreams();
		for(Entry<String,File> entry:hashmap.entrySet())
			writer.json('"'+entry.getKey()+'"');
		writer.pop();
		writer.close();
		
		}catch(Exception io) {
			io.printStackTrace();
		}
		
	}
	
		public static List<File> checkFolderSuffixFile(File dir,String suffix) {
			return checkFolderFile(dir,null,suffix,null);
		}
		public static List<File> checkFolderPrefixFile(File dir,String prefix) {
			return checkFolderFile(dir,prefix,null,null);
		}
		public static List<File> checkFolderPrefixFile(File dir,String prefix,List<File> list) {
			return checkFolderFile(dir,prefix,null,list);
		}
		public static List<File> checkFolderSuffixFile(File dir,String suffix,List<File> list) {
			return checkFolderFile(dir,null,suffix,list);
		}
		public static List<File> checkFolderFile(File dir,String prefix,String suffix,List<File> list) {
			if(list==null)
				list = new ArrayList<File>();
			try {
			for(File file:dir.listFiles()) {
				if(file==null)continue;
				if(file.isDirectory()) {
					list = checkFolderFile(file,prefix,suffix,list);
					continue;
				}
				String name = file.getName().split(Pattern.quote("."))[0];
				String type = file.getName().split(Pattern.quote("."))[1];
				if(suffix != null && !type.equalsIgnoreCase(suffix))continue;
				if(prefix != null && !name.startsWith(prefix))continue;
				list.add(file);
			}
			} catch(Exception e) { e.printStackTrace(); }
			return list;
		}
		
		public static List<File> checkFolder(String prefix,String suffix) {
			List<File> list = new ArrayList<File>();
			for(Entry<String,File> entry:getStreams().entrySet()) {
				String name = entry.getKey().split(Pattern.quote("."))[0];
				String type = entry.getKey().split(Pattern.quote("."))[1];
				if(suffix != null && !type.equalsIgnoreCase(suffix))continue;
				if(prefix != null && !name.startsWith(prefix))continue;
				list.add(entry.getValue());
			}
			return list;
		}
		
		public static HashMap<String,File> checkFolderToHashMap(String prefix,String suffix) {
			HashMap<String,File> list = new HashMap<String,File>();
			for(Entry<String,File> entry:getStreams().entrySet()) {
				String name = entry.getKey().split(Pattern.quote("."))[0];
				String type = entry.getKey().split(Pattern.quote("."))[1];
				if(suffix != null && !type.equalsIgnoreCase(suffix))continue;
				if(prefix != null && !name.startsWith(prefix))continue;
				list.put(entry.getKey(),entry.getValue());
			}
			return list;
		}
		
		static HashMap<String,File> hashMaps = null;
		
		public static HashMap<String,File> getStreams() {
			if(hashMaps==null)
				hashMaps = createStreams();
			return hashMaps;
		}
		
		public static void close() {
			SoundLoader.close();
			if(hashMaps!=null) {
			for(Entry<String,File> entry:hashMaps.entrySet()) {
				try {
					entry.setValue(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			hashMaps.clear();
			}
		}
		@SuppressWarnings("deprecation")
		public static HashMap<String,File> createStreams() {
			HashMap<String,File> streams = new HashMap<String,File>();
			try {
					File scripts = new File(getURL("scripts").getFile());
					File resources = new File(getURL("resources").getFile());
					List<File> filesScripts = checkFolderFile(scripts,null,null,null);
					List<File> filesResources = checkFolderFile(resources,null,null,null);
					for(File f:filesScripts) {
						StringBuilder builder = new StringBuilder(f.toURL().toString());
						String path = scripts.toURL().toString();
						builder.replace(0, path.length(), "");		
						streams.put("scripts/"+builder.toString(), f);
					}
					for(File f:filesResources) {
						StringBuilder builder = new StringBuilder(f.toURL().toString());
						String path = resources.toURL().toString();
						builder.replace(0, path.length(), "");		
						streams.put("resources/"+builder.toString(), f);
					}
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			return streams;
		}
		
	    @SuppressWarnings("deprecation")
		public static URL getURL(String name){
			try {
				char c = '/';
				name = name.replaceAll("/", 	System.getProperty("file.separator"));
				return  new File(System.getProperty("user.dir")+c+""+name).toURL();
			} catch (Exception e) {
					e.printStackTrace();
			}
	    	return null;
	    }
}

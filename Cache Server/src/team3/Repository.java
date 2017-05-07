package team3;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Repository {
	
	private static ConcurrentHashMap<String,String> database;
	private static Repository repo;
	
	private Repository(){
		database = new ConcurrentHashMap<String,String>();
	}
	
	public static Repository getInstance(){
		if(repo == null){
			repo = new Repository();
		}
		return repo;
	}
	
	public void addEntry(String key, String value){
		database.put(key, value);
	}

	public String getEntry(String key){
		return database.get(key);
	}
	
	public boolean hasKey(String key){
		return database.containsKey(key);
	}
	
	public Set<String> getKeys(){
		return database.keySet();
	}
}

package com.mindblowninnovation.ank;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Track {
	public int id;
	public String name;
	public ArrayList<Artist> artists;
	public Album album;
	
	public Track(){
		
	}
	
	public Track(int id, String name, ArrayList<Artist> artists, Album album){
		this.id = id;
		this.name = name;
		this.artists = artists;
		this.album = album;
	}
	
	public Track(JSONObject jtrack) throws JSONException{
		name = jtrack.getString("name");
		ArrayList<Artist> artists = new ArrayList<Artist>();
		JSONArray jartists = jtrack.getJSONArray("artists");
		for(int j = 0; j < jartists.length(); j++){
			JSONObject jartist = jartists.getJSONObject(j);
			Artist artist = new Artist();
			artist.name = jartist.getString("name");
			artists.add(artist);
		}
		Album album = new Album();
		album.title = ((JSONObject)jtrack.getJSONObject("album")).getString("name");
		this.album = album;
		this.artists = artists;
	}
			
	
	public String getArtists(){
		String toReturn="";
		for(int i = 0; i < artists.size(); i++){
			if(i != artists.size()-1)
				toReturn += artists.get(i)+", ";
			else{
				toReturn +=artists.get(i);
			}
		}
		return toReturn;
	}
}

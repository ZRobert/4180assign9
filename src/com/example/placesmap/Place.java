//In class 9
//Place.java
//Robert Payne
package com.example.placesmap;

import org.json.JSONException;
import org.json.JSONObject;

public class Place {

	String lat, lng, name, strAddress;

	public Place(JSONObject placeJSONObject) throws JSONException {
		// TODO Auto-generated constructor stub
		name = "";
		lat = Double
				.toString((Double) placeJSONObject.getJSONObject("geometry")
						.getJSONObject("location").get("lat"));
		lng = Double
				.toString((Double) placeJSONObject.getJSONObject("geometry")
						.getJSONObject("location").get("lng"));
		if (placeJSONObject.getJSONObject("geometry").has("name"))
			name = (String) placeJSONObject.getJSONObject("geometry").get(
					"name");
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStrAddress() {
		return strAddress;
	}

	public void setStrAddress(String strAddress) {
		this.strAddress = strAddress;
	}
}
